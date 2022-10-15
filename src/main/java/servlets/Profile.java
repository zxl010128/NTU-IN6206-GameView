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
import entity.User;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class Profile
 */
@WebServlet("/Profile")
public class Profile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Profile() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Long user_id = Long.parseLong(request.getParameter("user_id"));
		try {
			UserDBAO userdbao = new UserDBAO();
			User user = userdbao.findByUserid(user_id);
//			request.setAttribute("user", user);
			
			PrintWriter out = response.getWriter(); 
//			JSONObject jsonObject=new JSONObject(); 
//			jsonObject.put("user_name", user.getUserName());
//			jsonObject.put("facepicture", user.getfacepic());
//			jsonObject.put("phonenumber", user.getphone());
//			jsonObject.put("email", user.getemail());
//			jsonObject.put("dob", user.getdob());
//			jsonObject.put("gender", user.getgender());
//			out.write(jsonObject.toString()); 
//			out.flush();
//			out.close();
			
			if (user==null) {
				JSONObject json = new JSONObject();
				json.put("data", "");
				json.put("message", "fail");
				json.put("status_code", 400);
				out.write(json.toString());
				out.flush();
				out.close();
				return;
			}
			else {
				JSONObject datajson = JSONObject.fromObject(user);
				JSONObject json = new JSONObject();
				json.put("data", datajson);
				json.put("message", "success");
				json.put("status_code", 200);
				out.write(json.toString());
				out.flush();
				out.close();
				return;
			}
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//redirect
		
		}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}