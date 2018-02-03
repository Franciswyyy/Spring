package cn.service;

import java.util.List;

import cn.pojo.Category;
import cn.util.Page;

public interface CategoryService {

	List<Category> list();
	int total();
	List<Category> list(Page page);
}
