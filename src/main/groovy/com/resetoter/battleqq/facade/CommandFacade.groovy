package com.resetoter.battleqq.facade

import com.resetoter.battleqq.logic.BattleCommand
import com.resetoter.battleqq.logic.PlayerBattle
import com.resetoter.battleqq.mybatis.dao.PlayerInfoMapper
import com.resetoter.battleqq.mybatis.model.PlayerInfo
import com.resetoter.smartqq.client.SmartQQClient
import com.resetoter.smartqq.facade.Facade
import com.resetoter.smartqq.model.GroupMessage
import com.resetoter.smartqq.spring.ApplicationContextHolder
import org.apache.log4j.Logger

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2017/5/10.
 */
class CommandFacade {

    static Logger logger = Logger.getLogger(CommandFacade.class);

    static HashMap<String, Method> commandMethodDict = new HashMap<String, Method>();

    static useCommand(SmartQQClient client, GroupMessage message){

        if(commandMethodDict.size() == 0){
            CommandFacade.class.methods.each {
                BattleCommand command = it.getAnnotation(BattleCommand.class)
                if(command == null){
                    return
                }else {
                    commandMethodDict.put(command.commandName(), it);
                }
            }
        }

        commandMethodDict.keySet().each {
            if(message.content.startsWith(it)){
                commandMethodDict[it].invoke(null, client, message)
            }
        }

    }

    @BattleCommand(commandName = "干死")
    static Attack(SmartQQClient client, GroupMessage message){
        if(!message.content.startsWith("干死 @"))
            return

        String nickName = Facade.receiver.getGroupUserNick(message)
        String enemyName = message.content.substring(4).trim();

        PlayerBattle.battle(client, message, nickName, enemyName)
    }

    @BattleCommand(commandName = "复活！")
    static ReBorn(SmartQQClient client, GroupMessage message){
        if(message.getContent() != "复活！")
            return

        def mapper = ApplicationContextHolder.instance.getBean(PlayerInfoMapper.class)
        String nickName = Facade.receiver.getGroupUserNick(message)
        PlayerInfo player = mapper.selectByPrimaryKey(nickName)

        if(player.point <= 100){
            client.sendMessageToGroup(message.groupId, "@$nickName 没积分啊！穷逼")
            return
        }
        if(player.hp > 0){
            client.sendMessageToGroup(message.groupId, "@$nickName 活着你复活个屁，的丢噶")
            return
        }

        if(player.is(null)){
            client.sendMessageToGroup(message.groupId, "@$nickName 创建角色先啊！")
            return
        }else{
            client.sendMessageToGroup(message.groupId, "@$nickName 你消耗了100点积分以复活")
            player.point -= 100
            player.hp = 100
            mapper.updateByPrimaryKey(player)
        }

    }

    @BattleCommand(commandName = "创建角色！")
    static CreateCharacter(SmartQQClient client, GroupMessage message){
        if(message.getContent() != "创建角色！")
            return
        def mapper = ApplicationContextHolder.instance.getBean(PlayerInfoMapper.class)
        String nickName = Facade.receiver.getGroupUserNick(message)
        if(mapper.selectByPrimaryKey(nickName).is(null)){
            mapper.insert(new PlayerInfo(playername: nickName, point: 0, power: 1, speed: 1, hp: 100, def: 1))
            client.sendMessageToGroup(message.groupId, "@$nickName 角色创建成功！你的账号就是你在群中的名字！")
        }else{
            client.sendMessageToGroup(message.groupId, "@$nickName 你已经拥有角色了！傻逼")
        }


    }

    @BattleCommand(commandName = "查看状态")
    static CheckState(SmartQQClient client, GroupMessage message){
        if(message.getContent() != "查看状态")
            return
        def mapper = ApplicationContextHolder.instance.getBean(PlayerInfoMapper.class)
        String nickName = Facade.receiver.getGroupUserNick(message)
        PlayerInfo player = mapper.selectByPrimaryKey(nickName)
        if(player.is(null)){
        }else{
            client.sendMessageToGroup(message.groupId,
                    "@$nickName \n" +
                            "你角色目前的积分为:" + player.point + "点\n" +
                            "力量为：" + player.power + "点\n" +
                            "防御为：" + player.def + "点\n" +
                            "速度为: " + player.speed + "点\n" +
                            "当前生命值为" + player.hp + "点\n"
            )
        }
    }

}
