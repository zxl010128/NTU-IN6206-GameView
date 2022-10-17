package servlets;

import java.io.IOException;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.CommentDBAO;
import entity.Comment;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class Top10Post
 */
@WebServlet("/Top10Post")
public class Top10Post extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Top10Post() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			PrintWriter out = response.getWriter();
			CommentDBAO commentdbao = new CommentDBAO();
			List<Comment> toppost = new ArrayList<Comment>(); 
			toppost = commentdbao.rankByLikes();
			if(toppost==null) {
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
				JSONArray datajson = new JSONArray();
				for (int i = 0; i < toppost.size(); i++) {
					Comment comment = toppost.get(i);
					JSONObject gameobject = JSONObject.fromObject(comment);
					datajson.add(gameobject);
				}
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
		}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}