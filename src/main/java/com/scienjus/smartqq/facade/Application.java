package com.scienjus.smartqq.facade;

import com.scienjus.smartqq.callback.MessageCallback;
import com.scienjus.smartqq.client.SmartQQClient;
import com.scienjus.smartqq.constant.ConfigConst;
import com.scienjus.smartqq.model.*;
import com.scienjus.smartqq.turning.TurningCommon;
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
        MessageCallbackFacade facade = MessageCallbackFacade.create(client, ConfigConst.MESSAGECALLBACK_FACADE);

        //初始化客户端
        client = new SmartQQClient(facade);
    }


}
