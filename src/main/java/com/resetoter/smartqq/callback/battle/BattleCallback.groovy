package com.resetoter.smartqq.callback.battle

import com.resetoter.battleqq.facade.BattleFacade
import com.resetoter.battleqq.spring.BattleQQContextHolder
import com.resetoter.smartqq.callback.MessageCallback
import com.resetoter.smartqq.facade.Facade
import com.resetoter.smartqq.model.DiscussMessage
import com.resetoter.smartqq.model.GroupMessage
import com.resetoter.smartqq.model.Message
import com.resetoter.smartqq.spring.ApplicationContextHolder

/**
 * Created by 35207 on 2017/5/9 0009.
 */
class BattleCallback implements MessageCallback{

    @Override
    void onMessage(Message message) {
        if(Facade.client == null)
            return;
        ((BattleFacade)ApplicationContextHolder.instance.getBean(BattleFacade.class))
                .onMessage(Facade.client, message);
    }

    @Override
    void onGroupMessage(GroupMessage message) {
        if(Facade.client == null)
            return;
        ((BattleFacade)ApplicationContextHolder.instance.getBean(BattleFacade.class))
                .onGroupMessage(Facade.client, message);
    }

    @Override
    void onDiscussMessage(DiscussMessage message) {
        if(Facade.client == null)
            return;
        ((BattleFacade)ApplicationContextHolder.instance.getBean(BattleFacade.class))
                .onDiscussMessage(Facade.client, message);
    }
}
