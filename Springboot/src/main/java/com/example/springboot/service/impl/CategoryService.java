package com.example.springboot.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.example.springboot.controller.dto.LoginDTO;
import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.controller.request.LoginRequest;
import com.example.springboot.controller.request.PasswordRequest;
import com.example.springboot.entity.Admin;
import com.example.springboot.entity.Category;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.AdminMapper;
import com.example.springboot.mapper.CategoryMapper;
import com.example.springboot.service.IAdminService;
import com.example.springboot.service.ICategoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

//实现类
@Slf4j
@Service
public class CategoryService implements ICategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    private static final String DEFAULT_PASS="123456";
    private static final String PASS_SALT="lkx";

    @Override
    public List<Category> list() {
        return categoryMapper.list();
    }

    @Override
    public PageInfo<Category> page(BaseRequest baseRequest) {
        List<Category> objs = categoryMapper.listByCondition(baseRequest);

        int page = baseRequest.getPageNum();
        int pageSize = baseRequest.getPageSize();

        int total = objs.size();
        List<Category> collect = objs
                .stream()
                .skip((page - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());

        //计算总页数

        int pageSum = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
        PageHelper.startPage(page, pageSize);

        PageInfo<Category> PageInfo = new PageInfo<Category>(collect);
        PageInfo.setTotal(total);
        PageInfo.setPages(pageSum);
        PageHelper.clearPage();
        return PageInfo;

    }

    @Override
    public void save(Category obj) {
        categoryMapper.save(obj);
    }

    @Override
    public Category getById(Integer id) {
        return categoryMapper.getById(id);
    }

    @Override
    public void update(Category obj) {
        obj.setUpdatetime(LocalDate.now());
        categoryMapper.updateById(obj);
    }

    @Override
    public void deleteById(Integer id) {
        categoryMapper.deleteById(id);
    }


}
