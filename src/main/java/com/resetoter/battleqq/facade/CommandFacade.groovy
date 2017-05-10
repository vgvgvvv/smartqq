package com.resetoter.battleqq.facade

import com.resetoter.battleqq.logic.BattleCommand
import com.resetoter.battleqq.mybatis.dao.PlayerMapper
import com.resetoter.smartqq.client.SmartQQClient
import com.resetoter.smartqq.facade.Facade
import com.resetoter.smartqq.model.GroupMessage
import com.resetoter.smartqq.spring.ApplicationContextHolder
import org.apache.log4j.Logger
import org.springframework.stereotype.Component

/**
 * Created by Administrator on 2017/5/10.
 */
@Component
class CommandFacade {

    static Logger logger = Logger.getLogger(CommandFacade.class);

    static useCommand(SmartQQClient client, GroupMessage message){
        CommandFacade facade = ApplicationContextHolder.instance.getBean(CommandFacade.class)
        CommandFacade.class.methods.each {
            logger.info(it.name)
            BattleCommand command = it.getAnnotation(BattleCommand.class)
            if(command == null){
                logger.info("不存在")
                return
            }
            if(message.content.startsWith(command.commandName())){
                it.invoke(null, client, message)
            }
        }
    }

    @BattleCommand(commandName = "干死")
    static Attack(SmartQQClient client, GroupMessage message){
        logger.info("发送者为" + Facade.client.getQQById(message.userId))
    }

    @BattleCommand(commandName = "创建角色！")
    static CreateCharacter(SmartQQClient client, GroupMessage message){
        logger.info("发送者为" + Facade.client.getQQById(message.userId))
    }

    @BattleCommand(commandName = "GameState")
    static CheckState(SmartQQClient client, GroupMessage message){
        if(message.getContent() != "GameState")
            return
        client.getGroupInfo(message.groupId).users.each {
            logger.info(it.toString())
        }
    }

}
