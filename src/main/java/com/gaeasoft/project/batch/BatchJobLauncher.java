package com.gaeasoft.project.batch;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gaeasoft.project.service.BoardService;

@Component
public class BatchJobLauncher  {
	
	@Autowired
    private BoardService boardService;
	
	@Autowired
    public BatchJobLauncher(BoardService boardService) {
        this.boardService = boardService;
    }
	
	// 매일 00:00:00에 실행
    @Scheduled(cron = "0 0 0 * * *") 
    public void runBatchJob() throws Exception {
    	LocalDateTime deleteDay = LocalDateTime.now().minusDays(7);
        System.out.println("Running batch job to delete articles older than: " + deleteDay);
        try {
            boardService.deleteBatchedNoticeArticle(deleteDay);
            System.out.println("Batch job completed.");
        } catch (Exception e) {
            System.err.println("Failed to run batch job: " + e.getMessage());
        }
    }
    
}
