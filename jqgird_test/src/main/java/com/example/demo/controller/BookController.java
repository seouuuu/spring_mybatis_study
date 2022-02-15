package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.dao.BookDAO;
import com.example.demo.vo.BookVO;

import lombok.Setter;

@Controller
@Setter
public class BookController {
	
	@Autowired
	private BookDAO dao;
	
	@RequestMapping(value="/listBook",produces = "application/xml")
	@ResponseBody
	public String listBook(int page,int rows,String sidx,String sord) {
		//System.out.println("page: "+page);
		//System.out.println("rows: "+rows);
//		if(sidx == null) {
//			System.out.println("null");
//		}
//		if(sidx=="") {
//			System.out.println("''입니다.");
//		}
//		System.out.println("sidx: "+sidx);
//		System.out.println("sord: "+sord);
		
		int records = dao.getTotalCount();
		int total = (int)Math.ceil(records/(double)rows);
		//System.out.println("전체 레코드수: " + records);
		//System.out.println("전체 페이지수: " + total);
		String r = "";
		r += "<rows>";
		r += "<page>"+page+"</page>";					//매개변수로 전달받은 페이지 설정
		r += "<total>"+total+"</total>";				//전체레코드 수에 따른 페이지 수 설정
		r += "<records>"+records+"</records>";			//db 연동하여 전체 레코드 수 설정
		
		//List<BookVO> list = dao.findAll();
		int start = (page-1)*rows +1;
		int end =  start + rows -1;
		HashMap map = new HashMap();
		map.put("start", start);
	    map.put("end", end);
	    map.put("sidx", sidx);
	    map.put("sord", sord);
		List<BookVO> list = dao.findAll(map);
		
		for(BookVO b : list) {
			r += "<row>";
			r += "<cell>"+b.getBookid()+"</cell>";
			r += "<cell>"+b.getBookname()+"</cell>";
			r += "<cell>"+b.getPublisher()+"</cell>";
			r += "<cell>"+b.getPrice()+"</cell>";
			r += "</row>";
		}
		
		r += "</rows>";
		return r;
	}
	
	@RequestMapping("/editBook")
	@ResponseBody
	public String editBook(String oper, BookVO b) {
		//System.out.println(oper);
		//System.out.println(b);
		if(oper.equals("add")) {
			dao.insertBook(b);
		}else if(oper.equals("edit")) {
			dao.updateBook(b);
		}else {
			dao.deleteBook(b.getBookid());
		}
		return "OK";
	}
}
