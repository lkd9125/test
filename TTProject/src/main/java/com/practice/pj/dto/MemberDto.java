package com.practice.pj.dto;

import lombok.Data;

@Data
public class MemberDto {
	private String m_id;
	private String m_pwd;
	private String m_name;
	private int m_point;
	private String g_name;
}
