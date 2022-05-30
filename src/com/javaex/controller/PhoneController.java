package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.PhoneDao;
import com.javaex.vo.PersonVo;

@WebServlet("/pbc") // 이름 바꿔주면 됨(브라우저 부르는주소)
public class PhoneController extends HttpServlet {

	// 필드
	private static final long serialVersionUID = 1L;

	// 생성자 -->기본 디폴트 생성자 사용
	/*
	 * public PhoneController() { super();
	 * 
	 * }
	 */

	// 메서드 getter/setter
	// 메서드 일반
	// -->get방식으로 요청시 호출 메서드
	
		//post 방식일때 한글깨짐 방지
		request.setCharacterEncoding("UTF-8");
		
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("controller");

		String action = request.getParameter("action");
		System.out.println(action);

		if ("list".equals(action)) {// 리스트일때
			// 데이터 가져오기
			PhoneDao phoneDao = new PhoneDao();
			List<PersonVo> phoneList = phoneDao.getPhoneList();
			System.out.println(phoneList);

			// request에 데이터 추가
			// -->****포워드 시킬게 여러개인 경우에는 servlet객체(controller) request안에 담아서(attribute) 가져옴
			request.setAttribute("pList", phoneList);

			// 데이터 + html --> html 짜기에는 jsp가 편함--> jsp로 처리하기
			// println("html 코드 작성")
			RequestDispatcher rd = request.getRequestDispatcher("/list.jsp");
			rd.forward(request, response); // **forward --> request, response 두개 항상 있어야함
		} else if ("writeForm".equals(action)) {// 등록폼일때

			// 포워드
			// 포워드
			RequestDispatcher rd = request.getRequestDispatcher("/writeForm.jsp");
			rd.forward(request, response);

		}else if("write".equals(action)) {//등록일때
			
			//파라미터에서 값 꺼내기(name,hp,company)
			 String name= request.getParameter("name");
			 String hp= request.getParameter("hp");
			 String company= request.getParameter("company");
			 
			//vo만들어서 값 초기화
			 PersonVo personVo= new PersonVo(name, hp, company); 
			 /*
			 //phoneDao.phoneInsert()를 통해 저장하기
			 PhoneDao phoneDao = new PhoneDao();
			 int count = phoneDao.phoneInsert(personVo);
			 System.out.println(count);
			
			 //리다이렉트 list
			 response.sendRedirect("/phonebook2/pbc?action=list");
			  */
		}else if("delete".equals(action)) {//삭제일때
		
			//파라미터에서 id값을 꺼낸다
			int id =Integer.parseInt(request.getParameter("id"));
		
			//phoneDao.phoneDelete()를 통해 저장하기
			PhoneDao phoneDao = new PhoneDao();
			int count = phoneDao.phoneDelete(id);
			System.out.println(count);
			
		}
		
		else {
			System.out.println("action 파라미터 없음");
		}
		
		
		
		
	}

	// -->post방식으로 요청시 호출 메서드
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
