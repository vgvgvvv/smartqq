package com.resetoter.smartqq.spring

import org.springframework.context.support.ClassPathXmlApplicationContext

/**
 * Created by Administrator on 2017/5/10.
 */
@Singleton
class ApplicationContextHolder {

    @Delegate
    final ClassPathXmlApplicationContext context =
            new ClassPathXmlApplicationContext("classpath:spring/ApplicationContext.xml");

}
