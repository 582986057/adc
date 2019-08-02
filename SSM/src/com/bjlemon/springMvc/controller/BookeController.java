package com.bjlemon.springMvc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bjlemon.springMvc.bean.Book;
import com.bjlemon.springMvc.service.BookServieI;

@Controller
@RequestMapping("/book")
public class BookeController {

	@Autowired
	private BookServieI bookService;

	// 获取所有的图书信息
	@ResponseBody
	@RequestMapping("/getAllBookInfos.action")
	public String getAllBookInfos() {

		List<Book> books = bookService.findAllBook();
		System.out.println("books:" + books.size());
		return null;
	}
}
