package com.ignitiv.service;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class FileWatchService {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    @PostConstruct
    public void watchFolder() {
    		new Thread(() -> {
            try {
                WatchService watchService = FileSystems.getDefault().newWatchService();

                Path path = Paths.get("input");
                path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

                while (true) {

                    WatchKey key = watchService.take();
                    for (WatchEvent<?> event : key.pollEvents()) {
                        Path filePath = path.resolve((Path) event.context());
                        System.out.println("New file detected: " + filePath);
                        
                        Thread.sleep(5000);
                        
                        JobParameters params = new JobParametersBuilder()
                                .addString("filePath", filePath.toString())
                                .addString("fileName", filePath.getFileName().toString())
                                .toJobParameters();
                        try {
                            jobLauncher.run(job, params);
                        } catch (Exception e) {
                            System.out.println("Job already executed or failed: " + e.getMessage());
                        }                    }
                    key.reset();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
