package cn.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mapper.CategoryMapper;
import cn.pojo.Category;
import cn.service.CategoryService;
import cn.util.Page;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	CategoryMapper categoryMapper;
	
	@Override
	public int total() {
		return categoryMapper.total();
	}
	
	@Override
	public List<Category> list() {
		return categoryMapper.list();
	}

	@Override
	public List<Category> list(Page page) {
		return categoryMapper.list(page);
	}

	
}
