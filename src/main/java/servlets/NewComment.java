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
import net.sf.json.JSONObject;

/**
 * Servlet implementation class NewComment
 */
@WebServlet("/NewComment")
public class NewComment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewComment() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Long game_id = Long.parseLong(request.getParameter("game_id"));
		String content = request.getParameter("content");
		try {
			PrintWriter out = response.getWriter();
			UserDBAO userdbao = new UserDBAO();
//			Cookie[] cookie = request.getCookies();
//			Long id = (long)0;
//			if(cookie == null) {
//				JSONObject jsonObject = new JSONObject();
//				jsonObject.put("data", "");
//				jsonObject.put("message", "fail");
//				jsonObject.put("status_code", 401);
//				out.write(jsonObject.toString()); 
//				out.flush(); 
//				out.close();
//				return; 
//			}
//			for(int i=0;i<cookie.length;i++) {
//				if("token".equals(cookie[i].getName())) {
//					id = userdbao.identifyId(cookie[i].getValue());
//					if(id == 0) {
//						JSONObject jsonObject = new JSONObject();
//						jsonObject.put("data", "");
//						jsonObject.put("message", "fail");
//						jsonObject.put("status_code", 401);
//						out.write(jsonObject.toString()); 
//						out.flush(); 
//						out.close();
//						return; 
//					}
//				}
//			}
			Long id = userdbao.identifyId(request.getParameter("token"));
			CommentDBAO commentdbao = new CommentDBAO();
			boolean x = commentdbao.insertComment(id, game_id, content);
			if(x == false) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("data", "");
				jsonObject.put("message", "fail");
				jsonObject.put("status_code", 500); 
				out.write(jsonObject.toString()); 
				out.flush(); 
				out.close();
				return;
			}
			else {
				boolean y = userdbao.addCoin(id, 1);
				if(y == false){
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
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("data", "");
					jsonObject.put("message", "success");
					jsonObject.put("status_code", 200); 
					out.write(jsonObject.toString()); 
					out.flush(); 
					out.close();
					return;}
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
