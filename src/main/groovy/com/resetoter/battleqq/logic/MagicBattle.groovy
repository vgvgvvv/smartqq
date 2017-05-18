package com.resetoter.battleqq.logic

import com.resetoter.battleqq.Entity.Player
import com.resetoter.battleqq.Entity.PlayerEx
import com.resetoter.battleqq.logic.skill.BaseSkill
import com.resetoter.battleqq.mybatis.dao.PlayerInfoMapper
import com.resetoter.battleqq.mybatis.model.PlayerInfo
import com.resetoter.smartqq.client.SmartQQClient
import com.resetoter.smartqq.facade.Facade
import com.resetoter.smartqq.manager.ConfigManager
import com.resetoter.smartqq.model.GroupMessage
import com.resetoter.smartqq.spring.ApplicationContextHolder
import com.resetoter.smartqq.util.Util

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Created by 35207 on 2017/5/16 0016.
 */
class MagicBattle extends PlayerBattle {

    Set<String> skillNames
    HashMap<String, BaseSkill> skillHashMap = new HashMap<String, BaseSkill>()
    static final String skillPackage = "com.resetoter.battleqq.logic.skill."

    MagicBattle(SmartQQClient client, GroupMessage message, String nickName, String enemyName) {
        super(client, message, nickName, enemyName)
        ConfigManager.loadConfig(BaseSkill.SKILL_CONFIG)
        skillNames = ConfigManager.getConfig(BaseSkill.SKILL_CONFIG).stringPropertyNames()
    }

    @Override
    def attack(Player attacker, Player beAttack) {
        BaseSkill skill = getRamdomSkill(attacker)
        skill.use(attacker, beAttack)
        client.sendMessageToGroup(message.groupId, skill.getDescription(attacker, beAttack))
    }

    BaseSkill getRamdomSkill(Player attacker){
        String skillName = skillPackage + skillNames[Util.random.nextInt(skillNames.size())]
        if(skillHashMap.containsKey(skillName))
            return skillHashMap[skillName]

        BaseSkill skill = (BaseSkill)Class.forName(skillName).newInstance()
        skillHashMap.put(skillName, skill)

        return skill
    }

}