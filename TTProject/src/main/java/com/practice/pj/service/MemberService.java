package com.practice.pj.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.practice.pj.dao.MemberDao;
import com.practice.pj.dto.MemberDto;

import lombok.extern.java.Log;

@Service
@Log
public class MemberService {

	@Autowired
	private MemberDao mdao;
	@Autowired
	private BCryptPasswordEncoder pwdEncoder;

	private ModelAndView mv;

	// 회원가입 메서드
	@Transactional
	public String GmInsert(MemberDto dto, RedirectAttributes rttr) {
		String view = null;
		String msg = null;
		log.info("아이디 : " + dto.getM_id());
		log.info("비밀번호 : " + dto.getM_pwd());
		log.info("이름 : " + dto.getM_name());
		String pwd = pwdEncoder.encode(dto.getM_pwd());
		dto.setM_pwd(pwd);

		try {
			mdao.MInsert(dto);
			view = "redirect:/";
			msg = "회원가입성공!";
		} catch (Exception e) {
			e.printStackTrace();
			view = "redirect:joinFrm";
			msg = "회원가입 실패 관리자에게 문의하세요.";
		}

		rttr.addFlashAttribute("msg", msg);

		return view;
	}

	public ModelAndView loginProc(MemberDto dto, HttpSession session, RedirectAttributes rttr) {
		String view = null;
		String msg = null;
		mv = new ModelAndView();
		
		MemberDto dto1 = mdao.login(dto.getM_id());
		// select * from member where m_id = dto.getM_id();
		
		if (dto1 == null){
				view = "redirect:loginFrm";
				msg = "로그인실패, 아이디 및 비밀번호를 다시 확인해주세요";
		} else {
			if (pwdEncoder.matches(dto.getM_pwd(), dto1.getM_pwd())) {
				view = "redirect:list?pageNum=1";
				session.setAttribute("mb", dto1);
			} else {
				view = "redirect:loginFrm";
				msg = "로그인실패, 아이디 및 비밀번호를 다시 확인해주세요";
			}
		}
		
		
		
		rttr.addFlashAttribute("msg", msg);
		mv.setViewName(view);
		return mv;
	}

	public String logoutProc(HttpSession session) {
		String view = null;

		session.removeAttribute("mb");

		view = "redirect:/";

		return view;
	}

	public String idcheck(String m_id) {

		String msg = null;
		int res = mdao.idcheck(m_id);

		if (res == 1) {
			msg = "아이디 중복";
		} else {
			msg = "아이디 사용가능";
		}

		return msg;
	}
}
