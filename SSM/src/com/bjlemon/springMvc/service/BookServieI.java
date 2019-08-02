package com.bjlemon.springMvc.service;

import java.util.List;

import com.bjlemon.springMvc.bean.Book;

/**
 * 
 * @author Administrator
 *
 */
public interface BookServieI {
	List<Book> findAllBook();
}
