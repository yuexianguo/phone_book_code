package com.phone.book.bean;

import java.util.ArrayList;

/**
 * description :
 * author : Andy.Guo
 * email : Andy.Guo@waclightiong.com.cn
 * data : 2021/12/31
 */
public class DeptTree {
    public String name;
    public ArrayList<DeptTree> child= new ArrayList<>();
    public DeptTree parent;
    public Boolean tag= false;
    public DeptTree(String name, DeptTree parent) {
        this.name = name;
        this.parent = parent;
    }
}

