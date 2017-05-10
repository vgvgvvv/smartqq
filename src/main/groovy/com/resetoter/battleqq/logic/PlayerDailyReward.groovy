package com.resetoter.battleqq.logic

import com.resetoter.battleqq.mybatis.dao.PlayerMapper
import com.resetoter.battleqq.mybatis.model.Player
import com.resetoter.battleqq.mybatis.model.PlayerKey
import com.resetoter.smartqq.client.SmartQQClient
import com.resetoter.smartqq.facade.Facade
import com.resetoter.smartqq.model.GroupMessage
import com.resetoter.smartqq.spring.ApplicationContextHolder

/**
 * Created by 35207 on 2017/5/11 0011.
 */
class PlayerDailyReward {

    static rewardBySpeak(SmartQQClient client, GroupMessage message){
        def mapper = ApplicationContextHolder.instance.getBean(PlayerMapper.class)
        PlayerKey key = new PlayerKey();
        String nickName = Facade.receiver.getGroupUserNick(message)
        key.setPlayername(nickName)
        Player player = mapper.selectByName(key)
        if(player.is(null)){
            mapper.insert(new Player(playername: nickName, point: message.content.length()))
            client.sendMessageToGroup(message.groupId, "你第一次讲话，被视作创建角色！")
        }else{
            player.point = player.point + message.content.length()
            mapper.updateByPrimaryKeySelective(player)
        }
    }

}
