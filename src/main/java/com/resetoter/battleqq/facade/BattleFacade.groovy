package com.resetoter.battleqq.facade

import com.resetoter.battleqq.logic.BattleCommand
import com.resetoter.battleqq.mybatis.model.Player
import com.resetoter.smartqq.callback.MessageCallback
import com.resetoter.smartqq.client.SmartQQClient
import com.resetoter.smartqq.facade.Facade
import com.resetoter.smartqq.model.DiscussMessage
import com.resetoter.smartqq.model.GroupMessage
import com.resetoter.smartqq.model.Message
import org.apache.log4j.Logger

/**
 * Created by 35207 on 2017/5/9 0009.
 */
class BattleFacade{

    static Logger logger  = Logger.getLogger(BattleFacade.class)

    //储存所有玩家
    HashMap<Integer, Player> playerHashMap = new HashMap<>();

    void onMessage(SmartQQClient client, Message message) {

    }

    void onGroupMessage(SmartQQClient client, GroupMessage message) {
        CommandFacade.useCommand(client, message)
    }

    void onDiscussMessage(SmartQQClient client, DiscussMessage message) {

    }





}
