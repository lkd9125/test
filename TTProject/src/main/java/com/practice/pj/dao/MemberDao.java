package com.practice.pj.dao;

import com.practice.pj.dto.MemberDto;

public interface MemberDao {
	// 회원정보 입력
	public void MInsert(MemberDto dto);
	// 회원 아이디가 맞으면 DTO 가져오는 메서드
	public MemberDto login(String m_id);
	// 아이디 중복체크 메서드
	public int idcheck(String m_id);
	// 포인트 올리기
	public void UPpoint(MemberDto dto);
}
