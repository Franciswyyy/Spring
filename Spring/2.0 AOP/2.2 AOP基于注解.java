


@Service("userServiceId")
public class UserServiceImpl implements UserService

切面类，含有多个通知
 
@Component
@Aspect
public class MyAspect {
	
	切入点当前有效
	@Before("execution(* cn.aspect.UserServiceImpl.*(..))")
	public void myBefore(JoinPoint joinPoint){
		System.out.println("前置通知 ： " + joinPoint.getSignature().getName());
	}
	
	声明公共切入点
	@Pointcut("execution(* cn.aspect.UserServiceImpl.*(..))")
	private void myPointCut(){
	}
	
	@AfterReturning(value="myPointCut()" ,returning="ret")
	public void myAfterReturning(JoinPoint joinPoint,Object ret){
		System.out.println("后置通知 ： " + joinPoint.getSignature().getName() + " , -->" + ret);
	}
	
	@Around(value = "myPointCut()")
	public Object myAround(ProceedingJoinPoint joinPoint) throws Throwable{
		System.out.println("前");
		//手动执行目标方法
		Object obj = joinPoint.proceed();
		
		System.out.println("后");
		return obj;
	}
	
	@AfterThrowing(value="execution(* cn.aspect.UserServiceImpl.*(..))" ,throwing="e")
	public void myAfterThrowing(JoinPoint joinPoint,Throwable e){
		System.out.println("抛出异常通知 ： " + e.getMessage());
	}
	
	@After("myPointCut()")
	public void myAfter(JoinPoint joinPoint){
		System.out.println("最终通知");
	}

}


	<!-- 1.扫描 注解类 -->
<context:component-scan base-package="cn.aspect"></context:component-scan>
	
	<!-- 2.确定 aop注解生效 -->
<aop:aspectj-autoproxy></aop:aspectj-autoproxy>


通过容器获得userServiceId的bean对象。