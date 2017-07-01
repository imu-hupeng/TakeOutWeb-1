package com.imudges.takeoutweb.module.v1.market;

import com.imudges.takeoutweb.bean.market.MarketUser;
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

/**
 * Created by HUPENG on 2017/7/1.
 */
@IocBean
public class MarketAuthorityFilter implements ActionFilter {
    @Inject
    Dao dao;

    @Override
    public View match(ActionContext actionContext) {
        boolean checkFlag = true;
        HttpServletRequest request = actionContext.getRequest();
        String ak = request.getParameter("ak");
        if (ak == null){
            checkFlag = false;
        }
        if (checkFlag){
            MarketUser user = dao.fetch(MarketUser.class, Cnd.where("ak", "=", ak));
            if (user == null){
                checkFlag = false;
            }
        }
        if (checkFlag){
            return null;
        }else {
            NutMap map = Toolkit.getFailResult(-100,"登录状态失效,请重新登录", null);
            return new ViewWrapper(new UTF8JsonView(new JsonFormat(true)), map);
        }
    }
}
