package com.gaeasoft.project.batch.config;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import com.gaeasoft.project.batch.QuartzJob;

@Configuration
public class QuartzJobConfig {
	
	@Autowired
	private ApplicationContext applicationContext;
	 
	// 스케줄러 팩토리 빈 구성, JobDetail과 Trigger 설정
	@Bean
	public SchedulerFactoryBean schedulerFactoryBean() {
	    SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
	    schedulerFactoryBean.setJobFactory(new AutowiringSpringBeanJobFactory());
	    schedulerFactoryBean.setJobDetails(quartzJobDetail());
	    schedulerFactoryBean.setTriggers(jobTrigger());
	    schedulerFactoryBean.start();
	    return schedulerFactoryBean;
	}
	@Bean
	public JobDetail quartzJobDetail() {
	    return JobBuilder.newJob(QuartzJob.class)
			                     .storeDurably()
			                     .withIdentity("QuartzJob")
			                     .withDescription("Invoke Batch Job service...")
			                     .build();
	}
	
	@Bean
	public Trigger jobTrigger() {
	    SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
														                                            .withIntervalInSeconds(60)
														                                            .repeatForever();
	    
	    return TriggerBuilder.newTrigger()
				                         .forJob(quartzJobDetail())
				                         .withIdentity("QuartzJobTrigger")
				                         .withDescription("QuartzJob Trigger")
				                         .withSchedule(scheduleBuilder)
				                         .build();
	}
	
	// Quartz에서 사용할 Job 클래스를 Spring Bean으로 자동 주입하는 JobFactory 정의
	private class AutowiringSpringBeanJobFactory extends SpringBeanJobFactory {
        @Override
        protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
            Object job = super.createJobInstance(bundle);
            applicationContext.getAutowireCapableBeanFactory().autowireBean(job);
            return job;
        }
    }

}
