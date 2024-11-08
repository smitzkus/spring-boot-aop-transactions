package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import jakarta.persistence.EntityManagerFactory;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
public class ProcessingService extends AbstractBaseService {

    private static final Logger LOG = LoggerFactory.getLogger(ProcessingService.class);
    private EntityManagerFactory emf;

    public ProcessingService(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void process(int threads) {

        List<Future<Boolean>> results = new ArrayList<>();
        for (int i = 0; i < threads; i++) {
            results.add(process());
        }
        try {
            for (Future<Boolean> b : results) {
                b.get();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Bean("taskExecutor")
    TaskExecutor taskExecutor() {
        return new SimpleAsyncTaskExecutor();
    }

    @Transactional
    @Async("taskExecutor")
    private Future<Boolean> process() {
        EntityManagerHolder emHolder = (EntityManagerHolder) TransactionSynchronizationManager.getResource(emf);
        if (emHolder == null) {
            LOG.warn("EntityManagerHolder should not be null here!");
        } else {
            LOG.info("Successfully received EntityManagerHolder!");
        }

        SharedSessionContractImplementor session = entityManager.unwrap(SharedSessionContractImplementor.class);
        if (session == null) {
            LOG.warn("SharedSessionContractImplementor should not be null here!");
        } else {
            LOG.info("Successfully received hibernate-session!");
        }
        return CompletableFuture.completedFuture(true);
    }
}