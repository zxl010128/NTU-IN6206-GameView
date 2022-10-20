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
import DAO.CommentDBAO;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class AddLike
 */
@WebServlet("/AddLike")
public class AddLike extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddLike() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Long post_id = Long.parseLong(request.getParameter("post_id"));
		try {
			PrintWriter out = response.getWriter();
			UserDBAO userdbao = new UserDBAO();
			CommentDBAO commentdbao = new CommentDBAO();
			Long id = (long)0;
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
			id = userdbao.identifyId(request.getParameter("token"));
			if(id == 0) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("data", "");
				jsonObject.put("message", "fail");
				jsonObject.put("status_code", 401);
				out.write(jsonObject.toString()); 
				out.flush(); 
				out.close();
				return; 
			}
			boolean x = userdbao.newLike(id, post_id);
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
			boolean y = commentdbao.addLike(post_id);
			if (y == false) {
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
				int isgetcoin = commentdbao.isGetCoin(post_id);
				if(isgetcoin == 0) {
					boolean z = userdbao.addCoin(id, 50);
					if(z == false) {
						JSONObject jsonObject = new JSONObject();
						jsonObject.put("data", "");
						jsonObject.put("message", "fail");
						jsonObject.put("status_code", 500); 
						out.write(jsonObject.toString()); 
						out.flush(); 
						out.close();
						return;
					}
				}
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("data", "");
				jsonObject.put("status_code", 200); 
				jsonObject.put("message", "success"); 
				out.write(jsonObject.toString()); 
				out.flush();
				out.close();
				return;  
			}
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
