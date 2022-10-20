package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import DAO.CommentDBAO;
import DAO.UserDBAO;
import entity.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class ProfilePostList
 */
@WebServlet("/ProfilePostList")
public class ProfilePostList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProfilePostList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Long user_id = Long.parseLong(request.getParameter("user_id"));
		int is_me = 0;
		try {
			UserDBAO userdbao = new UserDBAO();
			User user = userdbao.findByUserid(user_id);
			PrintWriter out = response.getWriter(); 
			
//			Cookie[] cookie = request.getCookies();
//			Long id = (long)0;
//			for(int i=0;i<cookie.length;i++) {
//				if("token".equals(cookie[i].getName())) {
//					id = userdbao.identifyId(cookie[i].getValue());
//					if(id == user_id) {
//						is_me=1;
//					}
//				}
//			}
			Long id = userdbao.identifyId(request.getParameter("token"));
			if(id == user_id) {
				is_me=1;
			}
			
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
				CommentDBAO commentdbao = new CommentDBAO();
				JSONObject datajson = new JSONObject();
				datajson.put("is_me", is_me);
				datajson.put("user_name", user.getUserName());
				datajson.put("facepicture", user.getfacepic());
				datajson.put("phonenumber", user.getphone());
				datajson.put("email", user.getemail());
				datajson.put("dob", user.getdob());
				datajson.put("gender", user.getgender());
				datajson.put("post_list", JSONArray.fromObject(commentdbao.findCommentsByUser(user_id)));
//				datajson.put("bookmark_list", JSONArray.fromObject(userdbao.findBookmarksByUser(user_id)));
//				datajson.put("like_list", JSONArray.fromObject(userdbao.findLikesByUser(user_id)));
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