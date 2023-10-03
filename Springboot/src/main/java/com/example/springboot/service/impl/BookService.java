package com.example.springboot.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.Book;
import com.example.springboot.mapper.BookMapper;
import com.example.springboot.service.IBookService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


//实现类
@Slf4j
@Service
public class BookService implements IBookService {

    @Autowired
    BookMapper bookMapper;

    private static final String DEFAULT_PASS="123456";
    private static final String PASS_SALT="lkx";

    @Override
    public List<Book> list() {
        return bookMapper.list();
    }

    @Override
    public PageInfo<Book> page(BaseRequest baseRequest) {
        List<Book> objs = bookMapper.listByCondition(baseRequest);

        int page = baseRequest.getPageNum();
        int pageSize = baseRequest.getPageSize();

        int total = objs.size();
        List<Book> collect = objs
                .stream()
                .skip((page - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());

        //计算总页数

        int pageSum = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
        PageHelper.startPage(page, pageSize);

        PageInfo<Book> PageInfo = new PageInfo<Book>(collect);
        PageInfo.setTotal(total);
        PageInfo.setPages(pageSum);
        PageHelper.clearPage();
        return PageInfo;

    }

    @Override
    public void save(Book obj) {
        obj.setCategory(category(obj.getCategories()));
        bookMapper.save(obj);
    }

    @Override
    public Book getById(Integer id) {
        return bookMapper.getById(id);
    }

    @Override
    public void update(Book obj) {
        obj.setCategory(category(obj.getCategories()));
        obj.setUpdatetime(new Date());
        bookMapper.updateById(obj);
    }

    @Override
    public void deleteById(Integer id) {
        bookMapper.deleteById(id);
    }

    private String category(List<String> categories){
        StringBuilder sb=new StringBuilder();
        if(CollUtil.isNotEmpty(categories)){
            categories.forEach(v->sb.append(v).append(" > "));
            return sb.toString().substring(0,sb.lastIndexOf(" > "));
        }
        return sb.toString();
    }


}
