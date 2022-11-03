package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import DAO.UserDBAO;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class Logout
 */
@WebServlet("/Logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Logout() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		try {
		UserDBAO userdbao = new UserDBAO();
//		Cookie[] cookie = request.getCookies();
//		Long id = (long)0;
//		if(cookie == null) {
//			JSONObject jsonObject = new JSONObject();
//			jsonObject.put("data", "");
//			jsonObject.put("message", "fail");
//			jsonObject.put("status_code", 401);
//			out.write(jsonObject.toString()); 
//			out.flush(); 
//			out.close();
//			return; 
//		}
//		for(int i=0;i<cookie.length;i++) {
//			if("token".equals(cookie[i].getName())) {
//				id = userdbao.identifyId(cookie[i].getValue());
//				if(id == 0) {
//					JSONObject jsonObject = new JSONObject();
//					jsonObject.put("data", "");
//					jsonObject.put("message", "fail");
//					jsonObject.put("status_code", 401);
//					out.write(jsonObject.toString()); 
//					out.flush(); 
//					out.close();
//					return; 
//				}
//			}
//		}
		Long id = userdbao.identifyId(request.getParameter("token"));
		boolean x = userdbao.clearToken(id);
		if(x == false){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("data", "");
			jsonObject.put("message", "fail");
			jsonObject.put("status_code", 500); 
			out.write(jsonObject.toString()); 
			out.flush(); 
			out.close();
			return;
		}
//		Cookie cookie1 = new Cookie("token","");
//		cookie1.setMaxAge(0);
//		cookie1.setPath("/");
//		response.addCookie(cookie1);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("data", "");
		jsonObject.put("message", "success");
		jsonObject.put("status_code", 200); 
		out.write(jsonObject.toString()); 
		out.flush(); 
		out.close();
		return;
		}catch (Exception e) {
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
