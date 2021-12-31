package com.phone.book.bean;

import java.util.ArrayList;

/**
 * description :
 * author : Andy.Guo
 * email : Andy.Guo@waclightiong.com.cn
 * data : 2021/12/31
 */
public class Tree {
    public String node;
    public ArrayList<Tree> child= new ArrayList<>();
    public Tree parent;
    public Boolean tag= false;
    public Tree(String node, Tree parent) {
        this.node = node;
        this.parent = parent;
    }
}

