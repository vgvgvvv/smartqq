package com.resetoter.battleqq.facade

import com.resetoter.battleqq.logic.BattleCommand
import com.resetoter.smartqq.model.GroupMessage

/**
 * Created by Administrator on 2017/5/10.
 */
class CommandFacade {

    @BattleCommand(commandName = "干死")
    def Attack(GroupMessage message){

    }

    @BattleCommand(commandName = "创建角色！")
    def CreateCharacter(GroupMessage message){

    }

}
