package com.resetoter.smartqq.spring.bean

import com.resetoter.battleqq.spring.bean.BattleQQConfig
import com.resetoter.smartqq.callback.MessageCallback
import com.resetoter.smartqq.client.SmartQQClient
import com.resetoter.smartqq.constant.ConfigConst
import com.resetoter.smartqq.facade.MessageCallbackFacade
import com.resetoter.smartqq.facade.Receiver
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.ImportResource

/**
 * Created by 35207 on 2017/5/10 0010.
 */
@Configuration
@Import(BattleQQConfig.class)
@ImportResource("classpath:spring/ApplicationContext.xml")
class SmartQQConfig {

    @Bean
    SmartQQClient getClient(MessageCallback facade){
        new SmartQQClient(facade)
    }

    @Bean
    MessageCallbackFacade getCallbackFacade(){
        MessageCallbackFacade.create(ConfigConst.MESSAGECALLBACK_FACADE);
    }

    @Bean
    Receiver getReceiver(SmartQQClient client){
        new Receiver(client)
    }

}
