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
 * Servlet implementation class Validation
 */
@WebServlet("/Validation")
public class Validation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Validation() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter(); 
		String resetcode = request.getParameter("resetcode"); 
		String email = request.getParameter("email");
		String newPwd = request.getParameter("newpassword");
		try { 
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
			if(newPwd.length()<6||newPwd.length()>20) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("data", "");
				jsonObject.put("message", "invalid length of password");
				jsonObject.put("status_code", 400);
				out.write(jsonObject.toString()); 
				out.flush(); 
				out.close();
				return;
			}
		UserDBAO userdbao = new UserDBAO(); 
		boolean email_exists = userdbao.ifEmailExists(email); 
		if (email_exists == false) { 
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("data", "");
			jsonObject.put("message", "email doesn't exist");
			jsonObject.put("status_code", 400);
			out.write(jsonObject.toString()); 
			out.flush(); 
			out.close();
			return;
		}
		boolean x  = userdbao.validationCode(email, resetcode); 
		if (x == false) { 
			JSONObject json = new JSONObject();
			json.put("data", "");
			json.put("message", "wrong reset code");
			json.put("status_code", 400);
			out.write(json.toString());
			out.flush();
			out.close();
			return;
		} 
		boolean y = userdbao.resetPassword(email, newPwd);
		if(y == false) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("data", "");
			jsonObject.put("message", "fail");
			jsonObject.put("status_code", 500); 
			out.write(jsonObject.toString()); 
			out.flush(); 
			out.close();
			return;
		}
		else{
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("data", "");
			jsonObject.put("status_code", 200); 
			jsonObject.put("message", "success"); 
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