package com.gaeasoft.project.util;

import java.util.UUID;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Aspect
@Component
public class TransactionLog {
	
	private static final Logger log = LoggerFactory.getLogger(TransactionLog.class);

    @Around("@annotation(transactional)")
    public Object logTransaction(ProceedingJoinPoint joinPoint, Transactional transactional) throws Throwable {
        String transactionId = UUID.randomUUID().toString();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        
        log.info("트랜잭션 실행: " + className  + "-" + methodName +"[ID: " + transactionId + "]");
        try {
            Object result = joinPoint.proceed();
            log.info("트랜잭션 커밋: " + className  + "-" + methodName +"[ID: " + transactionId + "]");
            return result;
        } catch (Throwable t) {
        	log.info("트랜잭션 롤백: " + className  + "-" + methodName +"[ID: " + transactionId + "]");
            throw t;
        }
    }

}
