package com.gaeasoft.project.batch;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuartzJob implements Job {
	
	@Autowired
    private BatchJobLauncher batchJobLauncher;
    private static final Logger log = LoggerFactory.getLogger(QuartzJob.class);

	// 주입된 배치 실행기를 사용하여 배치 작업을 실행
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
 		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
    	if (batchJobLauncher == null) {
            log.error("BatchJobLauncher 주입 실패: " + methodName);
            return;
        }
    	
    	try {
            log.info("배치 작업 실행 시작: " + methodName);
            batchJobLauncher.runBatchJob();
            log.info("배치 작업 실행 완료: " + methodName);
        } catch (Exception e) {
            log.error("배치 작업 실행 실패: " + methodName + e);
            throw new JobExecutionException("Failed to run batch job", e);
        }
    }

}