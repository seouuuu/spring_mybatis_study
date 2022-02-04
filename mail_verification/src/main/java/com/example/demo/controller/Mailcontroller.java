package com.example.demo.controller;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.dao.CardDAO;
import com.example.demo.dao.MemberDAO;
import com.example.demo.vo.MemberCardFee;
import com.example.demo.vo.MemberVO;

import kr.co.youiwe.webservice.BitSms;
import lombok.Setter;

@Controller
@Setter
public class Mailcontroller {
	
	@Autowired
	private MemberDAO dao;
	
	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public void insert_form() {		
	}
	
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String insert_submit(MemberVO m) {
		int re = dao.insert(m);
		if(re == 1) {
			return "회원가입 성공";
		}else {
			return "회원가입 실패";
		}
	}
	
	@Autowired
	private MailSender javamMailSender;
	
	@RequestMapping("/checkVerification")
	@ResponseBody
	public String mail(String type,String to) {
		System.out.println(type);
		System.out.println(to);
		
		String code = "1234"; 
		Random r = new Random();
		int a = r.nextInt(10);
		int b = r.nextInt(10);
		int c = r.nextInt(10);
		int d = r.nextInt(10);
		code = a + ""+b+""+c+""+d;
		
		if(type.equals("email")) {
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setSubject("인증코드 발송");
			mailMessage.setFrom("seou101010@gmail.com");
			mailMessage.setTo(to);
			mailMessage.setText("인증번호 : "+ code);
			try {
				javamMailSender.send(mailMessage);
			}catch (Exception e) {
				System.out.println("예외발생:"+e.getMessage());
			}
		}else {
			BitSms sms = new BitSms();
			sms.sendMsg("01025598279", to, code);
		}
		
		return code;
	}
	
	@Autowired
	private CardDAO dao2;
	
	@RequestMapping("/sendCardFee")
	@ResponseBody
	public String sendCardFee() {
		List<MemberCardFee> list = dao2.selectCardFee();
		
		for(MemberCardFee m:list) {
			String to = m.getEmail();
			String name = m.getName();
			int amount = m.getAmount();
			String content = name + "님, 이번달 요금은 "+ amount + "원입니다.";
			
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setSubject(name+"님 이번달 명세서 입니다.[담당자:윤서우]");
			mailMessage.setFrom("seou101010@gmail.com");
			mailMessage.setTo(to);
			mailMessage.setText(content);
			try {
				javamMailSender.send(mailMessage);
			}catch (Exception e) {
				System.out.println("예외발생:"+e.getMessage());
			}
			System.out.println(name+"님에게 메일을 발송하였습니다.");
		}
		return "카드명세서 메일을 발송하였습니다..";
	}
}
