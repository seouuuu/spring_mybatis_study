package com.example.demo.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.db.DBManager;
import com.example.demo.vo.BookVO;

@Repository
public class BookDAO {

	public List<BookVO> findAll(HashMap map){
		return DBManager.findAll(map);
	}
	
	public int insertBook(BookVO b) {
		return DBManager.insert(b);
	}
	
	public int updateBook(BookVO b) {
		return DBManager.update(b);
	}
	
	public int deleteBook(int bookid) {
		return DBManager.delete(bookid);
	}
	
	public int getTotalCount() {
		return DBManager.getTotalCount();
	}
}
