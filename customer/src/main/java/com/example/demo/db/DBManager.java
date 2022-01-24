package com.example.demo.db;

import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.example.demo.vo.CustomerVO;

public class DBManager {
	
	//mybatis 설정파일에 있는 sql을 요청하려면 SqlSession이 필요
	//SqlSession을 만들어주는 SqlSessionFactory 변수를 멤버로 선언
	//DBManager 객체 없이 사용할 수 있도록 static
	private static SqlSessionFactory factory;
	
	//mabatis 설정파일인 sqlMapConfig파일을 읽어들여 SqlSessionFactory 객체 생성
	//사용자의 메소드 요청없이도 서버가 가동되면서 자동으로 동작하기 위해 static
	//파일의 내용을 읽어들이기 위해 예외처리
	static {
		try {
			//mybatis 설정파일인 sqlMapConfig 파일을 스트림으로 읽어옴
			//(스트림: 순서있는 자료의 흐름)
			//파일의 내용을 읽고 쓰려면 자료(데이터) 순서있게 나가고 들어와야 함
			//이것을 stream 이라고 함
			//환경설정파일이 문자로 되어있으므로 문자단위의 최상위 스트림인 Reader 객체 생성
			//mybatis 설정파일을 스트림으로 읽어오기 위해 Resources 클래스의 static 메소드인 getResourceAsReader 메소드 이용
			//매개변수로 설정파일의 경로를 포함한 이름 전달
			Reader reader = Resources.getResourceAsReader("com/example/demo/db/sqlMapConfig.xml");
			
			//생성한 스트림을 매개변수로 하는 SqlSessionFactory 객체 생성
			factory = new SqlSessionFactoryBuilder().build(reader);
			
			//사용했던 스트림 닫아줌
			reader.close();
		}catch (Exception e) {
			System.out.println("예외발생:"+e.getMessage());
		}
	}
	
	//mybatis 설정파일에 등록한 customerMapper 파일에 있는 sql 중 모든 고객목록을 검색하는 sql을 요청하는 메소드 정의
	//DBManager 객체를 생성하지 않고 사용할수 있도록 static
	public static List<CustomerVO> findAll(){
		
		//mybatis 설정파일에 있는 sql을 요청하기 위해 SqlSessionFactory를 통해 SqlSession을 얻어 와야 함
		SqlSession session = factory.openSession();
		
		//mybatis 설정파일에 있는 sql 설정파일의 네임스페이스와 아이디를 통해 요청
		List<CustomerVO> list = session.selectList("customer.findAll");
		
		//세션의 사용이 끝나면 닫아줌
		session.close();
		return list;
	}
}
