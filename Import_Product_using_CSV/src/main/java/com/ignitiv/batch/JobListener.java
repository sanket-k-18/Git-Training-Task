package com.ignitiv.batch;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ignitiv.util.Helpers;

@Component
public class JobListener implements JobExecutionListener {

    @Autowired
    private Helpers helpers;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        System.out.println("-------------PRE-LOADING CACHE BEFORE BATCH STARTS--------------------");
        try {
            helpers.preloadAll();
            System.out.println("---------------------Cache preload complete — batch starting.-----------------------");
        } catch (Exception e) {
            System.out.println("Cache preload FAILED — aborting job: " + e.getMessage());
            e.printStackTrace();
            jobExecution.setStatus(BatchStatus.FAILED);
            jobExecution.setExitStatus(new ExitStatus("FAILED", "Cache preload failed: " + e.getMessage()));        }
    }

}