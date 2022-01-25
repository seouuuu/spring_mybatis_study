package com.example.demo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.db.DBManager;
import com.example.demo.vo.BoardVO;

@Repository
public class BoardDAO {
	public List<BoardVO> findAll(){
		return DBManager.findAll();
	}
	
	public int insert(BoardVO b) {
		return DBManager.insert(b);
	}

	public BoardVO findByNO(int no) {
		return DBManager.findByNO(no);
	}
	
	public int update(BoardVO b) {
		return DBManager.update(b);
	}
}
