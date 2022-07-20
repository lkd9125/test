package com.practice.pj;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.practice.pj.dto.CommentDto;
import com.practice.pj.dto.FileDto;
import com.practice.pj.dto.ListDto;
import com.practice.pj.service.BoardService;

@Controller
public class BoardController {
	
	private ModelAndView mv;
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Autowired
	private BoardService bsv;
	
	@GetMapping("/list")
	public ModelAndView boardList(ListDto dto, HttpSession session) {
		logger.info("boardList()");
		
		String name = "list";
		
		mv = bsv.outList(dto,name, session);
		
		return mv;
	}
	
	@GetMapping("/writeFrm")
	public ModelAndView wirteFrm(String pageNum) {
		logger.info("writeFrm() 페이지 번호 : " + pageNum);
		
		mv = new ModelAndView();
		mv.addObject("pageNum", pageNum);
		
		mv.setViewName("writeFrm");
		return mv;
	}
	
	@PostMapping("/boardWriteProc")
	public String BoardInsertProc(MultipartHttpServletRequest multi,HttpSession session,RedirectAttributes rttr) {
		logger.info("boardWriteProc()");
		String view = null;
		
		view = bsv.BoardInsert(multi, session ,rttr);
		
		return view;
	}
	
	@GetMapping("/bcontents")
	public ModelAndView Contents(int bnum) {
		logger.info("Contents()");
		
		mv = bsv.Contents(bnum);
		
		return mv;
	}
	
	
	@PostMapping(value="/commentProc" , produces="application/json; charset=UTF-8")
	@ResponseBody
	public Map<String, List<CommentDto>> commnetProc(CommentDto dto){
		logger.info("commentProc()");
		
		Map<String, List<CommentDto>> cmap = bsv.CommentProc(dto);
		
		return cmap;
	}
	
	@GetMapping("/BoardUpdateFrm")
	public ModelAndView BoardUpdateFrm(int b_num, int pageNum) {
		logger.info("BoardUpdateFrm");
		
		mv = bsv.BoardUpdateFrm(b_num, pageNum);
		
		return mv;
	}
	
	@PostMapping(value="delFile" , produces="application/json; charset=UTF-8")
	@ResponseBody
	public Map<String, List<FileDto>> delFile(int b_num, String sysname, HttpSession session){
		logger.info("delFile()");
		
		Map<String, List<FileDto>> fmap = bsv.delFile(b_num, sysname, session);
		
		return fmap;
	}
	
	@PostMapping("/updateProc")
	public String updateProc(MultipartHttpServletRequest multi, RedirectAttributes rttr) {
		logger.info("updateProc()");
		String view = null;
		
		view = bsv.updateProc(multi, rttr);
		
		return view;
	}
	
	@GetMapping("/deleteProc")
	public String deleteBoard(int b_num, int pageNum,HttpSession session, RedirectAttributes rttr) {
		String view = null;
		
		view = bsv.deleteBoard(b_num, pageNum, session, rttr);
		
		return view;
	}
	
	@GetMapping("/download")
	public void download(FileDto dto, HttpServletResponse response, HttpSession session) {
		logger.info("download()");
		
		bsv.fileDownload(dto, response, session);
	}
}
