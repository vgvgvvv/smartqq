package com.resetoter.smartqq.facade

import com.resetoter.smartqq.callback.MessageCallback
import com.resetoter.smartqq.manager.ConfigManager
import com.resetoter.smartqq.model.DiscussMessage
import com.resetoter.smartqq.model.GroupMessage
import com.resetoter.smartqq.model.Message
import org.apache.log4j.Logger

import java.lang.reflect.Type

/**
 * Created by 35207 on 2017/5/8 0008.
 */
class MessageCallbackFacade implements MessageCallback {

    static Logger logger = Logger.getLogger(MessageCallbackFacade.class);

    private List<MessageCallback> callbackList = new ArrayList<MessageCallback>();

    static MessageCallbackFacade create(String configPath){
        MessageCallbackFacade facade = new MessageCallbackFacade();
        String callbacks = ConfigManager.getProperty(configPath, "callbacks");
        String[] callbackNames =  callbacks.split(",")
        callbackNames.each{
            logger.info("初始化回调器:" + it)
            Type callbackType = Type.forName(it)
            if(callbackType != null)
                facade.callbackList << callbackType.newInstance()
            else
                logger.warn("回调器初始化失败:" + it);
        };
        return facade;
    }

    private MessageCallbackFacade(){

    }

    @Override
    void onMessage(Message message) {
        try{
            if(Facade.receiver.working)
                return;
            Facade.receiver.mapping()

            logger.info("onMessage：" + message.getContent());
            callbackList.forEach({
                it.onMessage(message);
            });
        }catch (Exception e){
            logger.error(e.message, e);
        }

    }

    @Override
    void onGroupMessage(GroupMessage message) {
        try {
            if(Facade.receiver.working)
                return;
            Facade.receiver.mapping()
            logger.info("onGroupMessage：" + message.getContent());
            callbackList.forEach({
                it.onGroupMessage(message);
            });
        }catch (Exception e){
            logger.error(e.message, e);
        }
    }

    @Override
    void onDiscussMessage(DiscussMessage message) {
        try {
            if(Facade.receiver.working)
                return;
            Facade.receiver.mapping()
            logger.info("onDiscussMessage：" + message.getContent());
            callbackList.forEach({
                it.onDiscussMessage(message);
            });
        }catch (Exception e){
            logger.error(e.message, e);
        }

    }
}
