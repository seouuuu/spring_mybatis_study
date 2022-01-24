package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.example.demo.dao.CustomerDao;

@Controller
public class CustomerController {
	
	@Autowired
	private CustomerDao dao;

	public void setDao(CustomerDao dao) {
		this.dao = dao;
	}
	
	@RequestMapping("/listCustomer.do")
	public void list(Model model) {
		model.addAttribute("list",dao.findAll());
	}
}
