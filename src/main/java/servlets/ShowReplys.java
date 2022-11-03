package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.ProductDBAO;
import DAO.CommentDBAO;
import DAO.ReplyDBAO;
import DAO.UserDBAO;
import entity.Product;
import entity.Reply;
import entity.User;
import entity.Comment;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class ShowReplys
 */
@WebServlet("/ShowReplys")
public class ShowReplys extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowReplys() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		Long comment_id = Long.parseLong(request.getParameter("post_id"));
		Comment comment = new Comment();  
		try {
			CommentDBAO commentdbao = new CommentDBAO();
			comment = commentdbao.getById(comment_id);
			if(comment==null) {
				JSONObject json = new JSONObject();
				json.put("data", "");
				json.put("message", "fail");
				json.put("status_code", 500);
				out.write(json.toString());
				out.flush();
				out.close();
				return;
			}
			
			else {
				JSONObject datajson = new JSONObject();
				UserDBAO userdbao = new UserDBAO();
				datajson.put("comment", JSONObject.fromObject(comment));
				ReplyDBAO replydbao = new ReplyDBAO();
				List<Reply> replys = replydbao.getByCommentId(comment_id);
				JSONArray replyList = new JSONArray();
				for(int i=0;i<replys.size();i++) {
					JSONObject item = new JSONObject();
					Reply reply = replys.get(i);
         			Long userid = reply.getUserId();
					User user = userdbao.findByUserid(userid);
					System.out.println(user);
					item.put("user",JSONObject.fromObject(user));
					item.put("reply",JSONObject.fromObject(reply));
					replyList.add(item);
				}
				datajson.put("allreplys",replyList);
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
		
		// redirect
		
		}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}