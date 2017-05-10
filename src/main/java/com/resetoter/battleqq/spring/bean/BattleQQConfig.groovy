package com.resetoter.battleqq.spring.bean

import com.resetoter.battleqq.facade.BattleFacade
import com.resetoter.battleqq.facade.CommandFacade
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Created by Administrator on 2017/5/10.
 */
@Configuration
class BattleQQConfig {

    @Bean
    BattleFacade facade(){
        new BattleFacade();
    }

    @Bean
    CommandFacade commandFacade(){
        new CommandFacade()
    }

}
