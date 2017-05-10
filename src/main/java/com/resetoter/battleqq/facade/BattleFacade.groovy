package com.resetoter.battleqq.facade

import com.resetoter.battleqq.logic.BattleCommand
import com.resetoter.battleqq.mybatis.model.Player
import com.resetoter.smartqq.callback.MessageCallback
import com.resetoter.smartqq.model.DiscussMessage
import com.resetoter.smartqq.model.GroupMessage
import com.resetoter.smartqq.model.Message

/**
 * Created by 35207 on 2017/5/9 0009.
 */
class BattleFacade implements MessageCallback{

    //储存所有玩家
    Dictionary<String, Player> playerDictionary = new HashMap<>();


    @Override
    void onMessage(Message message) {

    }

    @Override
    void onGroupMessage(GroupMessage message) {

    }

    @Override
    void onDiscussMessage(DiscussMessage message) {

    }




    @BattleCommand(commandName = "干死")
    def Attack(GroupMessage message){

    }

    @BattleCommand(commandName = "创建角色！")
    def CreateCharacter(GroupMessage message){

    }


}
