package com.phone.book.bean;

import java.util.ArrayList;

/**
 * description :
 * author : Andy.Guo
 * email : 495311081@qq.com
 * data : 2021/12/31
 */
public class DeptTree {
    public Long id;
    public Long pid;
    public String name;
    public ArrayList<DeptTree> child= new ArrayList<>();
    public DeptTree parent;
    public Boolean tag= false;
    public Integer level;
    public DeptTree(Long id, Long pid, String name, DeptTree parent,Integer level,Boolean tag) {
        this.id = id;
        this.pid = pid;
        this.name = name;
        this.parent = parent;
        this.level = level;
        this.tag = tag;
    }
}

