
利用事务来实现转账功能

create database Test;
use Test;
create table account(
  id int primary key auto_increment,
  username varchar(50),
  money int
);
insert into account(username,money) values('jack','10000');
insert into account(username,money) values('rose','10000');

一、最基础的Java实现
	2个接口以及对应的实现类,一个配置文件。

public interface AccountDao  ----out，in付款和收款功能
public class AccountDaoImpl extends JdbcDaoSupport implements AccountDao{
	public void out(String outer, Integer money) {
		this.getJdbcTemplate().update("update account set money = money - ? where username = ?", money,outer);}
	public void in(String inner, Integer money) {
		this.getJdbcTemplate().update("update account set money = money + ? where username = ?", money,inner);}}
public interface AccountService  ---- transfer 转账功能
public class AccountServiceImpl implements AccountService{
	private AccountDao accountDao;
	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;}
	@Override
	public void transfer(String outer, String inner, Integer money) {
		accountDao.out(outer, money);
		//断电
//		int i = 1/0;
		accountDao.in(inner, money);}}
applicationContext.xml中进行配置
1.DataSource 2.dao 3.service

	<bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
		<property name="driverClass" value="com.mysql.jdbc.Driver"></property>
		<property name="jdbcUrl" value="jdbc:mysql://localhost:3306/Test"></property>
		<property name="user" value="root"></property>
		<property name="password" value="root"></property>
	</bean>
		
	<bean id="accountDao" class="cn.dao.impl.AccountDaoImpl">
		<property name="dataSource" ref="dataSource"></property>
	</bean>

	<bean id="accountService" class="cn.service.impl.AccountServiceImpl">
		<property name="accountDao" ref="accountDao"></property>
	</bean>>

  return get.Bean(accountService, AccountService.class).transfer("jack", "rose", 1000);


二、手动管理事务
Spring底层采用的是TransactionTemplate
	1.service需要获得TransactionTemplate
	2.spring配置模板并注入service
	3.模板需要注入事务管理器
	4.配置事务管理器， 需要注入datasource
只需修改AccountServiceImpl 和 applicationContext.xml文件


public class AccountServiceImpl implements AccountService {

	private AccountDao accountDao;
	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}
	
	//需要spring注入模板
	private TransactionTemplate transactionTemplate;
	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}
	@Override
	public void transfer(final String outer,final String inner,final Integer money) {
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				accountDao.out(outer, money);
				//断电
//				int i = 1/0;
				accountDao.in(inner, money);
			}
		});

	}

}
	<!-- 1 dataSource  -->
	<!-- 2 dao  -->
	<bean id="accountDao" class="com.itheima.dao.impl.AccountDaoImpl">
		<property name="dataSource" ref="dataSource"></property>
	</bean>
	<!-- 3 service -->
	<bean id="accountService" class="com.itheima.service.impl.AccountServiceImpl">
		<property name="accountDao" ref="accountDao"></property>
		<property name="transactionTemplate" ref="transactionTemplate"></property>
	</bean>
	
	<!-- 4 创建模板 -->
	<bean id="transactionTemplate" class="org.springframework.transaction.support.TransactionTemplate">
		<property name="transactionManager" ref="txManager"></property>
	</bean>
	
	<!-- 5 配置事务管理器 ,管理器需要事务，事务从Connection获得，连接从连接池DataSource获得 -->
	<bean id="txManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
		<property name="dataSource" ref="dataSource"></property>
	</bean>

三.工厂bean生成代理: 半自动
	Spring提供了事务的代理工厂 beanTransactionProxyFactoryBean
	2个接口以及对应的实现类和一相同
在applicationContext.xml中添加
	<!-- 1 datasource -->
	<!-- 2 dao  -->
	<!-- 3 service -->
	<!-- 4 service 代理对象 
		4.1 proxyInterfaces 接口 
		4.2 target 目标类
		4.3 transactionManager 事务管理器
		4.4 transactionAttributes 事务属性（事务详情）
			prop.key ：确定哪些方法使用当前事务配置
			prop.text:用于配置事务详情
				格式：PROPAGATION,ISOLATION,readOnly,-Exception,+Exception
					传播行为		隔离级别	是否只读		异常回滚		异常提交
				例如：
					<prop key="transfer">PROPAGATION_REQUIRED,ISOLATION_DEFAULT</prop> 默认传播行为，和隔离级别
					<prop key="transfer">PROPAGATION_REQUIRED,ISOLATION_DEFAULT,readOnly</prop> 只读
					<prop key="transfer">PROPAGATION_REQUIRED,ISOLATION_DEFAULT,+java.lang.ArithmeticException</prop>  有异常扔提交
	-->
	<bean id="proxyAccountService" class="org.springframework.transaction.interceptor.TransactionProxyFactoryBean">
		<property name="proxyInterfaces" value="com.itheima.service.AccountService"></property>
		<property name="target" ref="accountService"></property>
		<property name="transactionManager" ref="txManager"></property>
		<property name="transactionAttributes">
			<props>
				<prop key="transfer">PROPAGATION_REQUIRED,ISOLATION_DEFAULT</prop>
			</props>
		</property>
	</bean>

	<!-- 5 事务管理器 -->
	<bean id="txManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
		<property name="dataSource" ref="dataSource"></property>
	</bean>