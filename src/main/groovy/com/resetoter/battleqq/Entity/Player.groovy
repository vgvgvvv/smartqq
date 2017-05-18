package com.resetoter.battleqq.Entity

import com.resetoter.battleqq.mybatis.dao.PlayerInfoMapper
import com.resetoter.battleqq.mybatis.model.PlayerInfo
import com.resetoter.smartqq.spring.ApplicationContextHolder

/**
 * Created by 35207 on 2017/5/16 0016.
 */
class Player {

    static playerMapper = ApplicationContextHolder.instance.getBean(PlayerInfoMapper.class)


    @Delegate PlayerInfo info

    Player(String name){
        info = playerMapper.selectByPrimaryKey(name)
    }

}
