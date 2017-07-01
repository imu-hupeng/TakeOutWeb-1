package com.imudges.takeoutweb.module.v1.market;

import com.imudges.takeoutweb.bean.market.MarketUser;
import com.imudges.takeoutweb.bean.market.MarketVersion;
import com.imudges.takeoutweb.module.v1.restaurant.RestaurantAuthorityFilter;
import com.imudges.takeoutweb.util.Toolkit;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.*;

/**
 * Created by HUPENG on 2017/7/1.
 */
@IocBean
@Filters(@By(type = MarketAuthorityFilter.class ,args={"ioc:marketAuthorityFilter"}))
@At("/v1/market")
public class MarketModule {
    @Inject
    Dao dao;

    @At("/check_version")
    @Ok("json")
    public Object checkVersion(){
        boolean checkFlag = true;
        NutMap result = null;
        MarketVersion version = dao.fetch(MarketVersion.class, Cnd.where("id",">=", "0").desc("id"));
        if (version == null){
            checkFlag = false;
        }
        if (checkFlag){
            //服务器上存在一个版本
            result = Toolkit.getSuccessResult("版本检查成功", version);
        }else {
            //服务器上不存在任何版本
            result = Toolkit.getFailResult(-1, "版本检查失败", null);
        }
        return result;
    }

    @At("/login")
    @Ok("json:{locked:{password|id}}")
    public Object login(@Param("username")String username, @Param("password")String password){
        NutMap result = null;
        boolean loginFlag = true;
        MarketUser user = dao.fetch(MarketUser.class,
                Cnd.where("username","=", username).and("password", "=", password));
        if (user == null){
            loginFlag = false;
        }else {

        }

        return result;
    }

}
