package com.practice.pj.dto;

import lombok.Data;

@Data
public class ListDto {
	private String colname;
	private String keyword;
	private int pageNum;
	private int listCnt;
}
