package cn.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mapper.CategoryMapper;
import cn.pojo.Category;
import cn.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	CategoryMapper categoryMapper;
	
	@Override
	public List<Category> list() {
		return categoryMapper.list();
	}

	
}
