package com.gaeasoft.project.batch;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gaeasoft.project.service.BoardService;
import com.gaeasoft.project.service.MemberService;

@Component
public class BatchJobLauncher {
	
	private static final Logger log = LoggerFactory.getLogger(BatchJobLauncher.class);
	@Autowired
    private BoardService boardService;
	@Autowired
	private MemberService memberService;
	
	@Autowired
    public BatchJobLauncher(BoardService boardService) {
        this.boardService = boardService;
    }
	
	// 매일 00:00:00에 실행
	@Scheduled(cron = "0 0 0 * * *")    
    public void runBatchJob() throws Exception {
 		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
    	LocalDateTime deleteDay = LocalDateTime.now().minusDays(7);
    	LocalDateTime withdrawalDay = LocalDateTime.now().minusDays(30);
        log.info("해당 날짜 기준 게시글 삭제 배치 작업 실행: " + methodName + "[" + deleteDay + "]");
        log.info("해당 날짜 기준 회원 삭제 배치 작업 실행: " + methodName + "[" + withdrawalDay + "]");
        
        try {
        	boardService.deleteBatchedNoticeArticle(deleteDay);
       		memberService.deleteBatchedMemberArticles(withdrawalDay);
            memberService.deleteBatchedMember(withdrawalDay);
            log.info("배치 작업 완료: " + methodName);
        
        } catch (Exception e) {
            log.error("배치 작업 실패: " + methodName + e);
            System.err.println("Failed to run batch job: " + e.getMessage());
        }
    }
    
}
