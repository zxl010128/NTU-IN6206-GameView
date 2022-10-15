package servlets;

import java.io.IOException;

import java.io.PrintWriter;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import DAO.UserDBAO;
import entity.User;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter(); 
		
		String input = request.getParameter("input"); 
		String password = request.getParameter("password"); 
		
		try { 
		UserDBAO userdbao = new UserDBAO(); 
		boolean x = false;
		boolean ifExists = false;
		if(input.contains("@")) {
			ifExists = userdbao.ifEmailExists(input);
			if(ifExists == false){
//				jsonObject.put("message", "Email doesn't exist! ");
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("data", "");
				jsonObject.put("message", "email doesn't exist");
				jsonObject.put("status_code", 400); 
				out.write(jsonObject.toString()); 
				out.flush(); 
				out.close();
				return;
			}
			x = userdbao.loginByEmail(input, password);
		}
		else {
			ifExists = userdbao.checkUsernameExists(input);
			if(ifExists == false){
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("data", "");
				jsonObject.put("message", "username doesn't exit");
				jsonObject.put("status_code", 400); 
				out.write(jsonObject.toString()); 
				out.flush(); 
				out.close();
				return;
			}
			x  = userdbao.loginByUsername(input, password); 
		}
		if (x == false) { 
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("data", "");
			jsonObject.put("message", "password is wrong");
			jsonObject.put("status_code", 400); 
			out.write(jsonObject.toString()); 
			out.flush(); 
			out.close();
			return;
		} 
		
		// token 
		String token = userdbao.generateToken(input);
		if(token.equals("")) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("data", "");
			jsonObject.put("message", "fail");
			jsonObject.put("status_code", 500); 
			out.write(jsonObject.toString()); 
			out.flush(); 
			out.close();
			return;
		}
		
		Cookie cookie = new Cookie("token",token);
		cookie.setMaxAge(15*24*60*60);
		cookie.setPath("/");
		response.addCookie(cookie);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("data", "");
		jsonObject.put("message", "success");
		jsonObject.put("status_code", 200); 
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
