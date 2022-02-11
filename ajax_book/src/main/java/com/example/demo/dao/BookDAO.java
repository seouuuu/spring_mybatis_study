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
	
	public List<BookVO> listPublisher(){
		return DBManager.listPublisher();
	}
	
	public List<BookVO> findByPublisher(String publihser){
		return DBManager.findByPublisher(publihser);
	}
}