package servlets;

import java.io.IOException;

import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import entity.User;
import DAO.UserDBAO;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class ChangeProfile
 */
@WebServlet("/ChangeProfile")
public class ChangeProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangeProfile() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
//		String username = request.getParameter("username"); 
		String email = request.getParameter("email"); 
		String phonenumber = request.getParameter("phonenumber"); 
//		String facepicture = request.getParameter("facepicture"); 
//		String password = request.getParameter("password"); 
		String dob = request.getParameter("dob"); 
		int gender = Integer.parseInt(request.getParameter("gender")); 
		try { 
			PrintWriter out = response.getWriter();
			UserDBAO userdbao = new UserDBAO();
//			Cookie[] cookie = request.getCookies();
//			Long id = (long)0;
//			if(cookie == null) {
//				JSONObject jsonObject = new JSONObject();
//				jsonObject.put("data", "");
//				jsonObject.put("message", "fail");
//				jsonObject.put("status_code", 401);
//				out.write(jsonObject.toString()); 
//				out.flush(); 
//				out.close();
//				return; 
//			}
//			for(int i=0;i<cookie.length;i++) {
//				if("token".equals(cookie[i].getName())) {
//					id = userdbao.identifyId(cookie[i].getValue());
//					if(id == 0) {
//						JSONObject jsonObject = new JSONObject();
//						jsonObject.put("data", "");
//						jsonObject.put("message", "fail");
//						jsonObject.put("status_code", 401);
//						out.write(jsonObject.toString()); 
//						out.flush(); 
//						out.close();
//						return; 
//					}
//				}
//			}
			Long id = userdbao.identifyId(request.getParameter("token"));
			if(id == 0) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("data", "");
				jsonObject.put("message", "fail");
				jsonObject.put("status_code", 401);
				out.write(jsonObject.toString()); 
				out.flush(); 
				out.close();
				return; 
			}
			if(phonenumber.length()!=8) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("data", "");
				jsonObject.put("message", "wrong number format");
				jsonObject.put("status_code", 400);
				out.write(jsonObject.toString()); 
				out.flush(); 
				out.close();
				return;
			}
			User user=userdbao.findByUserid(id);
			if(email.equals(user.getemail())==false) {
				if(userdbao.ifEmailExists(email)) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("data", "");
				jsonObject.put("message", "email exists");
				jsonObject.put("status_code", 400);
				out.write(jsonObject.toString()); 
				out.flush(); 
				out.close();
				return;
			}
			}
			if(gender==3) {
				gender=user.getgender();
			}
			
			boolean x = userdbao.changeProfile(email, phonenumber, gender, dob, id);
			if (x == false) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("data", "");
				jsonObject.put("message", "fail");
				jsonObject.put("status_code", 500); 
				out.write(jsonObject.toString()); 
				out.flush(); 
				out.close();
				return;
//				JSONObject jsonObject=new JSONObject() ; 
//				jsonObject.put("message", "Modification failed! "); 
//				out.write(jsonObject.toString()); 
//				out.flush(); 
//				return; 
			} 
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("data", "");
				jsonObject.put("status_code", 200); 
				jsonObject.put("message", "success"); 
				out.write(jsonObject.toString()); 
				out.flush();
				out.close();
				return;  
		} catch (Exception e) { 
			// TODO Auto-generated catch block 
			e.printStackTrace(); 
			}
		
		}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}