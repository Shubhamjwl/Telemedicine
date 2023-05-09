package com.nsdl.telemedicine.doctor.batch;

import java.net.MalformedURLException;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.extensions.excel.RowMapper;
import org.springframework.batch.extensions.excel.mapping.BeanWrapperRowMapper;
import org.springframework.batch.extensions.excel.poi.PoiItemReader;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.nsdl.telemedicine.doctor.dto.CsvBulkDoctorRequest;
import com.nsdl.telemedicine.doctor.entity.DoctorRegDtlsEntity;

@Configuration
@EnableBatchProcessing
public class BulkDoctorRegistrationConfig {

	@Bean
	@StepScope
	PoiItemReader<CsvBulkDoctorRequest> reader(@Value("#{jobParameters['fileName']}") String fileName)
			throws MalformedURLException {
		PoiItemReader<CsvBulkDoctorRequest> reader = new PoiItemReader<>();
		reader.setLinesToSkip(1);
		reader.setResource(new FileSystemResource(fileName));
		reader.setStrict(false);
		reader.setRowMapper(excelRowMapper());
		return reader;
	}

	private RowMapper<CsvBulkDoctorRequest> excelRowMapper() {
		BeanWrapperRowMapper<CsvBulkDoctorRequest> rowMapper = new BeanWrapperRowMapper<>();
		rowMapper.setDistanceLimit(0);
		rowMapper.setTargetType(CsvBulkDoctorRequest.class);
		return rowMapper;
	}

	@Bean
	@StepScope
	ItemProcessor<CsvBulkDoctorRequest, DoctorRegDtlsEntity> processor() {
		return new DoctorRegistrationProcessor();
	}

	@Bean
	@StepScope
	ItemWriter<DoctorRegDtlsEntity> writer() {
		return new DoctorRegistrationWriter();
	}

	@Bean
	Step excelFileToDatabaseStep(ItemReader<CsvBulkDoctorRequest> reader,
			ItemProcessor<CsvBulkDoctorRequest, DoctorRegDtlsEntity> processor, ItemWriter<DoctorRegDtlsEntity> writer,
			StepBuilderFactory stepBuilderFactory) {
		return stepBuilderFactory.get("excelFileToDatabaseStep").<CsvBulkDoctorRequest, DoctorRegDtlsEntity>chunk(1)
				.reader(reader).processor(processor).writer(writer).build();
	}

	@Bean
	Job excelFileToDatabaseJob(JobBuilderFactory jobBuilderFactory, @Qualifier("excelFileToDatabaseStep") Step step) {
		return jobBuilderFactory.get("excelFileToDatabaseJob").incrementer(new RunIdIncrementer()).flow(step).end()
				.build();
	}

}
