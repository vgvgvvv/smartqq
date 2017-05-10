package com.resetoter.battleqq.facade

import com.resetoter.battleqq.logic.BattleCommand
import com.resetoter.battleqq.mybatis.dao.PlayerMapper
import com.resetoter.battleqq.mybatis.model.Player
import com.resetoter.battleqq.mybatis.model.PlayerKey
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
            BattleCommand command = it.getAnnotation(BattleCommand.class)
            if(command == null){
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
        if(message.getContent() != "创建角色！")
            return
        def mapper = ApplicationContextHolder.instance.getBean(PlayerMapper.class)
        PlayerKey key = new PlayerKey();
        String nickName = Facade.receiver.getGroupUserNick(message)
        key.setPlayername(nickName)
        if(mapper.selectByName(key).is(null)){
            mapper.insert(new Player(playername: nickName, point: 0))
            client.sendMessageToGroup(message.groupId, "角色创建成功！你的账号就是你在群中的名字！")
        }else{
            client.sendMessageToGroup(message.groupId, "你已经拥有角色了！傻逼")
        }


    }

    @BattleCommand(commandName = "查看状态")
    static CheckState(SmartQQClient client, GroupMessage message){
        if(message.getContent() != "查看状态")
            return
        def mapper = ApplicationContextHolder.instance.getBean(PlayerMapper.class)
        PlayerKey key = new PlayerKey();
        String nickName = Facade.receiver.getGroupUserNick(message)
        key.setPlayername(nickName)
        Player player = mapper.selectByName(key)
        if(player.is(null)){
        }else{
            client.sendMessageToGroup(message.groupId, "你角色目前的积分为:" + player.point + "点")
        }
    }

}
