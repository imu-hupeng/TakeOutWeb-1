package com.imudges.takeoutweb.module.v1.restaurant;

import com.imudges.takeoutweb.bean.restaurant.RestaurantUser;
import com.imudges.takeoutweb.bean.restaurant.RestaurantVersion;
import com.imudges.takeoutweb.bean.restaurant.SMSLog;
import com.imudges.takeoutweb.util.SendMessage;
import com.imudges.takeoutweb.util.Toolkit;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by HUPENG on 2017/7/1.
 */
@IocBean
@At("/v1/restaurant")
@Fail("http:500")
@Filters(@By(type = RestaurantAuthorityFilter.class ,args={"ioc:restaurantAuthorityFilter"}))
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
        boolean loginFlag = true;
        NutMap result = null;

        RestaurantUser user = dao.fetch(RestaurantUser.class,
                Cnd.where("username", "=", username).and("password","=", password));
        if (user == null){
            loginFlag = false;
        }
        if (!loginFlag){
            user = dao.fetch(RestaurantUser.class,
                    Cnd.where("phone", "=", username).and("password","=", password));
            if (user != null){
                loginFlag = true;
            }
        }
        if (loginFlag){
            user.setAk(Toolkit.getAccessKey());
            dao.update(user);
            result = Toolkit.getSuccessResult("登录成功", user);
        }else {
            result = Toolkit.getFailResult(-1,"登录失败,原因：用户名或者密码错误", null);
        }
        return result;
    }

    @At("/check_login_status")
    @Ok("json:{locked:'password|id'}")
    @Filters
    public Object checkLoginStatus(@Param("ak")String ak){
        NutMap result = null;
        boolean checkFlag = true;

        RestaurantUser user = dao.fetch(RestaurantUser.class, Cnd.where("ak", "=", ak));
        if (user == null){
            checkFlag = false;
        }
        if(checkFlag){
            result = Toolkit.getSuccessResult("当前登录状态有效",user);
        }else {
            result = Toolkit.getFailResult(-1, "当前登录状态无效",null);
        }
        return result;
    }


    @At("register_by_phone")
    @Ok("json")
    public Object registerByPhone(@Param("phone")String phone, @Param("code")String code, @Param("password")String password){
        NutMap result = new NutMap();



        return result;
    }

    @At("send_sms")
    @Ok("json")
    public Object sendSMS(@Param("phone") String phone, HttpServletRequest request){
        NutMap result = new NutMap();
        boolean checkFlag = true;
        String ip = request.getRemoteAddr();

        Date date = new Date(System.currentTimeMillis() - 60 * 1000);
        //判断一分钟以内此IP有没有发送过，有则拒绝发送
        SMSLog smsLog = dao.fetch(SMSLog.class, Cnd.where("phone","=", phone).and("add_time", ">", date));
        if (smsLog != null){
            checkFlag = false;
        }
        if (checkFlag){
            //send sms and add log than return successful result
            String checkCode = new SendMessage().sendSMS(phone);
            smsLog = new SMSLog();
            smsLog.setCode(checkCode);
            smsLog.setIp(ip);
            smsLog.setPhone(phone);
            smsLog.setAddTime(new Date(System.currentTimeMillis()));
            dao.insert(smsLog);

            result = Toolkit.getSuccessResult("短信发送成功", null);

        }else {
            //return fail result
            result = Toolkit.getFailResult(-1,"你发送短信的频率太频繁了,请稍后再试", null);
        }
        return result;
    }


}
