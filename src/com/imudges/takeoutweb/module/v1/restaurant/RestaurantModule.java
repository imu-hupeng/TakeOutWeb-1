package com.imudges.takeoutweb.module.v1.restaurant;

import com.imudges.takeoutweb.bean.restaurant.RestaurantUser;
import com.imudges.takeoutweb.bean.restaurant.RestaurantVersion;
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
@At("/v1/restaurant")
@Fail("http:500")
@Filters
public class RestaurantModule {
    @Inject
    Dao dao;

    @Filters
    @At("/check_version")
    @Ok("json")
    public Object chekcVersion(){
        RestaurantVersion version = dao.fetch(RestaurantVersion.class, Cnd.where("id",">=", 0).desc("id"));
        if (version == null){
            return Toolkit.getFailResult(-1,"检查失败，服务器上不包含任何版本的信息", null);
        }else {
            return Toolkit.getSuccessResult("检查成功", version);
        }
    }

    @Filters
    @Ok("json:{locked:'password|id'}")
    @At("/login")
    public Object login(@Param("username")String username, @Param("password")String password){
        boolean loginResult = true;
        NutMap result = null;

        RestaurantUser user = dao.fetch(RestaurantUser.class,
                Cnd.where("username", "=", username).and("password","=", password));
        if (user == null){
            loginResult = false;
        }
        if (!loginResult){
            user = dao.fetch(RestaurantUser.class,
                    Cnd.where("phone", "=", username).and("password","=", password));
            if (user != null){
                loginResult = true;
            }
        }
        if (loginResult){
            user.setAk(Toolkit.getAccessKey());
            dao.update(user);
            result = Toolkit.getSuccessResult("登录成功", user);
        }else {
            result = Toolkit.getFailResult(-1,"登录失败,原因：用户名或者密码错误", null);
        }
        return result;
    }

}
