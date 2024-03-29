<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:jdbc="http://www.springframework.org/schema/jdbc" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:batch="http://www.springframework.org/schema/batch" xmlns:aop="http://www.springframework.org/schema/aop"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
		http://www.springframework.org/schema/batch http://www.springframework.org/schema/batch/spring-batch-2.1.xsd
		http://www.springframework.org/schema/jdbc http://www.springframework.org/schema/jdbc/spring-jdbc-3.0.xsd
		http://www.springframework.org/schema/aop 
		http://www.springframework.org/schema/aop/spring-aop-3.0.xsd ">

	<bean id="${message}"
		class="org.springframework.batch.support.transaction.ResourcelessTransactionManager" />

	<bean id="jobRepository"
		class="org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean">
		<property name="transactionManager" ref="transactionManager" />
	</bean>

	<bean id="jobLauncher"
		class="org.springframework.batch.core.launch.support.SimpleJobLauncher">
		<property name="jobRepository" ref="jobRepository" />
	</bean>

	<!-- <bean id="dataSource" class="org.springframework.jdbc.datasource.SingleConnectionDataSource">
		<property name="driverClassName" value="org.h2.Driver" />
		<property name="url" value="jdbc:h2:." />
		<property name="username" value="sa" />
		<property name="password" value="" />
		<property name="suppressClose" value="true" />
	</bean> -->
	<bean id="dataSource" class="org.springframework.jdbc.datasource.SingleConnectionDataSource">
		<property name="driverClassName" value="com.mysql.jdbc.ReplicationDriver" />
		<property name="url" value="jdbc:mysql://10.8.7.212:3306/automatedtransformation" />
		<property name="username" value="test1" />
		<property name="password" value="test1" />
		<property name="suppressClose" value="true" />
	</bean> 
	
	<bean id="interfaceMasterBLO" class="org.abc.framework.blo.InterfaceMasterBLO">
		<property name="interfaceMasterDAO" ref="interfaceMasterDAO" />
	</bean>

	<bean id="interfaceMasterDAO" class="org.abc.framework.dao.InterfaceMasterDAO">
		<property name="dataSource" ref="dataSource" />
	</bean>
	
	<bean id="interfaceMasterReaderTasklet"
		class="org.abc.framework.tasklet.InterfaceMasterReaderTasklet"
		scope="step">
		<property name="interfaceMasterBLO" ref="interfaceMasterBLO" />
	</bean>


	<!-- <jdbc:initialize-database data-source="dataSource">
		<jdbc:script location="classpath:/org/abc/framework/common/create_base_tables.sql" />
		<jdbc:script location="classpath:/org/abc/framework/blo/insert_base_tables.sql" />
	</jdbc:initialize-database> -->


</beans>