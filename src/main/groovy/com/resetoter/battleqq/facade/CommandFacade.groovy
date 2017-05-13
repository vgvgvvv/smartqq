package com.resetoter.battleqq.facade

import com.resetoter.battleqq.Entity.PlayerEx
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
            client.sendMessageToGroup(message.groupId, "@$nickName 你消耗了500点积分以复活")
            player.point -= 500
            player.hp = player.getMaxHp()
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
            mapper.insert(PlayerEx.getDefaultPlayerInfo(nickName))
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

    @BattleCommand(commandName = "我刚刚怎么死的啊？")
    static LastKiller(SmartQQClient client, GroupMessage message){
        if(message.getContent() != "我刚刚怎么死的啊？")
            return

        def mapper = ApplicationContextHolder.instance.getBean(PlayerInfoMapper.class)
        String nickName = Facade.receiver.getGroupUserNick(message)
        PlayerInfo player = mapper.selectByPrimaryKey(nickName)

        if(player.lastkiller == null){
            client.sendMessageToGroup(message.groupId,"@$nickName 你还没死过！")
            return
        }

        client.sendMessageToGroup(message.groupId,"@$nickName 上次杀死你的是 @${player.lastkiller}！")
    }

    static int powerCost = 1000;
    @BattleCommand(commandName = "增加力量：")
    static AddPower(SmartQQClient client, GroupMessage message){
        String content = message.getContent()
        if(!content.startsWith("增加力量："))
            return

        def mapper = ApplicationContextHolder.instance.getBean(PlayerInfoMapper.class)
        String nickName = Facade.receiver.getGroupUserNick(message)
        PlayerInfo player = mapper.selectByPrimaryKey(nickName)

        Integer addPower = -1;
        try{
            String num = content.substring(5)
            addPower = Integer.parseInt(num)
        }catch (Exception e){
            logger.error(e.message, e)
            client.sendMessageToGroup(message.groupId,"@$nickName 格式是 增加力量：数字 才行")
            return
        }

        if(addPower < 0){
            client.sendMessageToGroup(message.groupId,"@$nickName 不能小于零！")
            return
        }

        if(player.point < powerCost * addPower){
            client.sendMessageToGroup(message.groupId,"@$nickName 你没钱啊！")
            return
        }

        player.point -= powerCost * addPower
        player.power += addPower
        player.hp = player.maxHp

        mapper.updateByPrimaryKey(player)
        client.sendMessageToGroup(message.groupId,"@$nickName 花费了 ${powerCost * addPower} 点积分提升了 ${addPower} 点力量")
    }

    static int speedCost = 1000;
    @BattleCommand(commandName = "增加速度：")
    static AddSpeed(SmartQQClient client, GroupMessage message){
        String content = message.getContent()
        if(!content.startsWith("增加速度："))
            return

        def mapper = ApplicationContextHolder.instance.getBean(PlayerInfoMapper.class)
        String nickName = Facade.receiver.getGroupUserNick(message)
        PlayerInfo player = mapper.selectByPrimaryKey(nickName)

        Integer addSpeed = -1;
        try{
            addSpeed = Integer.parseInt(content.substring(5))
        }catch (Exception e){
            logger.error(e.message, e)
            client.sendMessageToGroup(message.groupId,"@$nickName 格式是 增加速度：数字 才行")
            return
        }

        if(addSpeed < 0){
            client.sendMessageToGroup(message.groupId,"@$nickName 不能小于零！")
            return
        }

        if(player.point < speedCost * addSpeed){
            client.sendMessageToGroup(message.groupId,"@$nickName 你没钱啊！")
            return
        }

        player.point -= speedCost * addSpeed
        player.speed += addSpeed
        player.hp = player.maxHp

        mapper.updateByPrimaryKey(player)
        client.sendMessageToGroup(message.groupId,"@$nickName 花费了 ${speedCost * addSpeed} 点积分提升了 ${addSpeed} 点速度")
    }

    static int defCost = 1000;
    @BattleCommand(commandName = "增加防御：")
    static AddDef(SmartQQClient client, GroupMessage message){
        String content = message.getContent()
        if(!content.startsWith("增加防御："))
            return

        def mapper = ApplicationContextHolder.instance.getBean(PlayerInfoMapper.class)
        String nickName = Facade.receiver.getGroupUserNick(message)
        PlayerInfo player = mapper.selectByPrimaryKey(nickName)

        Integer addDef = -1;
        try{
            addDef = Integer.parseInt(content.substring(5))
        }catch (Exception e){
            logger.error(e.message, e)
            client.sendMessageToGroup(message.groupId,"@$nickName 格式是 增加防御：数字 才行")
            return
        }

        if(addDef < 0){
            client.sendMessageToGroup(message.groupId,"@$nickName 不能小于零！")
            return
        }

        if(player.point < defCost * addDef){
            client.sendMessageToGroup(message.groupId,"@$nickName 你没钱啊！")
            return
        }

        player.point -= defCost * addDef
        player.def += addDef
        player.hp = player.maxHp

        mapper.updateByPrimaryKey(player)
        client.sendMessageToGroup(message.groupId,"@$nickName 花费了 ${defCost * addDef} 点积分提升了 ${addDef} 点防御")
    }
}
