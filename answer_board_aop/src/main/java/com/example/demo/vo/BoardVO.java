package com.example.demo.vo;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class BoardVO {
	private int no;
	private String title;
	private String writer;
	private String pwd;
	private String content;
	private Date regdate;
	private int hit;
	private String fname;
	private int fsize;
	private int b_ref;
	private int b_level;
	private int b_step;
	private String ip;
	private MultipartFile uploadFile;
}
