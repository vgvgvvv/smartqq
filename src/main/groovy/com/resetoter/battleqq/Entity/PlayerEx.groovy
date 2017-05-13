package com.resetoter.battleqq.Entity

import com.resetoter.battleqq.mybatis.model.PlayerInfo
import com.resetoter.smartqq.util.Util

/**
 * Created by admin on 2017/5/13 0013.
 */
class PlayerEx {

    static extension(){
        PlayerInfo.metaClass.getMaxHp = {
            ((PlayerInfo)delegate).power * 15 + 500
        }
    }

    static getDefaultPlayerInfo(nickName){
        new PlayerInfo(playername: nickName, point: 0, power: 10, speed: 10, hp: 100, def: 10)
    }

    static getHurt(PlayerInfo attacker, PlayerInfo beAttacked){
        if(attacker.power - beAttacked.def <= 0){
            Util.random.nextInt(attacker.speed * 50)
        }else{
            (attacker.power - beAttacked.def) * 100 + attacker.speed * (Util.random.nextInt(100) - 50)
        }
    }

}
