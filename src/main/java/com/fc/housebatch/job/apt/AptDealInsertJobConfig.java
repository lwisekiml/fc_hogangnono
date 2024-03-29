package com.fc.housebatch.job.apt;

import com.fc.housebatch.adapter.ApartmentApiResource;
import com.fc.housebatch.core.dto.AptDealDto;
import com.fc.housebatch.core.repository.LawdRepository;
import com.fc.housebatch.core.service.AptDealService;
import com.fc.housebatch.job.validator.YearMonthParameterValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.batch.item.xml.builder.StaxEventItemReaderBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import java.time.YearMonth;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class AptDealInsertJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final ApartmentApiResource apartmentApiResource;

    @Bean
    public Job aptDealInsertJob(
            Step guLawdCdStep,
            Step aptDealInsertStep
    ) {
        return jobBuilderFactory.get("aptDealInsertJob")
                .incrementer(new RunIdIncrementer())
                .validator(new YearMonthParameterValidator())
                .start(guLawdCdStep)
                .on("CONTINUABLE").to(aptDealInsertStep).next(guLawdCdStep)
                .from(guLawdCdStep)
                .on("*").end()
                .end()
                .build();
    }

    @JobScope
    @Bean
    public Step guLawdCdStep(Tasklet guLawdCdTasklet) {
        return stepBuilderFactory.get("guLawdCdStep")
                .tasklet(guLawdCdTasklet)
                .build();
    }

    /**
     * Executioncontext에 저장할 데이터
     * 1. guLawdCd - 구 코드 -> 다음 스텝에서 활용할 값
     * 2. gulLawdCdList - 구 코드 리스트
     * 3. itemCount - 남아있는 구 코드의 갯수
     */
    @StepScope
    @Bean
    public Tasklet guLawdCdTasklet(LawdRepository lawdRepository) {
        return new GuLawdTasklet(lawdRepository);
    }

    @JobScope
    @Bean
    public Step aptDealInsertStep(
            StaxEventItemReader<AptDealDto> aptDealResourceReader,
            ItemWriter<AptDealDto> aptDealWriter
    ) {
        return stepBuilderFactory.get("aptDealInsertStep")
                .<AptDealDto, AptDealDto>chunk(10)
                .reader(aptDealResourceReader)
                .writer(aptDealWriter)
                .build();
    }


    @StepScope
    @Bean
    public StaxEventItemReader<AptDealDto> aptDealResourceReader(
            @Value("#{jobParameters['yearMonth']}") String yearMonth,
            @Value("#{jobExecutionContext['guLawdCd']}") String guLawdCd,
            Jaxb2Marshaller aptDealDtoMarshaller
    ) {
        return new StaxEventItemReaderBuilder<AptDealDto>()
                .name("aptDealResourceReader")
                .resource(apartmentApiResource.getResource(guLawdCd, YearMonth.parse(yearMonth)))
                .addFragmentRootElements("item")
                .unmarshaller(aptDealDtoMarshaller)
                .build();
    }

    @StepScope
    @Bean
    public Jaxb2Marshaller aptDealDtoMarshaller() {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setClassesToBeBound(AptDealDto.class);
        return jaxb2Marshaller;
    }


    @StepScope
    @Bean
    public ItemWriter<AptDealDto> aptDealWriter(AptDealService aptDealService) {
        return items -> {
            items.forEach(aptDealService::upsert);
            System.out.println("============ Writing Completed ============"); // 10개 출력되는 것 확인
        };
    }
}
