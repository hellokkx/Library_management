package com.example.springboot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

//@Data
@Data
public class Book {
    private Integer id;
    private String name;
    private String description;
    private String publishdate;
    private String author;
    private String publisher;
    private String category;
    private String bookno;
    private String cover;
    private Integer nums;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date createtime;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date updatetime;
    private List<String>categories;
}


