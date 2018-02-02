Bean声明周期

public interface UserService---void addUser()
public class UserServiceImpl implements UserService{
	addUser();  init();  destory(); 
}

public class MyBeanPostProcessor implements BeanPostProcessor{
	before();
	after(){------可以返回个动态代理类  
		return Proxy.newProxyInstance(...)
	} 
}

	<!--  
		init-method 用于配置初始化方法,准备数据等
		destroy-method 用于配置销毁方法,清理资源等
	-->
<bean id="userServiceId" class="cn.UserServiceImpl" 
		init-method="Init" destroy-method="Destroy" ></bean>
	
	<!-- 将后处理的实现类注册给spring -->
<bean class="cn.MyBeanPostProcessor"></bean>

1.获得容器
2.geiBean()---实现方法
3.关闭容器