package com.example.demo.db;

import java.io.Reader;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.example.demo.vo.GoodsVO;
import com.example.demo.vo.SistLog;

public class DBManager {
	private static SqlSessionFactory factory;
	static {
		try {
			Reader reader = Resources.getResourceAsReader("com/example/demo/db/sqlMapConfig.xml");
			factory = new SqlSessionFactoryBuilder().build(reader);
			reader.close();
		}catch (Exception e) {
			System.out.println("예외발생:"+e.getMessage());
		}
	}
	
	public static int getTotalRecord() {
		SqlSession session = factory.openSession();
		int count = session.selectOne("goods.totalRecord");
		System.out.println("매니저에서 전체레코드수:"+count);
		session.close();
		return count;
	}
	
	public static List<GoodsVO> findAll(HashMap map){
		SqlSession session = factory.openSession();
		List<GoodsVO> list = session.selectList("goods.findAll",map);
		session.close();
		return list;
	}
	
	public static int insert(GoodsVO g) {
		SqlSession session = factory.openSession();
		int re = session.insert("goods.insert",g);
		session.commit();
		session.close();
		return re;
	}
	
	public static GoodsVO findByNo(int no) {
		SqlSession session = factory.openSession();
		GoodsVO g = session.selectOne("goods.findByNO",no);
		session.close();
		return g;
	}
	
	public static int update(GoodsVO g) {
		SqlSession session = factory.openSession();
		int re = session.update("goods.update",g);
		session.commit();
		session.close();
		return re;
	}

	public static int delete(int no) {
		SqlSession session = factory.openSession();
		int re = session.update("goods.delete",no);
		session.commit();
		session.close();
		return re;
	}
	
	public static int insertLog(SistLog log) {
		SqlSession session = factory.openSession();
		int re = session.insert("sistLog.insert",log);
		session.commit();
		session.close();
		return re;
	}
}
