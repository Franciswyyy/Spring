一对多的注解：

public interface CategoryMapper {
    @Select(" select * from category_ ")
    @Results({ 
                @Result(property = "id", column = "id"),
                @Result(property = "products", javaType = List.class, column = "id", many = @Many(select = "cn.mapper.ProductMapper.listByCategory") )
            })
    public List<Category> list();
 
}

public interface ProductMapper {
  
    @Select(" select * from product_ where cid = #{cid}")
    public List<Product> listByCategory(int cid);
     
}

mybatis-config.xml
<mappers>
    <mapper resource="cn/pojo/Category.xml"/>
    <mapper class="cn.mapper.CategoryMapper"/> 
    <mapper class="cn.mapper.ProductMapper"/> 
</mappers>

Test

CategoryMapper mapper = session.getMapper(CategoryMapper.class);
 
    listAll(mapper);     // 一次读取，而不是用默认的输出
              
    session.commit();
    session.close();


private static void listAll(CategoryMapper mapper) {
        List<Category> cs = mapper.list();
        for (Category c : cs) {
            System.out.println(c.getName());
            List<Product> ps = c.getProducts();
            for (Product p : ps) {
                System.out.println("\t"+p.getName());
            }
        }
    }


多对一的注解：

public interface CategoryMapper {
    @Select(" select * from category_ where id = #{id}")
    public Category get(int id);
     
}

public interface ProductMapper {
    @Select(" select * from product_ ")
    @Results({ 
        @Result(property="category",column="cid",one=@One(select="cn.mapper.CategoryMapper.get")) 
    })
    public List<Product> list();
}

 ProductMapper mapper = session.getMapper(ProductMapper.class);
 
        List<Product> ps= mapper.list();
        for (Product p : ps) {
            System.out.println(p + "\t对应的分类是:\t" + p.getCategory().getName());
        }
 
        session.commit();
        session.close();