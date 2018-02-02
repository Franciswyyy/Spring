静态工厂：

public interface UserService ----  void addUser();
public class UserServiceImpl implements UserService 
public class MyBeanFactory {
	/创建实例
	public static UserService createService(){
		return new UserServiceImpl();}}
	<!-- 将静态工厂创建的实例交予spring 
		class 确定静态工厂全限定类名
		factory-method 确定静态方法名
	-->
<bean id="userServiceId" class="包名.MyBeanFactory" factory-method="createService"/>

1.自定义工厂
	UserService userService = MyBeanFactory.createService();
	userService.addUser();
2.spring工厂
	1.获的Spring容器
	2.UserService userService = applicationContext.getBean("userServiceId" ,UserService.class);
	  userService.addUser();

实例工厂：
三个文件都一样，只是MyBeanFactory的方法是非静态的。
特点:必须先创建工厂实例对象，再来创建对象

	<!-- 创建工厂实例 -->
<bean id="myBeanFactoryId" class="com.MyBeanFactory"></bean>
	<!-- 获得userservice 
		* factory-bean 确定工厂实例
		* factory-method 确定普通方法
	-->
<bean id="userServiceId" factory-bean="myBeanFactoryId" factory-method="createService"></bean>

1.自定义
	两步，一步创建工厂，工厂实例再创建对象
2.spring工厂--跟上述一样