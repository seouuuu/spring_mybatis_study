package com.example.demo.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.vo.MemberVO;

@Controller
public class HelloController {

	@RequestMapping("/hello")
	public void hello(Model model) {
		System.out.println("컨트롤러 동작입니다.");
		String name = "홍길동";
		
		ArrayList<String> list = new ArrayList<String>();
		list.add("사과");
		list.add("귤");
		list.add("수박");
		model.addAttribute("name",name);
		model.addAttribute("list",list);
		
		ArrayList<MemberVO> memberList = new ArrayList<MemberVO>();
		memberList.add(new MemberVO(1,"홍길동",20,"서울"));
		memberList.add(new MemberVO(2,"이순신",24,"제주"));
		memberList.add(new MemberVO(3,"김유신",52,"인천"));
		
		model.addAttribute("memberList",memberList);
	}
}
