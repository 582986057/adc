package com.bjlemon.springMvc.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bjlemon.springMvc.bean.Book;
import com.bjlemon.springMvc.mapper.BookMapper;
import com.bjlemon.springMvc.service.BookServieI;

@Service("bookService")
@Transactional
public class BookService implements BookServieI{ 
	
	@Autowired
	private BookMapper bookMapper;
	
	//获取所有的图书信息
	public List<Book> findAllBook() {
		List<Book> books = bookMapper.findAllBook();
		
		return books;
	}

}
