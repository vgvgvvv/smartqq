package com.resetoter.smartqq.callback.battle

import com.resetoter.battleqq.facade.BattleFacade
import com.resetoter.battleqq.spring.BattleQQContext
import com.resetoter.smartqq.callback.MessageCallback
import com.resetoter.smartqq.model.DiscussMessage
import com.resetoter.smartqq.model.GroupMessage
import com.resetoter.smartqq.model.Message

/**
 * Created by 35207 on 2017/5/9 0009.
 */
class BattleCallback implements MessageCallback{

    @Override
    void onMessage(Message message) {
        ((BattleFacade)BattleQQContext.instance.context.getBean(BattleFacade.class))
                .onMessage(message);
    }

    @Override
    void onGroupMessage(GroupMessage message) {
        ((BattleFacade)BattleQQContext.instance.context.getBean(BattleFacade.class))
                .onGroupMessage(message);
    }

    @Override
    void onDiscussMessage(DiscussMessage message) {
        ((BattleFacade)BattleQQContext.instance.context.getBean(BattleFacade.class))
                .onDiscussMessage(message);
    }
}
