package com.resetoter.smartqq.facade

import com.resetoter.battleqq.mybatis.dao.PlayerInfoMapper
import com.resetoter.battleqq.mybatis.model.PlayerInfo
import com.resetoter.smartqq.spring.ApplicationContextHolder

/**
 * Created by 35207 on 2017/5/8 0008.
 */
class TestFacade {

    static main(args){
        System.out.println("Hello")
        try{
            PlayerInfoMapper mapper = ApplicationContextHolder.instance.getBean(PlayerInfoMapper.class);
            PlayerInfo player = new PlayerInfo();
            player.qqnumber = 101000;
            player.point = 0;
            mapper.insert(player)
        }catch (e){
            System.out.print(e);
        }

    }

}
