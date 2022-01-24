package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dao.DeptDao;
import com.example.demo.vo.DeptVO;

@Controller
public class DeptController {
	@Autowired
	private DeptDao dao;
	
	public void setDao(DeptDao dao) {
		this.dao = dao;
	}

	@RequestMapping("/listDept.do")
	public ModelAndView list() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("list",dao.findAll());
		return mav;
	}
	
	@RequestMapping(value = "insertDept.do",method= RequestMethod.GET)
	public void insertForm() {
	}
	
	@RequestMapping(value = "insertDept.do",method= RequestMethod.POST)
	public ModelAndView insertSubmit(DeptVO d) {
		ModelAndView mav = new ModelAndView("redirect:/listDept.do");
		int re = dao.insert(d);
		if(re!=1) {
			mav.setViewName("error");
			mav.addObject("msg","부서등록 실패");
		}
		return mav;
	}
}
