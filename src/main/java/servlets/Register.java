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
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("data", "");
				jsonObject.put("message", "email existed");
				jsonObject.put("status_code", 400);
				out.write(jsonObject.toString()); 
				out.flush(); 
				out.close();
				return;
			} 
			boolean username_exists = userdbao.checkUsernameExists(username); 
			if (username_exists == true) { 
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("data", "");
				jsonObject.put("message", "username existed");
				jsonObject.put("status_code", 400);
				out.write(jsonObject.toString()); 
				out.flush(); 
				out.close();
				return;
			} 
			if(email.contains("@")==false) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("data", "");
				jsonObject.put("message", "wrong email format");
				jsonObject.put("status_code", 400);
				out.write(jsonObject.toString()); 
				out.flush(); 
				out.close();
				return;
			}
			if(email.length()>40) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("data", "");
				jsonObject.put("message", "too long email");
				jsonObject.put("status_code", 400);
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
			if(password.length()<6||password.length()>20) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("data", "");
				jsonObject.put("message", "invalid length of password");
				jsonObject.put("status_code", 400);
				out.write(jsonObject.toString()); 
				out.flush(); 
				out.close();
				return;
			}
			if(username.matches("^[a-zA-Z0-9]+$")==false) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("data", "");
				jsonObject.put("message", "invalid username");
				jsonObject.put("status_code", 400);
				out.write(jsonObject.toString()); 
				out.flush(); 
				out.close();
				return;
			}
			if(username.length()>20) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("data", "");
				jsonObject.put("message", "too long username");
				jsonObject.put("status_code", 400);
				out.write(jsonObject.toString()); 
				out.flush(); 
				out.close();
				return;
			}
			boolean y = userdbao.register(username,password,phonenumber,email,dob,gender); 
			if (y == true) { 
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("data", "");
				jsonObject.put("message", "success");
				jsonObject.put("status_code", 200);
				out.write(jsonObject.toString()); 
				out.flush(); 
				out.close();
			} 

			else { 
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("data", "");
				jsonObject.put("message", "fail");
				jsonObject.put("status_code", 500); 
				out.write(jsonObject.toString()); 
				out.flush(); 
				out.close();
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
