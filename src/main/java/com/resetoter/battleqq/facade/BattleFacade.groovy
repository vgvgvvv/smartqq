package com.resetoter.battleqq.facade

import com.resetoter.battleqq.logic.BattleCommand
import com.resetoter.battleqq.mybatis.model.Player
import com.resetoter.smartqq.callback.MessageCallback
import com.resetoter.smartqq.client.SmartQQClient
import com.resetoter.smartqq.model.DiscussMessage
import com.resetoter.smartqq.model.GroupMessage
import com.resetoter.smartqq.model.Message

/**
 * Created by 35207 on 2017/5/9 0009.
 */
class BattleFacade{

    //储存所有玩家
    //Dictionary<String, Player> playerDictionary = new HashMap<>();


    void onMessage(SmartQQClient client, Message message) {

    }

    void onGroupMessage(SmartQQClient client, GroupMessage message) {

    }

    void onDiscussMessage(SmartQQClient client, DiscussMessage message) {

    }




    @BattleCommand(commandName = "干死")
    def Attack(GroupMessage message){

    }

    @BattleCommand(commandName = "创建角色！")
    def CreateCharacter(GroupMessage message){

    }


}
