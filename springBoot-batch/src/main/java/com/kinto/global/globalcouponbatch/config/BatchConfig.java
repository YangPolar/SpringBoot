package com.kinto.global.globalcouponbatch.config;

import com.kinto.global.globalcouponbatch.Model.SystemLogModel;
import com.kinto.global.globalcouponbatch.Processor.SystemLogProcessor;
import com.kinto.global.globalcouponbatch.common.listener.JobCompletionNotificationListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;

/**
 * @author YangLiming
 */
@Configuration
@EnableBatchProcessing
public class BatchConfig {
    private static final Logger log = LoggerFactory.getLogger(BatchConfig.class);

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    private DataSource dataSource;

    @Bean
    public ItemReader<SystemLogModel> reader() {
        return new JdbcCursorItemReaderBuilder<SystemLogModel>()
                .name("cursorItemReader")
                .dataSource(dataSource)
                .sql("SELECT * FROM system_log ORDER BY create_datetime asc LIMIT 10")
                .rowMapper(new BeanPropertyRowMapper<>(SystemLogModel.class))
                .build();
    }

    @Bean
    public SystemLogProcessor processor() {
        return new SystemLogProcessor();
    }

    @Bean
    public ItemWriter<SystemLogModel> writer(){
        return items -> {
            for(SystemLogModel c : items) {
                log.info("Writer: " + c.toString());
            }
        };
    }

    @Bean
    public Job deleteSystemLogJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory.get("deleteSystemLog").incrementer(
                new RunIdIncrementer()).listener(listener).flow(step1).end().build();
    }

    @Bean
    public Step step1(ItemWriter writer) {
        return stepBuilderFactory.get("step1")
                .<SystemLogModel, SystemLogModel>chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer).build();
    }
}
