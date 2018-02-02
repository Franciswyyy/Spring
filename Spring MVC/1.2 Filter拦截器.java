
Servlet获取中文参数，要先设置UTF-8编码
request.setCharacterEncoding("UTF-8");


doFilter方法的第一个参数req,是ServletRequest 类型的，不支持setCharacterEncoding，所以要先强制转换为HttpServletRequest

public class EncodingFilter implements Filter {
 
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
 
        request.setCharacterEncoding("UTF-8");
 
        chain.doFilter(request, response);}
 
    @Override
    public void init(FilterConfig arg0) throws ServletException {}
 	
 	@Override
    public void destroy() {}
}


<filter>
    <filter-name>EncodingFilter</filter-name>
    <filter-class>filter.EncodingFilter</filter-class>
</filter>
 
<filter-mapping>
    <filter-name>EncodingFilter</filter-name>
    <url-pattern>/*</url-pattern>    (*/)  //这就表示拦截所有请求
</filter-mapping>

*/
或者   用Filter的FilterConfig

<filter>
	<filter-name>EncodingFilter</filter-name>
	<filter-class>filter.EncodingFilter</filter-class>
	<init-param>
		<param-name>encoding</param-name>
		<param-value>UTF-8</param-value>
	</init-param>	
</filter>

<filter-mapping>
	<filter-name>EncodingFilter</filter-name>
	<servlet-name>/*</servlet-name>     (*/)
</filter-mapping> 

public class EncodingFilter implements Filter {
 
 	private FilterConfig fileterconfig;

 	@Override
    public void init(FilterConfig arg0) throws ServletException {
    	this.fileterConfig = fileterConfig;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
 		
 		String encoding = fileterconfig.getInitParameter("encoding");
        request.setCharacterEncoding(encoding);
 
        chain.doFilter(request, response);}
 
 	@Override
    public void destroy() {}
}