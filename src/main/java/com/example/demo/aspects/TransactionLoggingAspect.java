package com.example.demo.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TransactionLoggingAspect {

    private static final Logger LOG = LoggerFactory.getLogger(TransactionLoggingAspect.class);

    @Before("execution(@org.springframework.transaction.annotation.Transactional * com.example.demo.service.*.*(..))")
    public void logTransactionStart() {
        LOG.info("Transaction started...");
    }
}
