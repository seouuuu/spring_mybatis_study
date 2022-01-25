package com.example.demo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.db.DBManager;
import com.example.demo.vo.GoodsVO;

@Repository
public class GoodsDao {
	public List<GoodsVO> findAll(){
		return DBManager.findAll();
	}
	
	public int insert(GoodsVO g) {
		return DBManager.insert(g);
	}
	
	public GoodsVO findByNO(int no) {
		return DBManager.findByNo(no);
	}
	
	public int update(GoodsVO g) {
		return DBManager.update(g);
	}

	public int delete(int no) {
		// TODO Auto-generated method stub
		return DBManager.delete(no);
	}
}
