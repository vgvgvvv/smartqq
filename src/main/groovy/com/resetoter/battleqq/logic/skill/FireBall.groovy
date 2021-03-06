package com.resetoter.battleqq.logic.skill

import com.resetoter.battleqq.Entity.Player
import com.resetoter.smartqq.util.Util

/**
 * Created by 35207 on 2017/5/16 0016.
 */
class FireBall extends BaseSkill{

    int hurt

    FireBall(){

    }

    @Override
    def whenUse(Player user, Player target) {
        hurt = Util.random.nextInt(100)+100
        target.hp -= hurt
    }

    @Override
    def getDescription(Player attacker, Player target) {
        return super.getDescription(attacker, target) + "，造成了" + hurt + "点伤害"
    }
}
