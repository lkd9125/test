package com.practice.pj.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.practice.pj.dao.BoardDao;
import com.practice.pj.dao.MemberDao;
import com.practice.pj.dto.BoardDto;
import com.practice.pj.dto.CommentDto;
import com.practice.pj.dto.FileDto;
import com.practice.pj.dto.ListDto;
import com.practice.pj.dto.MemberDto;
import com.practice.pj.util.PagingUtil;

import lombok.extern.java.Log;

@Service
@Log
public class BoardService {
	private ModelAndView mv;

	private int listCnt = 10;

	@Autowired
	private MemberDao mdao;

	@Autowired
	private BoardDao bdao;

	@Transactional
	public String BoardInsert(MultipartHttpServletRequest multi, HttpSession session, RedirectAttributes rttr) {
		String view = null;
		String msg = null;

		BoardDto bdto = new BoardDto();

		bdto.setB_title(multi.getParameter("btitle"));
		bdto.setB_contents(multi.getParameter("bcontents").trim());
		bdto.setB_id(multi.getParameter("bid"));
		String fileres = multi.getParameter("fileCheck");

		try {
			bdao.BoardInsert(bdto);
			int bnum = bdto.getB_num();
			view = "redirect:list?pageNum=1";
			msg = "글 작성에 성공했습니다.";

			// 포인트 올리기
			MemberDto mdto = mdao.login(multi.getParameter("bid"));
			mdto.setM_point(mdto.getM_point() + 1);
			mdao.UPpoint(mdto);
			session.setAttribute("mb", mdto);

			// 파일뽑기
			if (fileres.equals("1")) {
				fileUpload(multi, bnum);
			}
		} catch (Exception e) {
			e.printStackTrace();
			view = "redirect:writeFrm";
			msg = "글 작성에 실패했습니다. 관리자에게 문의해주세요.";
		}
		rttr.addFlashAttribute("msg", msg);

		return view;
	}

	@Transactional
	private void fileUpload(MultipartHttpServletRequest multi, int bnum) throws Exception {
		String realPath = multi.getServletContext().getRealPath("/");
		realPath += "resources/uploadfile/";

		File folder = new File(realPath);
		if (!(folder.isDirectory())) {
			folder.mkdir();
		}

		Iterator<String> filename = multi.getFileNames();

		while (filename.hasNext()) {
			String fn = filename.next();

			List<MultipartFile> fList = multi.getFiles(fn);
			for (int i = 0; i < fList.size(); i++) {
				MultipartFile mf = fList.get(i);

				String oriname = mf.getOriginalFilename();
				String sysname = System.currentTimeMillis() + oriname.substring(oriname.lastIndexOf("."));

				FileDto fdto = new FileDto();

				fdto.setBf_num(bnum);
				fdto.setF_oriname(oriname);
				fdto.setF_sysname(sysname);

				File f = new File(realPath + sysname);
				mf.transferTo(f);

				bdao.BoardFileInsert(fdto);
			}
		}
	}

	public ModelAndView outList(ListDto dto, String name, HttpSession session) {

		mv = new ModelAndView();

		int num = dto.getPageNum();
		
		dto.setPageNum((num - 1) * listCnt);
		dto.setListCnt(listCnt);
		
		
		List<BoardDto> bList = bdao.getList(dto);
		
		session.setAttribute("pageNum", dto.getPageNum());

		dto.setPageNum(num);
		String page = pagingJob(dto , name);

		mv.addObject("bList", bList);
		mv.addObject("paging", page);
		mv.addObject("pageNum", dto.getPageNum());
		
		if(dto.getColname() != null) {
			session.setAttribute("list", dto);
		}
		else {//검색이 아닐 경우는 세션의 ListDto를 제거.
			session.removeAttribute("list");
		}
		

		mv.setViewName("boardList");
		return mv;
	}

	private String pagingJob(ListDto dto, String name) {
		String page = null;

		int maxnum = bdao.SelectCount(dto);
		
		int pageCnt = 5;
		String listname = null;

		switch (name) {
		case "list":
			listname = "list?";
			break;
		}
		
		if(dto.getColname() != null) {
			listname += "colname="+ dto.getColname()
				+ "&keyword=" + dto.getKeyword() + "&";
		}
		
		PagingUtil paging = new PagingUtil(maxnum, dto.getPageNum(), listCnt, pageCnt, listname);
		page = paging.makePaging();

		return page;
	}

	public ModelAndView Contents(int bnum) {
		mv = new ModelAndView();

		BoardDto bdto = bdao.getContents(bnum);
		List<FileDto> fList = bdao.getFile(bnum);
		List<CommentDto> cList = bdao.getComment(bnum);

		// 조회수 1증가
		int views = bdto.getB_views() + 1;
		bdto.setB_views(views);
		try {
			bdao.UPviews(bdto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		mv.addObject("BoardDto", bdto); // 게시글 출력용
		mv.addObject("fList", fList); // 파일 출력용
		mv.addObject("cList", cList); // 댓글 출력용

		mv.setViewName("boardContents");

		return mv;
	}

	@Transactional
	public Map<String, List<CommentDto>> CommentProc(CommentDto dto) {
		Map<String, List<CommentDto>> cmap = new HashMap<String, List<CommentDto>>();
		try {
			bdao.CommentInsert(dto);
			List<CommentDto> cList = bdao.getComment(dto.getCb_num());

			cmap.put("cList", cList);
		} catch (Exception e) {
			e.printStackTrace();
			cmap = null;
		}
		return cmap;
	}
	
	public ModelAndView BoardUpdateFrm(int b_num, int pageNum) {
		String view = null;
		mv = new ModelAndView();
		BoardDto dto = bdao.getContents(b_num);
		List<FileDto> fList = bdao.getFile(b_num);
		
		
		if(dto == null || fList == null) {
			view = "redirect:bcontents?b_num="+b_num+"&pageNum="+pageNum;
		}else {
			mv.addObject("BoardDto", dto);
			mv.addObject("fList",fList);
			view = "updateFrm";
		}
		mv.setViewName(view);
		
		return mv;
	}
	
	@Transactional
	public Map<String, List<FileDto>> delFile(int b_num, String sysname, HttpSession session){
		Map<String, List<FileDto>> fmap = new HashMap<String, List<FileDto>>();
		List<FileDto> fList = null;
		if(SingleDeleteFile(sysname, session)) {
			bdao.delFile(sysname);
			fList = bdao.getFile(b_num);
			
		}else {
			fmap = null;
		}
		
		fmap.put("fList", fList);
		
		return fmap;
	}
	
	private boolean SingleDeleteFile(String sysname, HttpSession session) {
		// realPath
		String realPath = session.getServletContext().getRealPath("/");
		realPath += "resources/uploadfile/";

		String deleteFileName = realPath + sysname;
		File file1 = new File(deleteFileName);

		return file1.delete(); // 파일 삭제에 성공한 경우
	}
	
	@Transactional
	public String updateProc(MultipartHttpServletRequest multi, RedirectAttributes rttr) {
		String view = null;
		String msg = null;
		
		BoardDto bdto = new BoardDto();
		
		int bnum = Integer.parseInt(multi.getParameter("bnum"));
		bdto.setB_title(multi.getParameter("btitle"));
		bdto.setB_contents(multi.getParameter("bcontents").trim());
		bdto.setB_num(bnum);
		
		String fileres = multi.getParameter("fileCheck");

		try {
			bdao.boardUpdate(bdto);


			// 파일올려
			if (fileres.equals("1")) {
				fileUpload(multi, bnum);
			}
			view = "redirect:bcontents?bnum="+bnum;
			msg = "글 수정에 성공했습니다.";
		} catch (Exception e) {
			e.printStackTrace();
			view = "redirect:BoardUpdateFrm";
			msg = "글 수정에 실패했습니다. 관리자에게 문의해주세요.";
		}
		rttr.addFlashAttribute("msg", msg);

		return view;
	}
	
	public String deleteBoard(int b_num, int pageNum,HttpSession session, RedirectAttributes rttr) {
		String view = null;
		String msg = null;
		
		try {
			fileDelete(b_num, session);
			bdao.deleteComment(b_num);
			bdao.deleteBoardFile(b_num);
			bdao.deleteBoard(b_num);
			msg = "삭제성공!";
			view = "redirect:list?pageNum="+pageNum;
		} catch (Exception e) {
			e.printStackTrace();
			view = "redirect:contents?bnum="+b_num;
			msg = "삭제실패!";
		}
		rttr.addFlashAttribute("msg", msg);
		mv.setViewName(view);

		
		return view;
	}
	
	private void fileDelete(Integer bnum, HttpSession session) {
		// realPath
		String realPath = session.getServletContext().getRealPath("/");
		realPath += "resources/uploadfile/";

		// 지울 사진 pick
		List<FileDto> blist = bdao.getFile(bnum);

		for (int i = 0; i < blist.size(); i++) {
			String deleteFileName = realPath + blist.get(i).getF_sysname();
			File file1 = new File(deleteFileName);
			file1.delete();
		}
	}
	
	public void fileDownload(FileDto dto, HttpServletResponse response, HttpSession session) {
		String realPath = session.getServletContext().getRealPath("/");
		realPath += "resources/uploadfile/";

		realPath += dto.getF_sysname();

		InputStream is = null;
		OutputStream os = null;

		try {
			String dfname = URLEncoder.encode(dto.getF_oriname(), "UTF-8");

			File file = new File(realPath);

			is = new FileInputStream(file);

			// 인터넷을 통해 전달하기 위한 설정
			response.setContentType("aplication/octet-stream");
			response.setHeader("content-Disposition", "attachment; filename=\"" + dfname + "\"");

			os = response.getOutputStream();

			byte[] buffer = new byte[1024]; // 1kb
			int length;
			while ((length = is.read(buffer)) != -1) {
				os.write(buffer);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				os.flush();
				os.close();
				is.close();
			} catch (IOException ie) {
				ie.printStackTrace();
			}
		}
	}
	
	
}
