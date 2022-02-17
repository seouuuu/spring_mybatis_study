package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dao.BookDAO;
import com.example.demo.vo.BookVO;

import lombok.Setter;

@Controller
@Setter
public class BookController {
	
	@Autowired
	private BookDAO dao;
	
	@RequestMapping("/listBook")
	public void list(Model model){
		model.addAttribute("list",dao.findAll());
	}
	
	@RequestMapping("/detailBook/{bookid}")
	public ModelAndView detail(@PathVariable int bookid){
		ModelAndView mav = new ModelAndView();
		BookVO b = dao.findByBookid(bookid);
		mav.setViewName("detailBook.html");
		mav.addObject("b", b);
		return mav;
	}
	
	@RequestMapping(value="/updateBook/{bookid}",method = RequestMethod.GET)
    public ModelAndView updateForm(@PathVariable int bookid,Model model) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("updateBook.html");
        model.addAttribute("b", dao.findByBookid(bookid));
        return mav;
    }
	
	@RequestMapping(value="/updateBook/{bookid}",method = RequestMethod.POST)
	public ModelAndView update(@PathVariable BookVO b){
		ModelAndView mav = new ModelAndView();
		dao.update(b);
		mav.setViewName("updateBook.html");
		return mav;
	}
	
	@RequestMapping("/deleteBook/{bookid}")
	public ModelAndView delete(@PathVariable int bookid){
		ModelAndView mav = new ModelAndView();
		dao.delete(bookid);
		mav.setViewName("deleteBook.html");
		return mav;
	}
	
}
