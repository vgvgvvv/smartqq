package com.resetoter.battleqq.logic

import com.resetoter.battleqq.Entity.Player
import com.resetoter.battleqq.Entity.PlayerEx
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

    //线程池
    static ExecutorService cachedThreadPool = Executors.newCachedThreadPool()
    //正在战斗的玩家列表
    static ArrayList<String> battlePlayerList = new ArrayList<String>()
    //
    static playerMapper = ApplicationContextHolder.instance.getBean(PlayerInfoMapper.class)

    //两个玩家
    Player p1
    Player p2

    //获取回合数
    public int turn;

    SmartQQClient client
    GroupMessage message
    String nickName
    String enemyName

    PlayerBattle(SmartQQClient client, GroupMessage message, String nickName, String enemyName){
        this.client = client
        this.message = message
        this.nickName = nickName
        this.enemyName = enemyName
    }

    /**
     * 战斗
     * @return
     */
    def battle() {

        turn = 0
        if (!checkBattle())
            return

        client.sendMessageToGroup(message.groupId, "@$nickName 攻击了 @$enemyName 战斗开始！")

        cachedThreadPool.execute {

            battlePlayerList.add(nickName)
            battlePlayerList.add(enemyName)

            while (p1.hp > 0 && p2.hp > 0) {

                attack(p1, p2)
                if(isP1Win())
                    break

                attack(p2, p1)

                if(isP2Win())
                    break

                Thread.sleep(1000)

                turn++

            }

            playerMapper.updateByPrimaryKey(p1.info)
            playerMapper.updateByPrimaryKey(p2.info)

            battlePlayerList.remove(nickName)
            battlePlayerList.remove(enemyName)
        }
    }

    /**
     * 检查是否可以加入战斗
     * @param client
     * @param message
     * @param nickName
     * @param enemyName
     * @return
     */
    boolean checkBattle() {
        if (nickName == enemyName) {
            client.sendMessageToGroup(message.groupId, "@$nickName 你干嘛要干自己？")
            return false
        }

        if (battlePlayerList.contains(nickName)) {
            client.sendMessageToGroup(message.groupId, "@$nickName 你已经在战斗中了！")
            return false
        }

        if (battlePlayerList.contains(enemyName)) {
            client.sendMessageToGroup(message.groupId, "@$nickName $enemyName 已经在战斗中了！你想干蛤？")
            return false
        }

        def enemy = Facade.receiver.getGroupInfoFromID(message.groupId).users.find {
            it.nick == enemyName || it.card == enemyName
        }

        p1 = new Player(nickName)
        if (p1.hp <= 0) {
            client.sendMessageToGroup(message.groupId, "@$nickName 死人怎么战斗？笑死大牙了！")
            return false
        }
        if (enemy == null) {
            client.sendMessageToGroup(message.groupId, "@$nickName 查无此人啊？？？")
            return false
        }
        p2 = new Player(enemyName)
        if (p2.info == null) {
            client.sendMessageToGroup(message.groupId, "@$nickName 不存在此人！")
            return false
        }
        if (p2.hp <= 0) {
            client.sendMessageToGroup(message.groupId, "@$nickName 你没有办法鞭尸！")
            return false
        }

        return true
    }

    /**
     * P1是否胜利
     * @param client
     * @param message
     * @param nickName
     * @param enemyName
     * @return
     */
    boolean  isP1Win(){
        if (p2.hp <= 0) {
            client.sendMessageToGroup(message.groupId,
                    "@$enemyName 你死了 @$nickName 是胜利者！胜利者获得500点积分，并且回复生命值！")
            p1.point += 500
            p1.hp = PlayerEx.getMaxHp()
            p2.lastkiller = nickName
            return true
        }
        return false
    }

    /**
     * P2是否胜利
     * @param client
     * @param message
     * @param nickName
     * @param enemyName
     * @return
     */
    boolean isP2Win(){
        if (p1.hp <= 0) {
            client.sendMessageToGroup(message.groupId,
                    "@$nickName 你死了 @$enemyName 是胜利者！胜利者获得500点积分，并且回复生命值！")
            p2.point += 500
            p2.hp = PlayerEx.getMaxHp()
            p1.lastkiller = enemyName
            return true
        }
        return false
    }

    /**
     * 攻击
     * @param attacker
     * @param beAttack
     * @return
     */
    def attack(Player attacker, Player beAttack){
        int p1hurt = PlayerEx.getHurt(attacker, beAttack)
        beAttack.hp -= p1hurt
        client.sendMessageToGroup(message.groupId, "@$nickName 收到了 $p1hurt 点伤害")
    }

}
