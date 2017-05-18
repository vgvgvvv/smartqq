package com.resetoter.battleqq.logic.skill

import com.resetoter.battleqq.Entity.Player
import com.resetoter.smartqq.facade.Facade
import com.resetoter.smartqq.manager.ConfigManager

/**
 * Created by 35207 on 2017/5/16 0016.
 */
abstract class BaseSkill {

    static SKILL_CONFIG = "/battle/SkillDescription.properties"

    /**
     * 获取描述
     * @return
     */
    def getDescription(Player attacker, Player target){
        String baseDes = ConfigManager.getProperty(SKILL_CONFIG, this.getClass().simpleName)
        baseDes = baseDes.replace("{p1}", attacker.playername)
        baseDes = baseDes.replace("{p2}", target.playername)
        baseDes
    }

    /**
     * 使用技能
     * @param user
     * @param target
     * @return
     */
    def use(Player user, Player target){
        whenUse(user, target)
    }

    /**
     * 使用技能
     * @param user
     * @param target
     * @return
     */
    abstract def whenUse(Player user, Player target);

}
