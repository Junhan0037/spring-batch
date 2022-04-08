package com.springbatch.job.jpa;

import com.springbatch.model.Store;
import com.springbatch.model.StoreHistory;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.springbatch.job.jpa.StoreBackupBatchConfiguration.JOB_NAME;

@Configuration
@ConditionalOnProperty(name = "job.name", havingValue = JOB_NAME) // 존재와 값에 따라 조건부로 빈을 생성
@RequiredArgsConstructor
public class StoreBackupBatchConfiguration {

    public static final String JOB_NAME = "storeBackupBatch";
    private static final String STEP_NAME = JOB_NAME + "Step";

    private final EntityManagerFactory entityManagerFactory;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private static final int CHUNK_SIZE = 1000;
    private static final String ADDRESS_PARAM = null;

    @Bean
    public Job job() {
        return jobBuilderFactory.get(JOB_NAME)
                .start(start())
                .build();
    }

    @Bean
    @JobScope
    public Step start() {
        return stepBuilderFactory.get(STEP_NAME)
                .<Store, StoreHistory>chunk(CHUNK_SIZE)
                .reader(reader(ADDRESS_PARAM))
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<Store> reader(@Value("#{jobParameters[address]}") String address) {
        Map<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("address", address + "%");

        JpaPagingItemReader<Store> reader = new JpaPagingItemReader<>();
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setQueryString("SELECT s FROM Store s WHERE s.address like :address");
        reader.setParameterValues(parameters);
        reader.setPageSize(CHUNK_SIZE);

        return reader;
    }

    public ItemProcessor<Store, StoreHistory> processor() {
        return item -> new StoreHistory(item, item.getProducts(), item.getEmployees());
    }

    public JpaItemWriter<StoreHistory> writer() {
        JpaItemWriter<StoreHistory> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }

}
