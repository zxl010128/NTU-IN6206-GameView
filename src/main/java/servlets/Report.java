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
 * Servlet implementation class Report
 */
@WebServlet("/Report")
public class Report extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Report() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter(); 
		String content = request.getParameter("content"); 
		try { 
		UserDBAO userdbao = new UserDBAO(); 
		boolean x = userdbao.report(content);
		if(x == false) {
			JSONObject json = new JSONObject();
			json.put("data", "");
			json.put("message", "fail");
			json.put("status_code", 500);
			out.write(json.toString());
			out.flush();
			out.close();
			return;
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
