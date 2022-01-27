package com.example.demo.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dao.BoardDAO;
import com.example.demo.vo.BoardVO;
import com.example.demo.vo.MemberVO;

@Controller
public class BoardController {
	@Autowired
	private BoardDAO dao;

	public void setDao(BoardDAO dao) {
		this.dao = dao;
	}
	
	@RequestMapping("/listBoard")
	public void list(
			HttpSession session,		//정렬컬럼,검색컬럼,검색어를 상태유지 하기 위해 session 변수를 선언
			Model model, 				//View 페이지에서 필요한 데이터를 유지하기 위해 Model 선언
			String searchColumn,		//검색컬럼을 받아오기 위한 변수
			String keyword,				//검색어를 받아오기 위한 변수
			String orderColumn, 		//정렬컬럼을 받아오기 위한 변수
			String writer,
			@RequestParam(value="pageNum",defaultValue = "1") int pageNum	//페이지 번호를 받아오기 위한 변수
			) {
		
//		MemberVO m =(MemberVO)session.getAttribute("member");
//		System.out.println("list==>로그인한 회원의 이름:" +m.getName());
		
		if(writer == null && session.getAttribute("writer")!= null) {
			writer = (String)session.getAttribute("writer");
		}
		
		//만약 새로운 정렬이 없고 세션에 정렬컬럼명이 저장되어 있다면 세션에 저장된 정렬컬럼명을 읽어옴
		if(orderColumn == null && session.getAttribute("orderColumn")!=null) {
			orderColumn = (String)session.getAttribute("orderColumn");
		}
		
		//만약 새로운 검색어가 없고 세션에 검색컬럼명과 검색어가 저장되어 있다면 세션에 저장된 검색컬럼명과 검색어를 읽어옴
		if(keyword == null) {
			if(session.getAttribute("keyword")!=null) {
				searchColumn = (String)session.getAttribute("searchColumn");
				keyword = (String)session.getAttribute("keyword");
			}
		}
		
		System.out.println("정렬컬럼:"+orderColumn);
		System.out.println("페이지번호:"+pageNum);
		System.out.println("검색컬럼:"+searchColumn);
		System.out.println("검색어:|"+keyword+"|");
		System.out.println("작성자:|"+writer+"|");
		
//		if(orderColumn == null) {
//			orderColumn = "no";
//		}
		
		//현재페이지에 보여줄 시작레코드와 마지막레코드의 위치 계산
		int start = (pageNum-1)*dao.pageSize + 1;
		int end = start + dao.pageSize - 1;
		
		//dao가 게시물 목록을 검색할 때 필요한 정보(정렬컬럼명,검색컬럼명,검색어,현재페이지에 보여줄 시작레코드,마지막레코드)들을 맵에 저장
		HashMap map = new HashMap();
		map.put("orderColumn", orderColumn);
		map.put("searchColumn", searchColumn);
		map.put("keyword", keyword);
		map.put("start", start);
		map.put("end", end);
		map.put("writer", writer);
		
		//dao를 통해 검색한 결과를 model에 저장
		//이때 findAll메소드에서 전체레코드수를 구하고 그 값을 가지고 전체페이지수도 계산
		model.addAttribute("list",dao.findAll(map));
		
		//dao에 계산된 전체페이지수를 model에 상태유지
		model.addAttribute("totalPage",dao.totalPage);
		
		//만약 정렬하였다면 정렬 칼럼이름을 세션에 저장하여 상태유지
		if(orderColumn != null) {
			session.setAttribute("orderColumn", orderColumn);
		}
		
		//만약 검색하였다면 검색한 컬럼이름과 검색어를 세션에 저장하여 상태유지
		if(keyword != null) {
			session.setAttribute("searchColumn", searchColumn);
			session.setAttribute("keyword", keyword);
		}
		
		//작성글이 여러개 일때 페이징 처리
		if(writer != null) {
			session.setAttribute("writer", writer);
		}
	}
	
	@RequestMapping("/detailBoard")
	public void detail(Model model,int no) {
		dao.updateHit(no);
		model.addAttribute("b", dao.findByNO(no));
	}
	
	@RequestMapping(value="/insertBoard",method = RequestMethod.GET)
	public void insertForm(Model model) {
		model.addAttribute("no",dao.getNextNo());
	}
	
	@RequestMapping(value="/insertBoard",method = RequestMethod.POST)
	public ModelAndView insertSubmit(HttpServletRequest request,BoardVO b) {
		String ip = request.getRemoteAddr();
		b.setIp(ip);
		System.out.println(ip);
		
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
	public ModelAndView updateSubmit(HttpServletRequest request,BoardVO b,String pwd) {
		String path = request.getRealPath("upload");
		ModelAndView mav = new ModelAndView("redirect:/listBoard");
		String oldFname = b.getFname();
		MultipartFile uploadFile = b.getUploadFile();
		String fname = uploadFile.getOriginalFilename();
		if(fname != null && !fname.equals("")) {
			b.setFname(fname);
		}
		int re = dao.update(b);
		if(re == 1) {
			//게시물 수정에 성공했고, 또 첨부파일도 수정했다면 파일복사
			if(fname != null && !fname.equals("")) {
				try {
					byte []data = uploadFile.getBytes();
					FileOutputStream fos = new FileOutputStream(path + "/" + fname);
					fos.write(data);
					fos.close();
					
					//원래게시물이 첨부파일이 있었다면 원래파일을 삭제
					if(oldFname != null && !oldFname.equals("")) {
						File file = new File(path + "/" +oldFname);
						file.delete();
					}
				}catch (Exception e) {
					// TODO: handle exception
				}
			}
		}else {
			
		}
	
		return mav;
	}
	
	@RequestMapping(value="/deleteBoard",method = RequestMethod.GET)
	public void deleteForm(int no,Model model) {
		model.addAttribute("no",no);
	}
	
	@RequestMapping(value="/deleteBoard",method = RequestMethod.POST)
	public ModelAndView deleteSubmit(int no,String pwd,HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("redirect:/listBoard");
		String oldFname = dao.findByNO(no).getFname();
		
		HashMap map = new HashMap();
		map.put("no", no);
		map.put("pwd", pwd);
		int re = dao.delete(map);
		if(re==1) {
			if(oldFname != null & !oldFname.equals("")) {
				String path = request.getRealPath("upload");
				File file = new File(path+"/"+oldFname);
				file.delete();
			}
		}else {
			mav.setViewName("error");
			mav.addObject("msg","게시물 삭제 실패");
		}
		return mav;
	}
}
