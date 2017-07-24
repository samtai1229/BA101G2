package com.member.controller;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import com.member.model.MemberService;
import com.member.model.MemberVO;
import com.sec_ord.model.SecOrdService;
import com.sec_ord.model.SecOrdVO;

import utility.MailService;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class MemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public static byte[] getPicByteArray(Part part) throws IOException {
		InputStream fis = null;
		try {
			fis = part.getInputStream();
		} catch (IOException e) {

			e.printStackTrace();
		}

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int i;
		while ((i = fis.read(buffer)) != -1) {
			baos.write(buffer, 0, i);
		}
		baos.close();
		fis.close();

		return baos.toByteArray();

	}
	
	
	protected boolean allowUser(String account, String password) {
	    if ("tomcat".equals(account) && "tomcat".equals(password))
	      return true;
	    else
	      return false;
	  }

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		System.out.println("進來了 要做的是 " + action);
		String location = req.getParameter("location");
		System.out.println("我從"+location+"進來的");
		
		

		
		// change status
		if ("change_status".equals(action)) { // 來自select_page.jsp的請求
				System.out.println("in MemberServlet change_status");

			try {
				/***************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/
				String memno = req.getParameter("memno");
				String status = req.getParameter("status");
				
				System.out.println("memno: "+memno);
				System.out.println("status: "+ status);
				
				/*************************** 2.開始查詢資料 *****************************************/
				
				MemberService mSvc = new MemberService();
				mSvc.updateStatus(memno, status);

				System.out.println("update success!");
				
				MemberVO mVO = mSvc.getOneMember(memno);
				
				HttpSession session = req.getSession();
				session.setAttribute("memno", mVO.getMemno());
				session.setAttribute("memname", mVO.getMemname());
				session.setAttribute("status", mVO.getStatus());
				
				System.out.println(
				 "after change_status: session - memno :" + session.getAttribute("memno")
				+", memname: "+ session.getAttribute("memname")
				+", status: "+ session.getAttribute("status"));
				
				
				
				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				e.getStackTrace();
				System.out.println("change exception");
			}
		}
		
		
		
		//從sec_ord移過來
			if ("get_second_ord_per_member".equals(action)) { // 來自select_page.jsp的請求

				List<String> errorMsgs = new LinkedList<String>();
				// Store this set in the request scope, in case we need to
				// send the ErrorPage view.
				req.setAttribute("errorMsgs", errorMsgs);
				String addaction = req.getParameter("addaction");
				System.out.println("addaction: "+addaction);

				try {
					/***************************
					 * 1.接收請求參數 - 輸入格式的錯誤處理
					 **********************/
					String memno = req.getParameter("memno");
					System.out.println("memno: "+memno);

					/*************************** 2.開始查詢資料 *****************************************/
					SecOrdService soSvc = new SecOrdService();
					List<SecOrdVO> list1 = soSvc.getAll();
					List<SecOrdVO> list2 = new ArrayList<SecOrdVO>();
					for(SecOrdVO ord : list1)
					{
						if(ord.getMemno().equals(memno))
						{
							list2.add(ord);
						}
					}
				
					System.out.println("addaction:"+addaction);
					/***************************
					 * 3.查詢完成,準備轉交(Send the Success view)
					 *************/
					req.setAttribute("memno", memno);
					req.setAttribute("list", list2); // 資料庫取出的empVO物件,存入req
					req.setAttribute("status", "all");
					String url="";
						if("backend".equals(addaction))
							url= "/backend/member/backSecOrdList.jsp";
						if("frontend".equals(addaction))
							url= "/frontend/member/frontSecOrdList.jsp";						
						
					RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交listOneEmp.jsp
					successView.forward(req, res);

					/*************************** 其他可能的錯誤處理 *************************************/
				} catch (Exception e) {
					errorMsgs.add("無法取得資料:" + e.getMessage());
					RequestDispatcher failureView = req.getRequestDispatcher("/frontend/second_order/select_page.jsp");
					failureView.forward(req, res);
				}
			}
		
		
		
		
		
		
		if ("getAllRentOrder".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/
				String memno = req.getParameter("memno");
				System.out.println("memno"+memno);
				String addaction = req.getParameter("addaction");
				System.out.println("addaction: "+ addaction);
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/member/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				

				/*************************** 2.單純轉址 *****************************************/
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/member/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				
				 // 3.查詢完成,準備轉交(Send the Success view)
				req.setAttribute("memno", memno);
				String url =""; 
					if("frontend".equals(addaction))
						url= "/frontend/member/frontRentOrdList.jsp";
					if("backend".equals(addaction))
						url= "/backend/member/backRentOrdList.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/member/select_page.jsp");
				failureView.forward(req, res);
			}
		}

		
		
		
		
//進入會員專區前:		
		if ("getOne_For_Enter".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/
				HttpSession session = req.getSession();
				
				String memno =(String) session.getAttribute("memno");
				String memname = (String) session.getAttribute("memname");
				String status = (String) session.getAttribute("status");
				
				
				System.out.println("getOne_For_Enter in");
				System.out.print("(s)memno: "+memno);
				System.out.print(", (s)status: "+status);
				
				if(memname!=null){
					System.out.println(", (s)memname: "+memname);
				}else{
					System.out.println(", memname is empty");
				}
					
				
				
				System.out.println("line-------------1");
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					System.out.println("!errorMsgs.isEmpty() 1");
					RequestDispatcher failureView = req.getRequestDispatcher("/index.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				
				System.out.println("line-------------2");
				/*************************** 2.開始查詢資料 *****************************************/
				MemberService memSvc = new MemberService();
				MemberVO memVO = memSvc.getOneMember(memno);

				if (memVO == null) {
					System.out.println("line-------------no memVO");
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					System.out.println("line-------------!errorMsgs.isEmpty() 2");
					RequestDispatcher failureView = req.getRequestDispatcher("/index.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				System.out.println("line-------------3");
				/***************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 *************/

				req.setAttribute("memVO", memVO); // 資料庫取出的empVO物件,存入req
				String url = "/frontend/member/frontMember.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				System.out.println("line-------------exception");
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/index.jsp");
				failureView.forward(req, res);
			}
		}

		
		if ("logout".equals(action)) {
			
			System.out.println(" ==logout==");
			
			req.getSession().invalidate();
			
			req.getRequestDispatcher("/index.jsp").forward(req, res);
		}
		
		
//登入後註冊		
		if ("register".equals(action)) {
			System.out.println("MemberServlet register");
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				//acc, pwd_1, pwd_2, mail
				String acc = req.getParameter("new_acc");
				String pwd_1 = req.getParameter("new_pwd");
				String pwd_2 = req.getParameter("pass");
				String mail = req.getParameter("mail");
				
				System.out.println("acc:"+acc+" pwd:"+pwd_1+" pass"+pwd_2+" mail"+mail);
			
				if ( mail==null||(mail.trim()).length() == 0 ||   pwd_2==null||(pwd_2.trim()).length() == 0 ||acc == null || pwd_1 == null || (acc.trim()).length() == 0 || (pwd_1.trim()).length() == 0) {
					System.out.println("欄位不可為空");
					errorMsgs.add("欄位不可為空");
				}
				if(!pwd_2.equals(pwd_1))
				{
					System.out.println("2次密碼不一致");
					errorMsgs.add("2次密碼不一致");
				}

				System.out.println("==============2");
				/*************************** 2.開始檢查帳號是否存在 *****************************************/
				MemberService memSvc = new MemberService();
				
				MemberVO memVO = null;
						
				if (memSvc.checkAccIsExisted(acc)) {
					errorMsgs.add("該帳號已存在");
					System.out.println("該帳號已存在");
					 memVO = memSvc.getOneMemberByAcc(acc);
					 req.setAttribute("error2", errorMsgs.get(0));
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/index.jsp");
					failureView.forward(req, res);
					System.out.println("!errorMsgs.isEmpty()");
					return;// 程式中斷
				}
				System.out.println("==============3");
				 // 3.查詢完成,準備轉交(Send the Success view) 轉交到會員專區的網頁
				
				
			//	"INSERT INTO MEMBER (memno,memname,sex,birth,mail,phone,addr,acc,pwd,idcard1,idcard2,license,status) "
				//+ "VALUES ('MEM'||LPAD(TO_CHAR(memno_seq.NEXTVAL), 6,'0'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,'uncompleted')";
				
				memSvc.insert("", "", new Timestamp(System.currentTimeMillis()), mail, "", "", acc, pwd_1, null, null, null);
				System.out.println("==============3-2");
				
				memVO = memSvc.getOneMemberByAccAndPwd(acc, pwd_1);
//mail/////////////				
                //new  MailService(memVO.getMail(),"註冊成功 ","歡迎您成為會員");
                System.out.println("==============4");
                
				req.setAttribute("memVO", memVO);
				
				//session
				req.getSession().setAttribute("memno", memVO.getMemno());
				req.getSession().setAttribute("status", memVO.getStatus());
				
				System.out.println(
						"after register session-memno: " + req.getSession().getAttribute("memno")
										     +", status:"+ req.getSession().getAttribute("status"));
				
				
				req.getRequestDispatcher("/frontend/member/frontMember.jsp").forward(req, res); 
				
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				System.out.println("regist exception");
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/member/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		
		
//登入		
		if ("login".equals(action)) {
			System.out.println("Login servlet");
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			//req.removeAttribute("action");
			
//			Enumeration<String> enumber = req.getAttributeNames();
//			while(enumber.hasMoreElements()){
//				String name = (String) enumber.nextElement();
//				System.out.println(name+" : "+req.getAttribute(name));
//			}

			try {
				
				HttpSession session = req.getSession();
				System.out.println("url_action="+session.getAttribute("action"));
				System.out.println("motno="+session.getAttribute("motno"));
				System.out.println("dayrange="+session.getAttribute("dayrange"));
				
				
				String acc = req.getParameter("acc");
				String pwd = req.getParameter("pwd");
				if (acc == null || pwd == null || (acc.trim()).length() == 0 || (pwd.trim()).length() == 0) {
					System.out.println("error empty acc");
					errorMsgs.add("帳號/密碼不可為空");
				}


				/*************************** 2.開始比對帳密會員資料 *****************************************/
				MemberService memSvc = new MemberService();
				MemberVO memVO = memSvc.getOneMemberByAccAndPwd(acc, pwd);

				if (memVO == null) {
					System.out.println("error empty acc");
					errorMsgs.add("無此帳號");
				} else {
					if (!pwd.equals(memVO.getPwd())) {
						System.out.println("error acc / pwd");
						errorMsgs.add("帳號/密碼錯誤");
					}
				}
				// Send the use back to the form, if there were errors
				
				if (!errorMsgs.isEmpty()) {
					System.out.println("error !errorMsgs.isEmpty()");
					req.setAttribute("error", errorMsgs.get(0));
					RequestDispatcher failureView = req.getRequestDispatcher("/index.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/***************************
				 * 3.查詢完成,準備轉交(Send the Success view) 轉交到會員專區的網頁
				 *************/

//login後: session要帶的有 memno, memname, status 
				req.setAttribute("memVO", memVO); // 資料庫取出的empVO物件,存入req
				
				
				session.setAttribute("memno", memVO.getMemno());
				session.setAttribute("memname", memVO.getMemname());
				session.setAttribute("status", memVO.getStatus());
				
				System.out.println(
				 "after login: session - memno :" + session.getAttribute("memno")
				+", memname: "+ session.getAttribute("memname")
				+", status: "+ session.getAttribute("status"));
				
				String url = null;
				if(memVO.getStatus().equals("completed")||memVO.getStatus().equals("confirmed") || memVO.getStatus().equals("unconfirmed"))
				{
					if("rental".equals((String)session.getAttribute("action"))){
						System.out.println("in rental url");
						req.setAttribute("action", "quick_search_product_2");
						System.out.println("action: "+ req.getAttribute("action"));
						url = "/backend/rent_ord/rentOrd.do";
					}else{
						url = location;
					}
					System.out.println("url= "+url);
				}
				else
				{
					req.setAttribute("addStatus", "uncompleted");
					 url = "/frontend/member/frontMember.jsp";
				}
				
				Enumeration<String> enumber2 = req.getAttributeNames();
				while(enumber2.hasMoreElements()){
					String name = (String) enumber2.nextElement();
					System.out.println(name+" 2: "+req.getAttribute(name));
				}

				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("帳密錯誤:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(req.getRequestURI()+"#tab-1");
				failureView.forward(req, res);
			}
		}


		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/
				String memno = req.getParameter("memno");

				if (memno == null || (memno.trim()).length() == 0) {
					errorMsgs.add("請輸入會員編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/member/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				try {
					if (memno.length() != 9 || !memno.contains("MEM"))
						throw new Exception();
				} catch (Exception e) {
					errorMsgs.add("會員編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/member/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				MemberService memSvc = new MemberService();
				MemberVO memVO = memSvc.getOneMember(memno);

				if (memVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/member/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/***************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 *************/

				req.setAttribute("memVO", memVO); 
				
				HttpSession session = req.getSession();
				session.setAttribute("memno", memVO.getMemno());
				session.setAttribute("memname", memVO.getMemname());
				session.setAttribute("status", memVO.getStatus());
				
				System.out.println(
				 "after getOne_For_Display -> MemberArea: session - memno :" + session.getAttribute("memno")
				+", memname: "+ session.getAttribute("memname")
				+", status: "+ session.getAttribute("status"));
				
				
				String url = "/backend/member/listOneMember.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/member/select_page.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOne_For_Update".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String memno = req.getParameter("memno");
				String addAction = req.getParameter("addAction");
				System.out.println("addAction = "+addAction);
				/*************************** 2.開始查詢資料 ****************************************/
				MemberService memSvc = new MemberService();
				MemberVO memVO = memSvc.getOneMember(memno);
				System.out.println("我拿到了memno:" + memno);
				System.out.println("我拿到了VO:" + memVO);
				System.out.println("我拿到了VO:" + memVO.getMemno());
				/***************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 ************/
				System.out.println("我拿到了VO 準備轉交");
				req.setAttribute("memVO", memVO); // 資料庫取出的empVO物件,存入req
				

				HttpSession session = req.getSession();
				session.setAttribute("memno", memVO.getMemno());
				session.setAttribute("memname", memVO.getMemname());
				session.setAttribute("status", memVO.getStatus());
				
				System.out.println(
				 "after getOne_For_Update: session - memno :" + session.getAttribute("memno")
				+", memname: "+ session.getAttribute("memname")
				+", status: "+ session.getAttribute("status"));
				
				
				
				String url="";
				if("modifyMember".equals(addAction)){
					url = "/backend/member/update_member_input.jsp";
				}else if("frontMember".equals(addAction)){
					url = "/frontend/member/frontend_update_member_input.jsp";
				}else{
					url = "/backend/member/listOneMember.jsp";
				}
					
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交update_emp_input.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 ************************************/
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}
		
		
		
		
		

		if ("update".equals(action)) { // 來自update_emp_input.jsp的請求
			System.out.println("準備更新!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/
				String memname = req.getParameter("memname").trim();
				System.out.println("拿到" + memname);
				String memno = req.getParameter("memno");
				System.out.println("拿到" + memno);
				String sex = req.getParameter("sex");
				System.out.println("拿到" + sex);
				String acc = req.getParameter("acc");
				System.out.println("拿到" + acc);
				String pwd = req.getParameter("pwd");
				System.out.println("拿到" + pwd);
				String addr = req.getParameter("address");
			       addr = (addr.isEmpty())? "" : addr;
				String phone = req.getParameter("phone");
				       phone =(req.getParameter("phone").isEmpty())? "": req.getParameter("phone");
				System.out.println("拿到" + phone);
				String mail = req.getParameter("mail");
				System.out.println("拿到" + mail);
				Timestamp birth = Timestamp.valueOf((req.getParameter("birth").isEmpty())? Timestamp.valueOf(LocalDateTime.now()).toString():req.getParameter("birth"));
				System.out.println("拿到" + birth);
		
				Part idcard1 = req.getPart("idcard1");
				System.out.println("拿到idcard1:大小" + idcard1.getSize());
				Part idcard2 = req.getPart("idcard2");
				System.out.println("拿到idcard2:大小" + idcard2.getSize());
				Part license = req.getPart("license");
				System.out.println("拿到license:大小" + license.getSize());
				String status = req.getParameter("status");
				System.out.println("拿到" + status);
				
	
				MemberVO memVO = new MemberVO();
				memVO.setMemname(memname);
				memVO.setMemno(memno);
				memVO.setStatus(status);
				memVO.setAcc(acc);
				memVO.setPwd(pwd);
				memVO.setPhone(phone);
				memVO.setMail(mail);
				memVO.setAddr(addr);
				if(idcard1.getSize()!=0)
				   memVO.setIdcard1(getPicByteArray(idcard1));
				if(idcard2.getSize()!=0)
					memVO.setIdcard2(getPicByteArray(idcard2));
				if(license.getSize()!=0)
					memVO.setLicense(getPicByteArray(license));
				memVO.setBirth(birth);
				memVO.setSex(sex);
				Timestamp credate = Timestamp.valueOf(req.getParameter("credate"));
				memVO.setCredate(credate);
				System.out.println("for member update test, in memberServlet line:615");
				if(memname.length()!=0 && birth!=null && phone.length()!=0 && addr.length()!=0 && sex.length()!=0)
					status="unconfirmed";
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("memVO", memVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/member/addMember.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始修改資料 *****************************************/
				MemberService memSvc = new MemberService();
	            MemberVO oldVO = memSvc.getOneMember(memno);
				byte[] id1 =oldVO.getIdcard1();
				byte[] id2 =oldVO.getIdcard2();
				byte[] lic =oldVO.getLicense();
				
				memVO = memSvc.update(memno, memname, sex, birth, mail, phone, addr, acc, pwd,  (idcard1.getSize()==0)? id1 :getPicByteArray(idcard1),
						 (idcard2.getSize()==0)? id2 :getPicByteArray(idcard2), (license.getSize()==0)? lic :getPicByteArray(license), status, credate);
				System.out.println("修改完成");
				/***************************
				 * 3.修改完成,準備轉交(Send the Success view)
				 *************/
				req.setAttribute("memVO", memSvc.getOneMemberByAcc(acc)); // 資料庫update成功後,正確的的empVO物件,存入req
				System.out.println("準備轉交");
				
				
				HttpSession session = req.getSession();
				session.setAttribute("memno", memVO.getMemno());
				session.setAttribute("memname", memVO.getMemname());
				session.setAttribute("status", memVO.getStatus());
				
				System.out.println(
				 "after update: session - memno :" + session.getAttribute("memno")
				+", memname: "+ session.getAttribute("memname")
				+", status: "+ session.getAttribute("status"));
				
				
				
				String url = "/frontend/member/listOneMember.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				System.out.println("GGGGGGGGGGGGGGGGGGGGGGG");
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/member/listOneMember.jsp");
				failureView.forward(req, res);
			}
		}

		if ("insert".equals(action)) { // 來自addSpot.jsp的請求
			System.out.println("我要新增會員資料");
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 *************************/
				String memname = req.getParameter("memname").trim();
				String sex = req.getParameter("sex");
				String acc = req.getParameter("acc");
				String pwd = req.getParameter("pwd");
				String addr = req.getParameter("address");
				       addr = (addr.isEmpty())? "" : addr;
				String phone = req.getParameter("phone");
			    phone =(req.getParameter("phone").isEmpty())? "0900000000": req.getParameter("phone");
				String mail = req.getParameter("mail");
				Timestamp birth = Timestamp.valueOf((req.getParameter("birth").isEmpty())?"1999-01-01 00:00:00":req.getParameter("birth")+" 00:00:00");
				System.out.println("拿到" + birth);
				Part idcard1 = req.getPart("idcard1");
				Part idcard2 = req.getPart("idcard2");
				Part license = req.getPart("license");

				MemberVO memVO = new MemberVO();
				memVO.setMemname(memname);
				memVO.setAcc(acc);
				memVO.setPwd(pwd);
				memVO.setPhone(phone);
				memVO.setMail(mail);
				memVO.setAddr(addr);
				memVO.setIdcard1(getPicByteArray(idcard1));
				memVO.setIdcard2(getPicByteArray(idcard2));
				memVO.setLicense(getPicByteArray(license));
				memVO.setBirth(birth);
				memVO.setSex(sex);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("memVO", memVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/member/addMember.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				MemberService memSvc = new MemberService();
				memVO = memSvc.insert(memname, sex, birth, mail, phone, addr, acc, pwd, getPicByteArray(idcard1),
						getPicByteArray(idcard2), getPicByteArray(license));

				/***************************
				 * 3.新增完成,準備轉交(Send the Success view)
				 ***********/
				System.out.println("轉到全部會員清單");
				
				HttpSession session = req.getSession();
				session.setAttribute("memno", memVO.getMemno());
				session.setAttribute("memname", memVO.getMemname());
				session.setAttribute("status", memVO.getStatus());
				
				System.out.println(
				 "after insert: session - memno :" + session.getAttribute("memno")
				+", memname: "+ session.getAttribute("memname")
				+", status: "+ session.getAttribute("status"));
				
				
				String url = "/backend/member/backendMember.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/member/addMember.jsp");
				failureView.forward(req, res);
			}
		}

		if ("delete".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			String requestURL = req.getParameter("requestURL"); // 送出刪除的來源網頁路徑:
																// 可能為【/emp/listAllEmp.jsp】
																// 或
																// 【/dept/listEmps_ByDeptno.jsp】
																// 或 【
																// /dept/listAllDept.jsp】

			try {
				/*************************** 1.接收請求參數 ***************************************/
				String memno = req.getParameter("memno");

				/*************************** 2.開始刪除資料 ***************************************/
				MemberService mmSvc = new MemberService();

				mmSvc.delete(memno);
				;

				/***************************
				 * 3.刪除完成,準備轉交(Send the Success view)
				 ***********/
				// DeptService deptSvc = new DeptService();
				// if(requestURL.equals("/dept/listEmps_ByDeptno.jsp") ||
				// requestURL.equals("/dept/listAllDept.jsp"))
				// req.setAttribute("listEmps_ByDeptno",deptSvc.getEmpsByDeptno(empVO.getDeptno()));
				// // 資料庫取出的list物件,存入request
				//
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url); // 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				//
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}
		
		
		
		
		
		if ("update_backend_verified".equals(action)||"update_frontend_verified".equals(action)) {
			System.out.println("update_verified");
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

//			try {
				/***************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/
				String memname = req.getParameter("memname").trim();
				System.out.println("拿到" + memname);
				String memno = req.getParameter("memno");
				System.out.println("拿到" + memno);
				String sex = req.getParameter("sex");
				System.out.println("拿到" + sex);
				String acc = req.getParameter("acc");
				System.out.println("拿到" + acc);
				String pwd = req.getParameter("pwd");
				System.out.println("拿到" + pwd);
				String addr = req.getParameter("address");
			       addr = (addr.isEmpty())? "" : addr;
				String phone = req.getParameter("phone");
				       phone =(req.getParameter("phone").isEmpty())? "": req.getParameter("phone");
				System.out.println("拿到" + phone);
				String mail = req.getParameter("mail");
				System.out.println("拿到" + mail);
				
				
				//date-handle
				SimpleDateFormat sdf2 = null;
				
				if("update_backend_verified".equals(action))
					sdf2 = new SimpleDateFormat("yy-MM-dd hh:mm:ss");
				if("update_frontend_verified".equals(action))
					sdf2 = new SimpleDateFormat("yy/MM/dd");
				
				
				java.util.Date date;
				long longTime;
				
				System.out.println("birth: "+ req.getParameter("birth"));
				java.sql.Timestamp birth = null;
				try {
					date = (java.util.Date) sdf2.parse(req.getParameter("birth"));// String to Date
																						
					longTime = date.getTime(); // 取long
					birth = new java.sql.Timestamp(longTime); // 切為SQL專用格式
					System.out.println("start_time (SQL) : "+ birth);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				System.out.println("拿到" + birth);
		
				Part idcard1 = req.getPart("idcard1");
				System.out.println("拿到idcard1:大小" + idcard1.getSize());
				Part idcard2 = req.getPart("idcard2");
				System.out.println("拿到idcard2:大小" + idcard2.getSize());
				Part license = req.getPart("license");
				System.out.println("拿到license:大小" + license.getSize());
				String status = req.getParameter("status");
				System.out.println("拿到status: " + status);
				
	
				MemberVO memVO = new MemberVO();
				memVO.setMemname(memname);
				memVO.setMemno(memno);
				memVO.setStatus(status);
				memVO.setAcc(acc);
				memVO.setPwd(pwd);
				memVO.setPhone(phone);
				memVO.setMail(mail);
				memVO.setAddr(addr);
				if(idcard1.getSize()!=0)
				   memVO.setIdcard1(getPicByteArray(idcard1));
				if(idcard2.getSize()!=0)
					memVO.setIdcard2(getPicByteArray(idcard2));
				if(license.getSize()!=0)
					memVO.setLicense(getPicByteArray(license));
				memVO.setBirth(birth);
				memVO.setSex(sex);
				Timestamp credate = Timestamp.valueOf(req.getParameter("credate"));
				memVO.setCredate(credate);
				if(memname.length()!=0 && birth!=null && phone.length()!=0 && 
						addr.length()!=0 && sex.length()!=0 && "uncompleted".equals(status))
					status="unconfirmed";
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("memVO", memVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/member/addMember.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始修改資料 *****************************************/
				MemberService memSvc = new MemberService();
	            MemberVO oldVO = memSvc.getOneMember(memno);
				byte[] id1 =oldVO.getIdcard1();
				byte[] id2 =oldVO.getIdcard2();
				byte[] lic =oldVO.getLicense();
				
				memVO = memSvc.update(memno, memname, sex, birth, mail, phone, addr, acc, pwd,  (idcard1.getSize()==0)? id1 :getPicByteArray(idcard1),
						 (idcard2.getSize()==0)? id2 :getPicByteArray(idcard2), (license.getSize()==0)? lic :getPicByteArray(license), status, credate);
				
				
				//當memname有更新後，把memname放到尚為空的memname
				if(req.getSession().getAttribute("memname")==null&&!memname.isEmpty()){
					req.getSession().setAttribute("memname", memname);
					System.out.println("session - memname add: "+ req.getSession().getAttribute("memname"));
				}
					
				
				System.out.println("修改完成");
				/***************************
				 * 3.修改完成,準備轉交(Send the Success view)
				 *************/
				
				MemberVO memVO2 = memSvc.getOneMemberByAcc(acc);
				
				req.setAttribute("memVO", memVO2); // 資料庫update成功後,正確的的empVO物件,存入req
				req.setAttribute("prev", req.getRequestURI());
				
				
				
				
				HttpSession session = req.getSession();
				session.setAttribute("memno", memVO2.getMemno());
				session.setAttribute("memname", memVO2.getMemname());
				session.setAttribute("status", memVO2.getStatus());
				
				System.out.println(
				 "after update_verified: session - memno :" + session.getAttribute("memno")
				+", memname: "+ session.getAttribute("memname")
				+", status: "+ session.getAttribute("status"));
				
				
				System.out.println("準備轉交");
				String url="";
				if("update_backend_verified".equals(action))
					url= "/backend/member/backendMember.jsp";
				else if("update_frontend_verified".equals(action))
					url= "/frontend/member/frontend_update_member_input.jsp";
				
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
//			} catch (Exception e) {
//				errorMsgs.add("修改資料失敗:" + e.getMessage());
//				System.out.println("GGGGGGGGGGGGGGGGGGGGGGG");
//				RequestDispatcher failureView = req.getRequestDispatcher("/backend/member/listOneMember.jsp");
//				failureView.forward(req, res);
//			}
		}
	}
}
