<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:jdbc="http://www.springframework.org/schema/jdbc" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:batch="http://www.springframework.org/schema/batch"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
		http://www.springframework.org/schema/batch http://www.springframework.org/schema/batch/spring-batch-2.1.xsd
		http://www.springframework.org/schema/jdbc http://www.springframework.org/schema/jdbc/spring-jdbc-3.0.xsd">

	
	<import resource="../common/baseConfiguration.xml" />

	<job id="${job_name}" xmlns="http://www.springframework.org/schema/batch">
		 
		<step id="populateIntConf" next="readWrite">
			<tasklet ref="interfaceMasterReaderTasklet" />
		</step>
		 
		<step id="readWrite" >
			<tasklet>
				<chunk reader="itemReader" 
					writer="dummyitemWritter" commit-interval="250">
				
				</chunk>
			</tasklet>
			
			
		</step>
		
		
	</job>
	
	<bean id="itemReader"
		class="com.framework.aside.reader.AttributesBeanFlatFilePeekableItemReader"
		scope="step">
		<property name="reader">
			<bean
				class="org.springframework.batch.item.support.SingleItemPeekableItemReader">
				<property name="delegate" ref="reader1" />
			</bean>

		</property>
		
	</bean>

	<bean id="reader1" class="org.springframework.batch.item.file.FlatFileItemReader"
		scope="step">
		
		<property name="lineMapper" ref="lineMapper" />
	</bean>

	<bean id="lineMapper"
		class="com.framework.aside.mapper.DynamicPatternMatchingCompositeLineMapper"
		scope="step">
		
	</bean>

	<bean id="hashMapRowMapper" class="com.framework.aside.mapper.HashMapRowMapper" />

		
	<bean id="dummyitemWritter"
		class="com.framework.aside.writer.CommonHeaderFooterFlileWriter"
		scope="step">
		
	</bean> 
	
	
	

</beans>