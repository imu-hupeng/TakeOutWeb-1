package com.imudges.takeoutweb;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.IocBy;
import org.nutz.mvc.annotation.Modules;

import org.nutz.mvc.annotation.SetupBy;
import org.nutz.mvc.ioc.provider.ComboIocProvider;

@SetupBy(value=MainSetup.class)
@IocBy(type=ComboIocProvider.class, args={"*js", "ioc/",
        "*anno", "com.imudges.takeoutweb",
        "*tx", // 事务拦截 aop
        "*quartz"}) // 异步执行aop
@IocBean
@Modules(scanPackage=true)
public class MainModule {

}