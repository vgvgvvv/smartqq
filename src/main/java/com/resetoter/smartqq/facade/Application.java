package com.resetoter.smartqq.facade;

import com.resetoter.smartqq.client.SmartQQClient;
import com.resetoter.smartqq.constant.ConfigConst;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * @author ScienJus
 * @date 2015/12/18.
 */
public class Application {

    private static Logger logger = Logger.getLogger(Application.class);

    private static SmartQQClient client;


    public static void main(String[] args){

        //初始化Log4
        PropertyConfigurator.configure(Application.class.getResourceAsStream(ConfigConst.LOG4J));
        //初始化facade
        MessageCallbackFacade facade = MessageCallbackFacade.create(ConfigConst.MESSAGECALLBACK_FACADE);

        //初始化客户端
        client = new SmartQQClient(facade);
    }


}
