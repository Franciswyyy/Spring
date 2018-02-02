加载顺序

1.了解Tomcat启动加载配置文件---servlet, filter, listener, spring
2.确定配置文件位置，通过系统初始化参数
3.从servletContext作用域获得Spring容器-(了解)

 <!-- 确定配置文件位置 -->
  <context-param>
  	<param-name>contextConfigLocation</param-name>
  	<param-value>classpath:applicationContext.xml</param-value>
  </context-param>
  
  <!-- 配置spring 监听器，加载xml配置文件 -->
  <listener>
  	<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
  </listener>


  ApplicationContext apppApplicationContext = 
				WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
