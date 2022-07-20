package com.practice.pj.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class CommentDto {
	private int c_num;
	private int cb_num;
	private String c_contents;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm" , timezone = "Asia/Seoul")
	private Timestamp c_date;
	private String c_id;
}
