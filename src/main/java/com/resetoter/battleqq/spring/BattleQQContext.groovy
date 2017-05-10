package com.resetoter.battleqq.spring

import com.resetoter.battleqq.spring.bean.BattleQQConfig
import org.springframework.context.annotation.AnnotationConfigApplicationContext

/**
 * Created by Administrator on 2017/5/10.
 */
@Singleton
class BattleQQContext {

    public final AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext(BattleQQConfig.class)
}
