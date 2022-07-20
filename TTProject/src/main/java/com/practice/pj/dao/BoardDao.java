package com.practice.pj.dao;

import java.util.List;
import java.util.Map;

import com.practice.pj.dto.BoardDto;
import com.practice.pj.dto.CommentDto;
import com.practice.pj.dto.FileDto;
import com.practice.pj.dto.ListDto;

public interface BoardDao {
	// 게시판 내용삽입
	public void BoardInsert(BoardDto dto);
	// 게시판 파일삽입
	public void BoardFileInsert(FileDto dto);
	// 리스트 뿌리기
	public List<BoardDto> getList(ListDto dto);
	// 게시판 내용 출력
	public BoardDto getContents(int bnum);
	// 게시판 파일 출력
	public List<FileDto> getFile(int bnum);
	// 게시판 조회수 올리기
	public void UPviews(BoardDto dto);
	
	// 댓글 삽입
	public void CommentInsert(CommentDto dto);
	// 댓글 가져오기
	public List<CommentDto> getComment(int bnum);
	
	// 게시글 업데이트 - 클릭한 파일 삭제
	public void delFile(String sysname);
	// 게시글 업데이트
	public void boardUpdate(BoardDto dto);
	
	// 댓글, 파일, 게시글
	public void deleteBoard(Integer bnum);
	public void deleteBoardFile(Integer bnum);
	public void deleteComment(Integer bnum);
	
	// 검색한 글 카운트
	public int SelectCount(ListDto dto);

}
