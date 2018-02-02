1.JDK动态代理实现AOP

public interface UserService   ---三个方法 add update delete
public class UserServiceImpl implements UserService  
public class Aspect   --两个方法 before after
public class MyBeanFactory{
	public static UserService createService(){
		final UserService userService = new UserServiceImpl();    //目标类
		final Aspect aspect = new Aspect();	  //切面类
		UserService proxyService = (UserService)proxy.newProxyInstance(
			MyBeanFactory.class.getClassLoader(),
			userService.getClass().getInterfaces(),
			new InvocationHandler(){
				@Override
				public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				aspect.before();
				Object obj = method.invoke(userService, args);
				aspect.after();						
				return obj;
			})}
		return proxyService;
}}
Test
UserService userService = MyBeanFactory.createService();
userService.add();


2.cglib字节码增强---没有接口

public class MyBeanFactory {
	public static UserServiceImpl createService(){
		final UserServiceImpl userService = new UserServiceImpl();
		final Aspect aspect = new Aspect();
		// 3.代理类 ，采用cglib，底层创建目标类的子类
		//3.1 核心类
		Enhancer enhancer = new Enhancer();
		//3.2 确定父类
		enhancer.setSuperclass(userService.getClass());
		/* 3.3 设置回调函数 , MethodInterceptor接口 等效 jdk InvocationHandler接口
		 * 	intercept() 等效 jdk  invoke()
		 * 		参数1、参数2、参数3：以invoke一样                   ---常用前三个参数
		 * 		参数4：methodProxy 方法的代理
		 */
		enhancer.setCallback(new MethodInterceptor(){

			@Override
			public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
				aspect.before();
				//执行目标类的方法
				Object obj = method.invoke(userService, args);
				// * 执行代理类的父类 ，执行目标类 （目标类和代理类 父子关系）  ..这两者是等价的。   后者是代理的(就是目标类的子类)
				methodProxy.invokeSuper(proxy, args);
				aspect.after();
				return obj;
			}});
		//3.4 创建代理
		UserServiceImpl proxService = (UserServiceImpl) enhancer.create();	
		return proxService;
	}}


3.半自动AOP(不要求掌握)
切面类采用环绕接口，要手动执行目标方法。
有没有接口都行
public class MyAspect implements MethodInterceptor{
	@Override
	public Object invoke(MethodInvocation mi) throws Throwable {
		System.out.println("前");
		Object obj = mi.proceed();    //手动执行目标方法
		System.out.println("后");
		return obj; }}

创建目标类，切面类（通知）
<bean id="userServiceId" class="cn.UserServiceImpl"></bean>
<bean id="aspectId" class="cn.aspect"></bean>
<!-- 3 创建代理类 
		* ProxyFactoryBean 用于创建代理工厂bean，生成特殊代理对象
			interfaces : 确定接口们
				通过<array>可以设置多个值
				只有一个值时，value=""
			target : 确定目标类
			interceptorNames : 通知 切面类的名称，类型String[]，如果设置一个值 value=""
			optimize :强制使用cglib,如果不设置的话，有接口就是JDK动态，没有接口采用cglib字节码增强
				<property name="optimize" value="true"></property>
	-->
<bean id="proxyServiceId" class="org.springframework.aop.framework.ProxyFactoryBean">
	<property name="interfaces" value="cn.UserService"></property>
	<property name="target" ref="userServiceId"></property>
	<property name="interceptorNames" value="aspectId"></property>
</bean>
最后通过从容器调用代理bean。


4.全自动AOP
aspect既然也是实现环绕通知的话，和半自动的一样，实现MethodInterceptor接口
<bean id="userServiceId" class="cn.UserServiceImpl"></bean>
<bean id="aspectId" class="cn.aspect"></bean>
<aop:config proxy-target-class="true">
		<aop:pointcut expression="execution(* cn.*.*(..))" id="myPointCut"/>
		<aop:advisor advice-ref="aspectId" pointcut-ref="myPointCut"/>
</aop:config>

<!--  aop编程 
		3.1 导入命名空间
		3.2 使用 <aop:config>进行配置
			proxy-target-class="true" 声明时使用cglib代理
			<aop:pointcut> 切入点 ，从目标对象获得具体方法
			<aop:advisor> 特殊的切面，只有一个通知 和 一个切入点
				advice-ref 通知引用
				pointcut-ref 切入点引用
		3.3 切入点表达式
			execution(* cn.spring_aop.*.*(..))
			选择方法         返回值任意   包                                             类名任意   方法名任意   参数任意
		
-->

从容器中取的是userServiceId

