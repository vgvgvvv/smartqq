package com.resetoter.smartqq.spring

import com.resetoter.smartqq.spring.bean.SmartQQConfig
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.support.ClassPathXmlApplicationContext

/**
 * Created by Administrator on 2017/5/10.
 */
@Singleton
class ApplicationContextHolder {

    @Delegate
    final AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext(SmartQQConfig.class);

}
