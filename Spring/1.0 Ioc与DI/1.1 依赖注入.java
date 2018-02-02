例子一：
BookDao----BookDaoImpl
BookService---BookServiceImpl  其中要注入BookDaoImpl对象

public class BookServiceImpl implements BookService {
	/ 方式1：之前，接口=实现类
	/private BookDao bookDao = new BookDaoImpl();
	/ 方式2：接口 + setter
	private BookDao bookDao;
	public void setBookDao(BookDao bookDao) {
		this.bookDao = bookDao;
	}
	@Override
	重写方法
}

	<bean id="bookServiceId" class="com.itheima.b_di.BookServiceImpl" >
		<property name="bookDao" ref="bookDaoId"></property>
	</bean>
	
	<!-- 创建dao实例 -->
	<bean id="bookDaoId" class="com.itheima.b_di.BookDaoImpl"></bean>




例子二：

两个POPJ
一个Product，其中有Category对象的setter，getter
一个Category

应该知道注入的方法有三种。

applicationContext.xml

<bean name="c" class="com.how2java.pojo.Category">
        <property name="name" value="category 1" />
    </bean>
    <bean name="p" class="com.how2java.pojo.Product">
        <property name="name" value="product1" />
        <property name="category" ref="c" />
    </bean>

Test  
1.得到容器
2.Product p = (Product) context.getBean("p");
  p.getName();   
  p.getCategory().getName();