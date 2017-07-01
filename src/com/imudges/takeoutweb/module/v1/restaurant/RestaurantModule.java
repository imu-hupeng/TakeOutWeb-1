package com.imudges.takeoutweb.module.v1.restaurant;

import com.imudges.takeoutweb.bean.restaurant.RestaurantVersion;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;

/**
 * Created by HUPENG on 2017/7/1.
 */
@IocBean
@At("/v1/restaurant")
@Fail("http:500")
@Filters
public class RestaurantModule {
    @Inject
    Dao dao;

    @Filters
    @At("/check_version")
    @Ok("json")
    public void chekcVersion(){
        RestaurantVersion version = dao.fetch(RestaurantVersion.class, Cnd.where("id",">=", 0).desc("id"));
//        if ()
    }
}
