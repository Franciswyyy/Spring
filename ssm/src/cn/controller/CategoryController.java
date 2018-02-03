package cn.controller;

/*
1.首先浏览器上访问路径 /listCategory
2. tomcat根据web.xml上的配置信息，拦截到了/listCategory，并将其交由DispatcherServlet处理。
3. DispatcherServlet 根据springMVC的配置，将这次请求交由CategoryController类进行处理，所以需要进行这个类的实例化
4. 在实例化CategoryController的时候，注入CategoryServiceImpl。 (自动装配实现了CategoryService接口的的实例，只有CategoryServiceImpl实现了CategoryService接口，所以就会注入CategoryServiceImpl)
5. 在实例化CategoryServiceImpl的时候，又注入CategoryMapper
6. 根据ApplicationContext.xml中的配置信息，将CategoryMapper和Category.xml关联起来了。
7. 这样拿到了实例化好了的CategoryController,并调用listCategory方法
8. 在listCategory方法中，访问CategoryService,并获取数据，并把数据放在"cs"上，接着服务端跳转到listCategory.jsp去
9. 最后在listCategory.jsp 中显示数据
*/


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.pojo.Category;
import cn.service.CategoryService;
import cn.util.Page;

// 告诉spring mvc这是一个控制器类
@Controller
@RequestMapping("")
public class CategoryController {
	@Autowired
	CategoryService categoryService;

	@RequestMapping("listCategory")
	public ModelAndView listCategory(Page page){
		ModelAndView mav = new ModelAndView();
		List<Category> cs= categoryService.list(page);
		int total = categoryService.total();
		
		page.caculateLast(total);
		
		// 放入转发参数
		mav.addObject("cs", cs);
		// 放入jsp路径
		mav.setViewName("listCategory");
		return mav;
	}

}
