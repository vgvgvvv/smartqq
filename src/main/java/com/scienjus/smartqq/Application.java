package com.scienjus.smartqq;

import com.scienjus.smartqq.callback.MessageCallback;
import com.scienjus.smartqq.client.SmartQQClient;
import com.scienjus.smartqq.model.*;

import java.io.IOException;
import java.util.List;

/**
 * @author ScienJus
 * @date 2015/12/18.
 */
public class Application {

    private static SmartQQClient client;
    public static void main(String[] args) {

        MessageCallback callback = new MessageCallback() {
            @Override
            public void onMessage(Message message) {
                System.out.println("onMessage：" + message.getContent());
            }

            @Override
            public void onGroupMessage(GroupMessage message) {
                System.out.println("onGroupMessage：" + message.getContent());
                ResponseGroup(client, message);
            }

            @Override
            public void onDiscussMessage(DiscussMessage message) {
                System.out.println("onDiscussMessage：" + message.getContent());
            }
        };

        client = new SmartQQClient(callback);

    }

    public static void ResponseGroup(SmartQQClient client, GroupMessage message){
        if(message.getContent().equals("命大")){
            client.sendMessageToGroup(message.getGroupId(), "啊？");
        }
    }
}
