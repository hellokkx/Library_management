package com.example.springboot.controller;


import cn.hutool.core.collection.CollUtil;
import com.example.springboot.common.Result;
import com.example.springboot.controller.request.AdminPageRequest;
import com.example.springboot.controller.request.CategoryPageRequest;
import com.example.springboot.entity.Book;
import com.example.springboot.entity.Category;
import com.example.springboot.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//对外--前端 传输数据
@CrossOrigin  //SpringBoot跨域问题解决
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    ICategoryService categoryService;


    @PostMapping("/save")
    public Result save(@RequestBody Category obj){
        categoryService.save(obj);
        return Result.success();
    }

    @PutMapping("/update")
    public Result update(@RequestBody Category obj){
        categoryService.update(obj);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        categoryService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        Category obj=categoryService.getById(id);
        return Result.success(obj);
    }

    @GetMapping("/list")
    public Result list(){
        List<Category>objs=categoryService.list();
        return Result.success(objs);
    }

    @GetMapping("/tree")
    public Result tree(){
        List<Category>list=categoryService.list();

        //对list操作
//        List<Category>treeList=list.stream()
//                .filter(v ->v.getPid()==null)
//                .collect(Collectors.toList());//第一层
        //递归实现多层children

        return Result.success(createTree(null,list));
    }
    private List<Category>createTree(Integer pid, List<Category> categories){
        List<Category>treeList=new ArrayList<>();
        for(Category category:categories){
            if(pid==null){
                if(category.getPid()==null){
                    treeList.add(category);
                    category.setChildren(createTree(category.getId(),categories));
                }
            }
            else if(pid.equals(category.getPid())){
                treeList.add(category);
                category.setChildren(createTree(category.getId(),categories));
            }
            if(CollUtil.isEmpty(category.getChildren())){
                category.setChildren(null);
            }
        }
        return treeList;
    }

    @GetMapping("/page")
    public Result page(CategoryPageRequest categoryPageRequest){
        return Result.success(categoryService.page(categoryPageRequest));
    }

}
