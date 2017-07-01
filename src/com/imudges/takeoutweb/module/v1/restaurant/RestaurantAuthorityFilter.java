package com.imudges.takeoutweb.module.v1.restaurant;

import com.imudges.takeoutweb.bean.restaurant.RestaurantUser;
import com.imudges.takeoutweb.util.Toolkit;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.JsonFormat;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.ActionContext;
import org.nutz.mvc.ActionFilter;
import org.nutz.mvc.View;
import org.nutz.mvc.view.UTF8JsonView;
import org.nutz.mvc.view.ViewWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by HUPENG on 2017/7/1.
 */
@IocBean
public class RestaurantAuthorityFilter implements ActionFilter {
    @Inject
    Dao dao;

    @Override
    public View match(ActionContext actionContext) {
        HttpServletRequest request = actionContext.getRequest();
        HttpSession session = request.getSession();

        String ak = request.getParameter("ak");
        RestaurantUser restaurantUser = dao.fetch(RestaurantUser.class, Cnd.where("ak", "=", ak));

        if (restaurantUser == null){
            NutMap map = Toolkit.getFailResult(-100,"登录状态失效,请重新登录", null);
            return new ViewWrapper(new UTF8JsonView(new JsonFormat(true)), map);
        }else {
            session.setAttribute("user", restaurantUser);
        }
        return null;

    }
}
