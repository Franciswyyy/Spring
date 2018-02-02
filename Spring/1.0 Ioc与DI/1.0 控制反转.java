
最基本的Ioc

例子一：
一个接口，一个对应的实现类,一个配置文件applicationContext.xml

public interface UserService {}
public class UserServiceImpl implements UserService { 
	public void addUser() {}
}


<!-- 配置service 
		<bean> 配置需要创建的对象
			id ：用于之后从spring容器获得实例时使用的
			class ：需要创建实例的全限定类名
-->
 <bean id="userServiceId" class="包名.UserServiceImpl"></bean>


Test
以前：
        UserService userService = new UserServiceImpl();
		userService.addUser();
现在
        /从spring容器获得
		/1 获得容器
		String xmlPath = "com/itheima/a_ioc/beans.xml";
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
		/2获得内容 --不需要自己new，都是从spring容器获得
		UserService userService = (UserService) applicationContext.getBean("userServiceId");
		userService.addUser();

================
例子二：
那么假如是构造方法的话，就可以设置值什么的
一个popj属性是id, name
	<bean name="c" class="com.pojo.Category">
        <property name="name" value="category 1" />
    </bean>
1.获得容器
2.Category c = (Category) applicationContext.getBean("c");   
  c.getName()  ----- category1