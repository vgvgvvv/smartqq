package com.resetoter.battleqq.logic

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

/**
 * Created by 35207 on 2017/5/9 0009.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface BattleCommand {
    /**
     * 命令声明
     * @return
     */
    String commandName();
}