package com.example.springboot.mapper;

import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.controller.request.PasswordRequest;
import com.example.springboot.entity.Admin;
import com.example.springboot.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CategoryMapper {

    List<Category> list();
    List<Category> listByCondition(BaseRequest userPageRequest);

    void save(Category obj);

    Category getById(Integer id);

    void updateById(Category obj);

    void deleteById(Integer id);


}


