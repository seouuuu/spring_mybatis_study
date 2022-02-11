package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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
	@ResponseBody
	public List<DeptVO> list() {		//Ajax통신으로 요청되었을 때 jsp로 이동하지 않고 부서목록을 json으로 응답하도록 함
		return dao.findAll();			//스프링부트에서는 직접 json으로 변환하지 않아도 알아서 List를 반환하면 json으로 만들어 줌
	}
	
	@RequestMapping(value = "/insertDept.do", method = RequestMethod.GET)
	public void insertForm() {		
	}
	
	@RequestMapping(value = "/insertDept.do", method = RequestMethod.POST)
	public ModelAndView insertSubmit(DeptVO d) {
		ModelAndView mav = new ModelAndView("redirect:/listDept.do");
		int re= dao.insert(d);
		if(re != 1) {
			mav.setViewName("error");
			mav.addObject("msg", "부서등록에 실패파였습니다.");
		}
		return mav;
	}
}

