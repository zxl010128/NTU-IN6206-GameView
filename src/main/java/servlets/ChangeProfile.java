package servlets;

import java.io.IOException;

import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		
		Long id = Long.parseLong(request.getParameter("user_id")); 
		String username = request.getParameter("username"); 
		String email = request.getParameter("email"); 
		String phonenumber = request.getParameter("phonenumber"); 
		String facepicture = request.getParameter("facepicture"); 
		String password = request.getParameter("password"); 
		String dob = request.getParameter("dob"); 
		int gender = Integer.parseInt(request.getParameter("gender")); 

		try { 
			UserDBAO userdbao = new UserDBAO();
			Cookie[] cookie = request.getCookies();
			for(int i=0;i<cookie.length;i++) {
				if("token".equals(cookie[i].getName())) {
					boolean x = userdbao.verifyToken(id, cookie[i].getValue());
					if(x == false) {
						JSONObject jsonObject=new JSONObject(); 
						jsonObject.put("message", "Please login ");
						PrintWriter out = response.getWriter(); 
						out.write(jsonObject.toString()); 
						out.flush();    
						return; 
					}
				}
			}
			PrintWriter out = response.getWriter(); 
//			UserDBAO userdbao = new UserDBAO(); 
			boolean x = userdbao.changeProfile(username, facepicture, password, email, phonenumber, gender, dob, id);
			if (x == false) { 
				JSONObject jsonObject=new JSONObject() ; 
				jsonObject.put("message", "Modification failed! "); 
				out.write(jsonObject.toString()); 
				out.flush(); 
				return; 
			} 
				JSONObject jsonObject=new JSONObject(); 
				jsonObject.put("message", "Modification Succeeded! "); 
				out.write(jsonObject.toString()); 
				out.flush();    
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
