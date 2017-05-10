package com.resetoter.smartqq.callback.turning

import com.resetoter.smartqq.callback.MessageCallback
import com.resetoter.smartqq.facade.Facade
import com.resetoter.smartqq.model.DiscussMessage
import com.resetoter.smartqq.model.GroupMessage
import com.resetoter.smartqq.model.Message

/**
 * Created by 35207 on 2017/5/11 0011.
 */
class TurningCallback implements MessageCallback{
    @Override
    void onMessage(Message message) {
        if(Facade.client == null)
            return;
        TurningCommon.Response(Facade.client, message);
    }

    @Override
    void onGroupMessage(GroupMessage message) {
        if(Facade.client == null)
            return;
        TurningCommon.ResponseGroup(Facade.client, message);
    }

    @Override
    void onDiscussMessage(DiscussMessage message) {
        if(Facade.client == null)
            return;
    }
}
