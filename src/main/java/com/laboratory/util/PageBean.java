package com.laboratory.util;

import java.io.Serializable;
import java.util.List;

import com.laboratory.util.PageInfo;

import com.github.pagehelper.Page;

public class PageBean<T> implements Serializable {
    private static final long serialVersionUID = 8656597559014685635L;
    private List<T> list;    //结果集
    private PageInfo pageInfo = new PageInfo();
    
    /**
     * 包装Page对象，因为直接返回Page对象，在JSON处理以及其他情况下会被当成List来处理，
     * 而出现一些问题。
     * @param list          page结果
     * @param navigatePages 页码数量
     */
    public PageBean(List<T> list) {
        if (list instanceof Page) {
            Page<T> page = (Page<T>) list;
            this.pageInfo.setPageNum(page.getPageNum());
            this.pageInfo.setPageSize(page.getPageSize());
            this.pageInfo.setTotal(page.getTotal());
            this.pageInfo.setPages(page.getPages());
            this.pageInfo.setSize(page.size());
            this.list = page;
        }
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

	public PageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}
    
}

