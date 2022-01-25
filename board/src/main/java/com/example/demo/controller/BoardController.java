package com.example.demo.controller;

import java.io.File;
import java.io.FileOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dao.BoardDAO;
import com.example.demo.vo.BoardVO;

@Controller
public class BoardController {
	@Autowired
	private BoardDAO dao;

	public void setDao(BoardDAO dao) {
		this.dao = dao;
	}
	
	@RequestMapping("/listBoard")
	public void list(Model model) {
		model.addAttribute("list",dao.findAll());
	}
	
	@RequestMapping("/detailBoard")
	public void detail(Model model,int no) {
		model.addAttribute("b", dao.findByNO(no));
	}
	
	@RequestMapping(value="/insertBoard",method = RequestMethod.GET)
	public void insertForm() {
	}
	
	@RequestMapping(value="/insertBoard",method = RequestMethod.POST)
	public ModelAndView insertSubmit(HttpServletRequest request,BoardVO b) {
		ModelAndView mav = new ModelAndView("redirect:/listBoard");
		String path = request.getRealPath("upload");
		System.out.println("path:"+path);
		b.setFname("");
		MultipartFile uploadFile = b.getUploadFile();
		String fname = uploadFile.getOriginalFilename();
		byte []data = null;
	
		if(fname!= null && !fname.equals("")) {
			b.setFname(fname);
		}
		
		int re = dao.insert(b);
		
		if(re==1) {
			try {
				data = uploadFile.getBytes();
				if(fname!= null && !fname.equals("")) {
					FileOutputStream fos = new FileOutputStream(path+"/"+fname);
					fos.write(data);
					fos.close();
				}
			}catch (Exception e) {
				// TODO: handle exception
			}
		}else {
			mav.setViewName("error");
			mav.addObject("msg","게시물 등록 실패");
		}
		return mav;
	}
	
	@RequestMapping(value="/updateBoard",method = RequestMethod.GET)
	public void updateForm(int no,Model model) {
		model.addAttribute("b", dao.findByNO(no));
	}
	
	@RequestMapping(value="/updateBoard",method = RequestMethod.POST)
	public ModelAndView updateSubmit(HttpServletRequest request,BoardVO b) {
		ModelAndView mav = new ModelAndView("redirect:/listBoard");
		String path = request.getRealPath("upload");
		String oldFname = b.getFname();
		MultipartFile uploadFile = b.getUploadFile();
		String fname = uploadFile.getOriginalFilename();
		
		if(fname != null && !fname.equals("")) {
			b.setFname(fname);
		}
		int re = dao.update(b);
		if(re==1) {
			//게시물 수정에 성공했고 첨부파일도 수정했다면 파일복사
			if(fname != null && !fname.equals("")) {
				try {
					byte []data = uploadFile.getBytes();
					FileOutputStream fos = new FileOutputStream(path+"/"+fname);
					fos.write(data);
					fos.close();
					
					//원래 게시물에 첨부파일이 있었다면 원래파일 삭제
					if(oldFname!=null && !oldFname.equals("")) {
						File file = new File(path +"/"+oldFname);
						file.delete();
					}
				}catch (Exception e) {
					// TODO: handle exception
				}
			}
		}else {
			mav.setViewName("error");
			mav.addObject("msg","게시판 수정 실패");
		}
		return mav;
	}
}
