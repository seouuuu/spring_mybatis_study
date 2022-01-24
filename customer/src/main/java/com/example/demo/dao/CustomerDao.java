package com.example.demo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.db.DBManager;
import com.example.demo.vo.CustomerVO;

@Repository
public class CustomerDao {
	public List<CustomerVO> findAll(){
		return DBManager.findAll();
	}
}
