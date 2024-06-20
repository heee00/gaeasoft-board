package com.gaeasoft.project.batch;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gaeasoft.project.controller.BoardController;
import com.gaeasoft.project.service.BoardService;

@Component
public class BatchJobLauncher  {
	
	@Autowired
    private BoardService boardService;
    private static final Logger log = LoggerFactory.getLogger(BoardController.class);
	
	@Autowired
    public BatchJobLauncher(BoardService boardService) {
        this.boardService = boardService;
    }
	
	// 매일 00:00:00에 실행
	@Scheduled(cron = "0 0 0 * * *") 
    public void runBatchJob() throws Exception {
    	String className = this.getClass().getSimpleName();
 		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
    	LocalDateTime deleteDay = LocalDateTime.now().minusDays(7);
        log.info(className + "-" + methodName + ": 해당 날짜 기준 삭제 배치 작업 실행[" + deleteDay + "]");
        try {
            boardService.deleteBatchedNoticeArticle(deleteDay);
            log.info(className + "-" + methodName + ": 배치 작업 완료.");
        } catch (Exception e) {
            log.error(className + "-" + methodName + ": 배치 작업 실행 실패", e);
            System.err.println("Failed to run batch job: " + e.getMessage());
        }
    }
    
}
