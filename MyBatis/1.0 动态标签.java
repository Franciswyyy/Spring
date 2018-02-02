if

<mapper namespace="cn.popj">
	<select id="listProduct" resultType="Product">
        select * from product_         
    </select>
    <select id="listProductByName" resultType="Product">
        select * from product_  where name like concat('%',#{name},'%')        
    </select>
</mapper>

对Product进行两条sql语句，一是查询所有，一个是根据名称模糊查询

<mapper namespace="com.how2java.pojo">
    <select id="listProduct" resultType="Product">
        select * from product_
        <if test="name!=null">
            where name like concat('%',#{name},'%')
        </if>        
    </select>        
</mapper>

表示没有传参数name，就查询所有，如果有name参数，那么就进行模糊查询。


where
当要进行多条件判断
<select id="listProduct" resultType="Product">
	select * from product_
	<if test="name!=null">
		where name like concat('%',#{name},'%')
	</if>		 	
	<if test="price!=0">
		and price > #{price}
	</if>		 	
</select>
问题是：当没有name参数，却有price从参数，执行
select * from product_ and price > 10   这样会报错
这样可以通过wehere标签来解决

<select id="listProduct" resultType="Product">
	select * from product_
	<where>
		<if test="name!=null">
			and name like concat('%',#{name},'%')
		</if>		 	
		<if test="price!=null and price!=0">
			and price > #{price}
		</if>	
	</where>	 	
</select>

<where>标签会进行自动判断
如果任何条件都不成立，那么就在sql语句里就不会出现where关键字
如果有任何条件成立，会自动去掉多出来的 and 或者 or。

与where标签类似的，在update语句里也会碰到多个字段相关的问题。 在这种情况下，就可以使用set标签：
 
    <set>
     	<if test="name != null">name=#{name},</if>
     	<if test="price != null">price=#{price}</if>
    </set>



trim 用来定制想要的功能，比如where标签就可以用
 
<trim prefix="WHERE" prefixOverrides="AND |OR ">
  ... 
</trim>
 

来替换

set标签就可以用
 
<trim prefix="SET" suffixOverrides=",">
  ...
</trim>


Mybatis里面没有else标签，但是可以使用when otherwise标签来达到这样的效果。

<select id="listProduct" resultType="Product">
      SELECT * FROM product_
      <where>
        <choose>
          <when test="name != null">
            and name like concat('%',#{name},'%')
          </when>          
          <when test="price !=null and price != 0">
            and price > #{price}
          </when>                
          <otherwise>
            and id >1
          </otherwise>
        </choose>
      </where>
</select>

提供了任何条件，就进行条件查询，否则就用id>1这个条件。


bind标签就像是再做一次字符串拼接，方便后续使

 <!-- 本来的模糊查询方式 -->
        <select id="listProduct" resultType="Product"> 
             select * from   product_  where name like concat('%',#{0},'%')        </select> -->
        </select>    

        <select id="listProduct" resultType="Product">
            <bind name="likename" value="'%' + name + '%'" />
            select * from   product_  where name like #{likename}
        </select>
