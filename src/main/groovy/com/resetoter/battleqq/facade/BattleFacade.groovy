package com.resetoter.battleqq.facade

import com.resetoter.battleqq.logic.PlayerDailyReward
import com.resetoter.battleqq.mybatis.model.Player
import com.resetoter.smartqq.client.SmartQQClient
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
    HashMap<Integer, Player> playerHashMap = new HashMap<Integer, Player>();

    public BattleFacade(){

    }

    void onMessage(SmartQQClient client, Message message) {

    }

    void onGroupMessage(SmartQQClient client, GroupMessage message) {
        CommandFacade.useCommand(client, message)
        PlayerDailyReward.rewardBySpeak(client, message)
    }

    void onDiscussMessage(SmartQQClient client, DiscussMessage message) {

    }






}
