package com.bjlemon.springMvc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.bjlemon.springMvc.bean.Book;

public interface BookMapper {

	@Select("select * from tb_book")
	List<Book> findAllBook();

}
