package com.resetoter.battleqq.Entity

import com.resetoter.battleqq.mybatis.model.PlayerInfo
import com.resetoter.smartqq.util.Util

/**
 * Created by admin on 2017/5/13 0013.
 */
class PlayerEx {

    static extension(){

    }

    static int getMaxHp(){
        500
    }

    static getDefaultPlayerInfo(nickName){
        new PlayerInfo(playername: nickName, point: 0, power: 10, speed: 10, hp: 500, def: 10)
    }

    static getHurt(PlayerInfo attacker, PlayerInfo beAttacked){
        100
    }

}
