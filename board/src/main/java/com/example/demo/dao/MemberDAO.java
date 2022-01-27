package com.example.demo.dao;

import org.springframework.stereotype.Repository;

import com.example.demo.db.DBManager;
import com.example.demo.vo.MemberVO;

@Repository
public class MemberDAO {

	public int insert(MemberVO m) {
		return DBManager.insertMember(m);
	}
	
	public MemberVO isMember(String id,String pwd) {
		return DBManager.isMember(id, pwd);
	}
}
