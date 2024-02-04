## P3. 호갱노노 배치 시스템 만들기

<br/>

### Ch 01. 실거래가 시스템 요구사항 분석 - 01. 요구사항 분석하기

### Ch 02. Batch 프로젝트 설계 - 02. 프로젝트 설계하기

### Ch 03. Batch 프로그램 개발 - 03. 동 배치 - 설계와 엔티티 구현

### Ch 03. Batch 프로그램 개발 - 04. 동 배치 - Job, Step, FlatFileItemReader
실행 전 Edit Configuration... -> Program arguments
>--spring.batch.job.names=lawdInsertJob -filePath=LAWD_CODE_SAMPLE.txt

<br/>

### Ch 03. Batch 프로그램 개발 - 04. 동 배치 - Job, Step, FlatFileItemReader

### Ch 03. Batch 프로그램 개발 - 05. 동 배치 - Service, ItemWriter, 실행
실행 전 Edit Configuration... -> Program arguments
>--spring.batch.job.names=lawdInsertJob -filePath=LAWD_CODE.txt

### Ch 03. Batch 프로그램 개발 - 06. 실거래가 배치 - API 응답 분석

### Ch 03. Batch 프로그램 개발 - 07. 실거래가 배치 - XML 파일 ItemReader

### Ch 03. Batch 프로그램 개발 - 08. 실거래가 배치 - Job, Step 완성하기
실행 전 Edit Configuration... -> Program arguments
>--spring.batch.job.names=aptDealInsertJob -filePath=apartment-api-response.xml

### Ch 03. Batch 프로그램 개발 - 09. 실거래가 배치 - StatEventItemReader 부가설명

### Ch 03. Batch 프로그램 개발 - 10. 실거래가 배치 - API 응답 ItemReader

### Ch 03. Batch 프로그램 개발 - 11. 실거래가 배치 - JobParameter로 API 호출

### Ch 03. Batch 프로그램 개발 - 12. 실거래가 배치 - CompositeJobParametersValidator

>--spring.batch.job.names=aptDealInsertJob -lawdCd=41135 -yearMonth=202107

올바른 날짜 형식이 아닙니다. yyyy-MM 형식이어야 합니다.

>--spring.batch.job.names=aptDealInsertJob -lawdCd=411350 -yearMonth=2024-01

5자리 문자열이어야 합니다.

### Ch 03. Batch 프로그램 개발 - 13. 실거래가 배치 - 구 코드 쿼리 작성하기

### Ch 03. Batch 프로그램 개발 - 14. 실거래가 배치 - JPA @Query

### Ch 03. Batch 프로그램 개발 - 15. 실거래가 배치 - 구 코드 읽는 Step

### Ch 03. Batch 프로그램 개발 - 16. 실거래가 배치 - 두 개의 Reader를 어떻게 처리할까


