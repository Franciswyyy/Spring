
构造方法方式
一个POPJ
User---id，username, age 以及构造方法 User(String username, Integer age)
	
	<!--
	<constructor-arg/> 用于配置构造方法一个参数argument
		name ：参数的名称
		value：设置普通数据
		ref：引用数据，一般是另一个bean id值
	-->
<constructor-arg name="username" value="jack"></constructor-arg>
<constructor-arg name="age" value="18"></constructor-arg>

<bean id="userId" class="cn.User" >
	<constructor-arg name="username" value="jack"></constructor-arg>
	<constructor-arg name="age" value="18"></constructor-arg>
</bean>


setter方式
两个POPJ
Person--属性Address,name,age
Address--属性add,tel
	<!-- setter方法注入 
		* 普通数据 
			<property name="" value="值">
			等效
			<property name="">
				<value>值
		* 引用数据
			<property name="" ref="另一个bean">
			等效
			<property name="">
				<ref bean="另一个bean"/>
	
	-->

<bean id="personId" class="cn.Person">
	<property name="name" value="阳志"></property>
	<property name="age">
		<value>22</value>
	</property>
		
	<property name="homeAddr" ref="homeAddrId"></property>
</bean>
	
<bean id="homeAddrId" class="cn.Address">
	<property name="addr" value="南京"></property>
	<property name="tel" value="911"></property>
</bean>

最后getbean(personId)就可以得到配置后的Person信息了。


集合注入
一个POPJ CollData
private String[] arrayData;
private List<String> listData;
private Set<String> setData;
private Map<String, String> mapData;
private Properties propsData;

传值的话也是一样的。
	<!-- 
		集合的注入都是给<property>添加子标签
			数组：<array>
			List：<list>
			Set：<set>
			Map：<map> ，map存放k/v 键值对，使用<entry>描述
			Properties：<props>  <prop key=""></prop>  【】
			
		普通数据：<value>
		引用数据：<ref>
	-->
<bean id="collDataId" class="cn.CollData" >
		<property name="arrayData">
			<array>
				<value>DS</value>
			</array>
		</property>
		
		<property name="listData">
			<list>
				<value>于嵩楠</value>
			</list>
		</property>
		
		<property name="setData">
			<set>
				<value>停封</value>
			</set>
		</property>
		
		<property name="mapData">
			<map>
				<entry key="jack" value="杰克"></entry>
				<entry>
					<key><value>rose</value></key>
					<value>肉丝</value>
				</entry>
			</map>
		</property>
		
		<property name="propsData">
			<props>
				<prop key="高富帅">嫐</prop>
				
			</props>
		</property>
	</bean>
</beans>