package com.resetoter.smartqq.facade

import com.resetoter.battleqq.mybatis.dao.PlayerMapper
import com.resetoter.battleqq.mybatis.model.Player
import com.resetoter.smartqq.spring.ApplicationContextHolder
import org.springframework.transaction.TransactionDefinition
import org.springframework.transaction.TransactionStatus
import org.springframework.transaction.support.DefaultTransactionDefinition

/**
 * Created by 35207 on 2017/5/8 0008.
 */
class TestFacade {

    static main(args){
        System.out.println("Hello")
        try{
            PlayerMapper mapper = ApplicationContextHolder.instance.getBean(PlayerMapper.class);
            Player player = new Player();
            player.qqnumber = 101000;
            player.point = 0;
            mapper.insert(player)
        }catch (e){
            System.out.print(e);
        }

    }

}
