package com.ignitiv.service;

import java.nio.file.*;
import java.time.Duration;

import org.springframework.batch.core.*;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class FileMoveListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {

        String fileName =
                jobExecution.getJobParameters().getString("fileName");
        
        

        System.out.println("=================================================");
        System.out.println("BATCH STARTED");
        System.out.println("File Name  : " + fileName);
        System.out.println("Start Time : " + jobExecution.getStartTime());
        System.out.println("=================================================");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {

        String filePath = jobExecution.getJobParameters().getString("filePath");
        

        if (filePath == null) {
            System.out.println("No filePath found. Skipping file move.");
            return;
        }

        String fileName = jobExecution.getJobParameters().getString("fileName");
        Duration duration = Duration.between(jobExecution.getStartTime(),jobExecution.getEndTime());

        System.out.println("-------------------------------------------------");
        System.out.println("BATCH FINISHED");
        System.out.println("File Name  : " + fileName);
        System.out.println("Status     : " + jobExecution.getStatus());
        System.out.println("Duration   : " + duration.toSeconds() + " seconds");
        System.out.println("-------------------------------------------------");

        moveFile(filePath, jobExecution.getStatus());
    }

    private void moveFile(String filePath, BatchStatus status) {
        try {

            Files.createDirectories(Paths.get("processed"));
            Files.createDirectories(Paths.get("error"));

            Path source = Paths.get(filePath);

            if (status == BatchStatus.COMPLETED) {
                Files.move(source,Paths.get("processed", source.getFileName().toString()),StandardCopyOption.REPLACE_EXISTING);
                System.out.println("File moved to processed folder.");
            } else {
                Files.move(source,Paths.get("error", source.getFileName().toString()),StandardCopyOption.REPLACE_EXISTING);
                System.out.println("File moved to error folder.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}