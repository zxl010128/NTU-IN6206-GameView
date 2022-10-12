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
 * Servlet implementation class Reset
 */
@WebServlet("/Reset")
public class Reset extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Reset() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter(); 
		String email = request.getParameter("email"); 
		try { 
		UserDBAO userdbao = new UserDBAO(); 
		boolean x  = userdbao.ifEmailExists(email); 
		if (x == false) { 
		JSONObject jsonObject=new JSONObject(); 
		jsonObject.put("message", "Email hasn't been registered! "); 
		out.write(jsonObject.toString()); 
		out.flush(); 
		return; 
		} 
		//reset code 
		boolean y = userdbao.sendResetCode(email); 
		if (y == false) { 
		JSONObject jsonObject=new JSONObject() ; 
		jsonObject.put("message", "Failed to send email! "); 
		out.write(jsonObject.toString()); 
		out.flush(); 
		return; 
		} 
		JSONObject jsonObject=new JSONObject() ; 
		jsonObject.put("message", "Reset code has been sent! "); 
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
