package com.resetoter.smartqq.facade

import com.resetoter.smartqq.client.SmartQQClient
import com.resetoter.smartqq.constant.ConfigConst
import com.resetoter.smartqq.manager.ExtensionManager
import com.resetoter.smartqq.spring.ApplicationContextHolder
import org.apache.log4j.Logger
import org.apache.log4j.PropertyConfigurator

/**
 * Created by 35207 on 2017/5/8 0008.
 */
class Facade {


    static Logger logger = Logger.getLogger(Facade.class);

    public static SmartQQClient client;
    public static Receiver receiver;

    static main(args){

        //应用扩展方法
        ExtensionManager.applyExtesion();
        //初始化Log4
        PropertyConfigurator.configure(Facade.class.getResourceAsStream(ConfigConst.LOG4J))
        //初始化客户端
        client = ApplicationContextHolder.instance.getBean(SmartQQClient.class)
        receiver = ApplicationContextHolder.instance.getBean(Receiver.class)
    }
}
