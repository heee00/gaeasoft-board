package com.gaeasoft.project.batch;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuartzJob implements Job {
	
	@Autowired
    private BatchJobLauncher batchJobLauncher;

	// 주입된 배치 실행기를 사용하여 배치 작업을 실행
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
    	if (batchJobLauncher == null) {
            System.err.println("BatchJobLauncher is not injected");
            return;
        }
    	try {
            batchJobLauncher.runBatchJob();
        } catch (Exception e) {
            throw new JobExecutionException("Failed to run batch job", e);
        }
    }

}
