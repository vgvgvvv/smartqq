package com.resetoter.battleqq.logic

import com.resetoter.battleqq.mybatis.dao.PlayerInfoMapper
import com.resetoter.battleqq.mybatis.model.PlayerInfo
import com.resetoter.smartqq.client.SmartQQClient
import com.resetoter.smartqq.facade.Facade
import com.resetoter.smartqq.model.GroupMessage
import com.resetoter.smartqq.spring.ApplicationContextHolder
import com.resetoter.smartqq.util.Util

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Created by 35207 on 2017/5/12 0012.
 */
class PlayerBattle {

    static ExecutorService cachedThreadPool = Executors.newCachedThreadPool()
    static playerMapper = ApplicationContextHolder.instance.getBean(PlayerInfoMapper.class)

    static battle(SmartQQClient client, GroupMessage message, String nickName, String enemyName){

        def enemy = Facade.receiver.getGroupInfoFromID(message.groupId).users.find{
            it.nick == enemyName || it.card == enemyName
        }

        //禁止挑战爱酱
//        if(client.accountInfo.uin== enemy.uin){
//            client.sendMessageToGroup(message.groupId, "@$nickName 嗯侧后捞？")
//            return
//        }

        PlayerInfo p1 = playerMapper.selectByPrimaryKey(nickName)
        if(p1.hp <= 0){
            client.sendMessageToGroup(message.groupId, "@$nickName 死人怎么战斗？笑死大牙了！")
            return
        }
        if(enemy == null){
            client.sendMessageToGroup(message.groupId, "@$nickName 查无此人啊？？？")
            return
        }
        PlayerInfo p2 = playerMapper.selectByPrimaryKey(enemyName)
        if(p2.hp <= 0){
            client.sendMessageToGroup(message.groupId, "@$nickName 你没有办法鞭尸！")
            return
        }

        client.sendMessageToGroup(message.groupId, "@$nickName 攻击了 @$enemyName 战斗开始！")

        cachedThreadPool.execute{

            while (p1.hp > 0 && p2.hp > 0){
                int p2hurt = getHurt(p1, p2)
                p2.hp -= p2hurt
                client.sendMessageToGroup(message.groupId,"@$enemyName 收到了 $p2hurt 点伤害")
                if(p2.hp <= 0){
                    client.sendMessageToGroup(message.groupId,
                            "@$enemyName 你死了 @$nickName 是胜利者！胜利者获得500点积分，并且回复生命值！")
                    p1.point += 500
                    p1.hp = 100
                    break;
                }
                int p1hurt = getHurt(p2, p1)
                p1.hp -= getHurt(p2, p1)
                client.sendMessageToGroup(message.groupId,"@$nickName 收到了 $p1hurt 点伤害")
                if(p1.hp <= 0){
                    client.sendMessageToGroup(message.groupId,
                            "@$nickName 你死了 @$enemyName 是胜利者！胜利者获得500点积分，并且回复生命值！")
                    p2.point += 500
                    p2.hp = 100
                    break;
                }
                Thread.sleep(1000)
            }

            playerMapper.updateByPrimaryKey(p1)
            playerMapper.updateByPrimaryKey(p2)
        }
    }

    private static getHurt(PlayerInfo attacker, PlayerInfo beAttacked){
        if(attacker.power - beAttacked.def <= 0){
            Util.random.nextInt(attacker.speed * 50)
        }else{
            (attacker.power - beAttacked.def) * 100 + Util.random.nextInt(attacker.speed * 100) - 50
        }
    }
}
