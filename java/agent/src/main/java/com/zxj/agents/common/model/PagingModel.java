package com.zxj.agents.common.model;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * 分页查找结果类
 * @author:zhongxiaojian
 * @date:2018-04-10 16:04
 */
public class PagingModel<T> implements Serializable {
    private long totalCount;
    private int pageCount;
    private int pageNum;
    private int pageSize;
    private List<T> list = new LinkedList<>();

    public PagingModel(List<T> list){
        if (list instanceof Page){
            Page<T> page = (Page<T>) list;
            this.totalCount = page.getTotal();
            this.pageCount = page.getPages();
            this.pageNum = page.getPageNum();
            this.pageSize = page.getPageSize();
            this.list = page;
        }
    }

    public PagingModel() {
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

}
