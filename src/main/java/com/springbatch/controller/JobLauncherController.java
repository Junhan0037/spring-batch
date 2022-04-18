package com.springbatch.controller;

import java.util.Date;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.batch.BasicBatchConfigurer;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobLauncherController {

    @Autowired private Job job;
    @Autowired private JobLauncher jobLauncher;

    @Qualifier("batchConfigurer")
    @Autowired private BasicBatchConfigurer basicBatchConfigurer;

    /**
     * 비동기적 방식
     */
    @GetMapping("/test")
    public String test() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
            .addString("id", "junhan")
            .addDate("date", new Date())
            .toJobParameters();

        SimpleJobLauncher jobLauncher = (SimpleJobLauncher) basicBatchConfigurer.getJobLauncher();
        jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
        jobLauncher.run(job, jobParameters);

        return "Async Job";
    }

}
