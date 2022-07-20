package com.practice.pj;



import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.practice.pj.dto.MemberDto;
import com.practice.pj.service.MemberService;


@Controller
public class HomeController {

	@Autowired
	private MemberService msv;
	
	private ModelAndView mv;
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	
	@GetMapping("/")
	public String home() {
		logger.info("home()");
		
		return "home";
	}
	
	@GetMapping("/joinFrm")
	public String GjoinFrm() {
		logger.info("joinFrm()");
		return "joinFrm";
	}
	
	@PostMapping("/joinProc")
	public String GmInsert(MemberDto dto, RedirectAttributes rttr) {
		logger.info("memInsert()");
		String view = null;
		
		view = msv.GmInsert(dto, rttr);
		
		return view;
	}
	
	@GetMapping("/loginFrm")
	public String GloginFrm() {
		logger.info("loginFrm()");
		return "loginFrm";
	}
	
	@PostMapping("/loginProc")
	public ModelAndView loginProc(MemberDto dto , HttpSession session, RedirectAttributes rttr) {
		logger.info("loginProc()");
		
		mv = msv.loginProc(dto, session, rttr);
		
		return mv;
	}
	
	@GetMapping("/logout")
	public String logoutProc(HttpSession session) {
		String view = null;
		
		view = msv.logoutProc(session);	
		
		return view; 
	}
	
	@GetMapping(value="/idcheck" , produces = "application/String; charset=UTF-8")
	@ResponseBody
	public String IdCheck(String m_id){
		logger.info("idcheck()" + m_id);
		
		String res = null;
		
		res = msv.idcheck(m_id);
		
		logger.info(res);
		
		return res;
	}
	
	
}
