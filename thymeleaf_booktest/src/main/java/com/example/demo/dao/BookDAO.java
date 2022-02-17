package com.example.demo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.db.DBManager;
import com.example.demo.vo.BookVO;

@Repository
public class BookDAO {
	public List<BookVO> findAll(){
		return DBManager.findAll();
	}
	
	public BookVO findByBookid(int bookid) {
		return DBManager.findByBookid(bookid);
	}
	
	public int update(BookVO b) {
		return DBManager.update(b);
	}
	
	public int delete(int bookid) {
		return DBManager.delete(bookid);
	}
}
