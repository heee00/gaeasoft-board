package com.gaeasoft.project.batch.config;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

@Configuration
@EnableScheduling
public class QuartzJobConfig extends SpringBeanJobFactory {
	
	@Autowired
	private ApplicationContext applicationContext;
	
	// Quartz에서 사용할 Job 클래스를 Spring Bean으로 자동 주입하는 JobFactory 정의
    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        Object job = super.createJobInstance(bundle);
        applicationContext.getAutowireCapableBeanFactory().autowireBean(job);
        return job;
    }

}
