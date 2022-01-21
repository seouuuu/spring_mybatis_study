package com.sist.controller;

import java.io.File;
import java.io.FileOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.sist.dao.GoodsDAO;
import com.sist.vo.GoodsVO;

@Controller
public class GoodsController {
	@Autowired
	private GoodsDAO dao;

	public void setDao(GoodsDAO dao) {
		this.dao = dao;
	}
	
	@RequestMapping("/listGoods.do")
	public ModelAndView list() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("list",dao.findAll());
		return mav;
	}
	
	@RequestMapping(value="/insertGoods.do",method = RequestMethod.GET)
	public void insertForm() {
		
	}
	
	@RequestMapping(value="/insertGoods.do",method = RequestMethod.POST)
	public ModelAndView insertSubmit(HttpServletRequest request, GoodsVO g) {
		//업로드할 폴더의 실경로를 알아오기 위해 request 변수를 매개변수로 선언
		
		String path = request.getRealPath("images");
		System.out.println("path:"+path);
		//request 객체를 통해 업로드할 폴더의 실제 경로를 읽어옴
		
		MultipartFile uploadFile = g.getUploadFile();
		//vo에 업로드한 파일의 정보를 받아옴
		
		String fname = uploadFile.getOriginalFilename();
		//업로드한 파일이름 알아봄
		
		byte[] data;
		try {
			data = uploadFile.getBytes();
			//업로드한 파일 내용 받아옴
			
			FileOutputStream fos = new FileOutputStream(path + "/" + fname);
			//서버에 파일을 출력하기 위한 스트림 생성
			
			fos.write(data);
			//서버에 파일을 출력
			
			fos.close();
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		g.setFname(fname);
		
		int re = dao.insert(g);
		ModelAndView mav = new ModelAndView("redirect:/listGoods.do");
		if(re!=1) {
			mav.setViewName("error");
			mav.addObject("msg","상품 등록에 실패");
		}
		return mav;
	}
	
	@RequestMapping("/detailGoods.do")
	public ModelAndView detail(int no) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("g",dao.findByNo(no));
		return mav;
	}
	
	@RequestMapping(value="/updateGoods.do",method = RequestMethod.GET)
	public ModelAndView updateForm( int no) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("g",dao.findByNo(no));
		return mav;
	}
	
	@RequestMapping(value="/updateGoods.do",method = RequestMethod.POST)
	public ModelAndView updateSubmit(HttpServletRequest request, GoodsVO g) {
		String path = request.getRealPath("images");
		
		//기존 사진이름을 변수에 담기
		String oldFname = g.getFname();
		
		//업로드한 파일 정보 받아오기
		MultipartFile uploadFile = g.getUploadFile();
		String fname = uploadFile.getOriginalFilename();
		byte []data;
		try {
			data = uploadFile.getBytes();
			
			//사진도 수정했다면(업로드한 파일이 있다면) 파일 복사
			if(fname != null && !fname.equals("")) {
				FileOutputStream fos = new FileOutputStream(path+"/"+fname);
				fos.write(data);
				fos.close();
				g.setFname(fname);
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		int re = dao.update(g);
		ModelAndView mav = new ModelAndView("redirect:/detailGoods.do?no="+g.getNo());
		if(re!=1) {
			mav.setViewName("error");
			mav.addObject("msg","상품 수정 실패");
		}else {		//수정에 성공
			if(fname != null && !fname.equals("")) {		//사진도 수정
				File file = new File(path + "/" + oldFname);
				file.delete();		//원래 사진 삭제
			}
		}
		return mav;
	}
	
	@RequestMapping(value="/deleteGoods.do")
	public ModelAndView deleteSubmit(HttpServletRequest request,int no) {
		String path = request.getRealPath("images");
		
		//지우려는 상품의 사진파일 이름 저장
		String oldFname = dao.findByNo(no).getFname();
		
		int re = dao.delete(no);
		ModelAndView mav = new ModelAndView("redirect:/listGoods.do");
		mav.addObject("g",dao.findByNo(no));
		if(re!=1) {
			mav.setViewName("error");
			mav.addObject("msg","상품 삭제 실패");
		}else {
			File file = new File(path + "/" + oldFname);
			file.delete();
		}
		return mav;
	}
}
