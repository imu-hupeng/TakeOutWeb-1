package com.imudges.takeoutweb;


import org.nutz.dao.Dao;
import org.nutz.dao.util.Daos;
import org.nutz.integration.quartz.NutQuartzCronJobFactory;
import org.nutz.ioc.Ioc;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;


public class MainSetup implements Setup{

    @Override
    public void destroy(NutConfig arg0) {

    }

    @Override
    public void init(NutConfig conf) {
        Ioc ioc = conf.getIoc();
        Dao dao = ioc.get(Dao.class);
        Daos.createTablesInPackage(dao, "com.imudges.takeoutweb", false);
        ioc.get(NutQuartzCronJobFactory.class);
    }

}