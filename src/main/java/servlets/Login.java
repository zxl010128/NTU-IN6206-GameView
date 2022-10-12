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
				JSONObject jsonObject=new JSONObject(); 
				jsonObject.put("message", "Email doesn't exist! "); 
				out.write(jsonObject.toString()); 
				out.flush(); 
				return; 
			}
			x = userdbao.loginByEmail(input, password);
		}
		else {
			ifExists = userdbao.checkUsernameExists(input);
			if(ifExists == false){
				JSONObject jsonObject=new JSONObject(); 
				jsonObject.put("message", "Username doesn't exist! "); 
				out.write(jsonObject.toString()); 
				out.flush(); 
				return; 
			}
			x  = userdbao.loginByUsername(input, password); 
		}
		if (x == false) { 
		JSONObject jsonObject=new JSONObject(); 
		jsonObject.put("message", "Wrong Password! "); 
		out.write(jsonObject.toString()); 
		out.flush(); 
		return; 
		} 
		
		JSONObject jsonObject=new JSONObject() ; 
		// token 
		String token = userdbao.generateToken(input);
		if(token.equals("")) {
			jsonObject.put("message", "Login Failed "); 	 
			out.write(jsonObject.toString()); 
			out.flush();    
			return; 
		}
		
		Cookie cookie = new Cookie("token",token);
		cookie.setMaxAge(15*24*60*60);
		cookie.setPath("/");
		response.addCookie(cookie);
		
		jsonObject.put("message", "Login Succeeded "); 	 
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
