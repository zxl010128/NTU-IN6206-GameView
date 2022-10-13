package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.UserDBAO;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter(); 

		//		HttpSession session = request.getSession(); 

		String username = request.getParameter("username"); 

		String email = request.getParameter("email"); 

		String phonenumber = request.getParameter("phonenumber"); 

		//String facepicture = request.getParameter("facepicture"); 

		String password = request.getParameter("password"); 

		String dob = request.getParameter("dob"); 

		int gender = Integer.parseInt(request.getParameter("gender")); 

 

		try { 
			UserDBAO userdbao = new UserDBAO(); 
			boolean email_exists = userdbao.ifEmailExists(email); 
			if (email_exists == true) { 
				JSONObject jsonObject=new JSONObject() ; 
				jsonObject.put("message", "Email Already Existed! "); 
				out.write(jsonObject.toString()); 
				out.flush(); 
				return; 
			} 
			
			boolean username_exists = userdbao.checkUsernameExists(username); 
			if (username_exists == true) { 
				JSONObject jsonObject=new JSONObject() ; 
				jsonObject.put("message", "Username Already Existed! "); 
				out.write(jsonObject.toString()); 
				out.flush(); 
				return; 
			} 
//			//generate validation code and send email
//			boolean x = userdbao.sendResetCode(email); 
//			if (x == false) { 
//				JSONObject jsonObject=new JSONObject() ; 
//				jsonObject.put("message", "Failed to Send Email! "); 
//				out.write(jsonObject.toString()); 
//				out.flush(); 
//				return; 
//			} 
//
//			//validation 
//			boolean y = userdbao.validationCode();
 

			boolean y = userdbao.register(username,password,phonenumber,email,dob,gender); 
			if (y == true) { 
				JSONObject jsonObject=new JSONObject(); 
				jsonObject.put("message", "Register Succeeded"); 
				out.write(jsonObject.toString()); 
				out.flush();    
				return; 
			} 

			else { 
				JSONObject jsonObject=new JSONObject(); 
				jsonObject.put("message", "Register Failed"); 
				out.write(jsonObject.toString()); 
				out.flush();    
				return; 
			} 
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
