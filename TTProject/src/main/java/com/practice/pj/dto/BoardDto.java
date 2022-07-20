package com.practice.pj.dto;


import lombok.Data;

@Data
public class BoardDto {
	private int b_num;
	private String b_title;
	private String b_contents;
	private String b_id;
	private int b_views;
}
