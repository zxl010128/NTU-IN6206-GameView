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
			
		UserDBAO userdbao = new UserDBAO(); 
		boolean x  = userdbao.validationCode(email, resetcode); 
		if (x == false) { 
		JSONObject jsonObject=new JSONObject(); 
		jsonObject.put("message", "Reset Code is wrong! "); 
		out.write(jsonObject.toString()); 
		out.flush(); 
		return; 
		} 
		boolean y = userdbao.resetPassword(email, newPwd);
		if(y == false) {
			JSONObject jsonObject=new JSONObject(); 
			jsonObject.put("message", "Resetting failed! "); 
			out.write(jsonObject.toString()); 
			out.flush(); 
			return; 
		}
		JSONObject jsonObject=new JSONObject() ; 
		jsonObject.put("message", "Your password has been reset! "); 
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
