package com.resetoter.smartqq.callback.turning;

import com.resetoter.smartqq.callback.MessageCallback;
import com.resetoter.smartqq.model.DiscussMessage;
import com.resetoter.smartqq.model.Message;
import com.resetoter.smartqq.facade.Facade;
import com.resetoter.smartqq.model.GroupMessage;

/**
 * Created by 35207 on 2017/5/8 0008.
 * 图灵Callback，负责所有的图灵机器人逻辑
 */
public class TurningCallback implements MessageCallback {

    @Override
    public void onMessage(Message message) {
        if(Facade.getClient() == null)
            return;
        TurningCommon.Response(Facade.getClient(), message);
    }

    @Override
    public void onGroupMessage(GroupMessage message) {
        if(Facade.getClient() == null)
            return;
        TurningCommon.ResponseGroup(Facade.getClient(), message);
    }

    @Override
    public void onDiscussMessage(DiscussMessage message) {
        if(Facade.getClient() == null)
            return;
    }

}
