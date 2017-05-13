package com.resetoter.battleqq.logic

import com.resetoter.battleqq.Entity.PlayerEx
import com.resetoter.battleqq.mybatis.dao.PlayerInfoMapper
import com.resetoter.battleqq.mybatis.model.PlayerInfo
import com.resetoter.smartqq.client.SmartQQClient
import com.resetoter.smartqq.facade.Facade
import com.resetoter.smartqq.model.GroupMessage
import com.resetoter.smartqq.spring.ApplicationContextHolder

/**
 * Created by 35207 on 2017/5/11 0011.
 */
class PlayerDailyReward {

    static rewardBySpeak(SmartQQClient client, GroupMessage message){
        def mapper = ApplicationContextHolder.instance.getBean(PlayerInfoMapper.class)
        String nickName = Facade.receiver.getGroupUserNick(message)
        PlayerInfo player = mapper.selectByPrimaryKey(nickName)
        if(player.is(null)){
            mapper.insert(PlayerEx.getDefaultPlayerInfo(nickName))
            client.sendMessageToGroup(message.groupId, "@{$nickName} 你第一次讲话，被视作创建角色！")
        }else{
            player.point = player.point + message.content.length()
            mapper.updateByPrimaryKeySelective(player)
        }
    }

}
