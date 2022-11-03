package servlets;

import java.io.IOException;


import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.GameDBAO;
import DAO.UserDBAO;
import DAO.CommentDBAO;
import entity.Game;
import entity.User;
import entity.Comment;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class ShowComments
 */
@WebServlet("/ShowComments")
public class ShowComments extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowComments() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		Long game_id = Long.parseLong(request.getParameter("game_id"));
		try {
			GameDBAO gamedbao = new GameDBAO();
			Game game = gamedbao.findById(game_id);
			if (game == null){
				JSONObject json = new JSONObject();
				json.put("data", "");
				json.put("message", "fail");
				json.put("status_code", 400);
				out.write(json.toString());
				out.flush();
				out.close();
				return;
			}
			else{
				JSONObject datajson = new JSONObject();
				datajson.put("game", JSONObject.fromObject(game));
				CommentDBAO commentdbao = new CommentDBAO();
				UserDBAO userdbao = new UserDBAO();
				List<Comment> comments = commentdbao.findCommentByGame(game_id);
				JSONArray commentings = new JSONArray();
				for(int i=0;i<comments.size();i++) {
					JSONObject commenting = new JSONObject();
					Comment comment = comments.get(i);
					Long userid = comment.getUserId();
					User user = userdbao.findByUserid(userid);
					commenting.put("user",JSONObject.fromObject(user));
					commenting.put("comment",JSONObject.fromObject(comment));
					commentings.add(commenting);
				}
				datajson.put("allcomments",commentings);
				
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