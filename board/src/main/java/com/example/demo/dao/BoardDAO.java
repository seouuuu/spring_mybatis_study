package com.example.demo.dao;

import java.util.HashMap;
import java.util.List;

import org.eclipse.jdt.internal.compiler.lookup.TypeConstants.DangerousMethod;
import org.springframework.stereotype.Repository;

import com.example.demo.db.DBManager;
import com.example.demo.vo.BoardVO;

@Repository
public class BoardDAO {
	public static int pageSize = 10;	//한 화면에 보여 줄 레코드 수
	public static int totalRecord;		//전체 레코드 수
	public static int totalPage;		//전체 페이지 수
	
	public int getNextNo() {
		return DBManager.getNextNO();
	}
	
	public List<BoardVO> findAll(HashMap map){
		totalRecord = DBManager.getTotalRecord(map);
		totalPage = (int)Math.ceil(totalRecord/(double)pageSize);
		return DBManager.findAll(map);
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

	public int delete(HashMap map) {
		return DBManager.delete(map);
	}
	
	public void updateHit(int no) {
		DBManager.updateHit(no);
	}

}
