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

import DAO.CommentDBAO;
import DAO.GameDBAO;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class CommentData
 */
@WebServlet("/CommentData")
public class CommentData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommentData() {
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
			CommentDBAO commentdbao = new CommentDBAO();
			GameDBAO gamedbao = new GameDBAO();
			List<String> categories = gamedbao.getCategories();
			List<Long> categoryid = new ArrayList<Long>();
			JSONArray datajson = new JSONArray();
			for(int i=0;i<categories.size();i++) {
				String category = categories.get(i);
				categoryid = gamedbao.getCategoryId(category);
				int commentnum = commentdbao.totalComments(categoryid);
				JSONObject json = new JSONObject();
				json.put("category", category);
				json.put("comments", commentnum);
				datajson.add(json);
			}
			
			if (categories.size()==0||categoryid.size()==0||datajson.size()==0){
				JSONObject json = new JSONObject();
				json.put("data", "");
				json.put("message", "fail");
				json.put("status_code", 500);
				out.write(json.toString());
				out.flush();
				out.close();
				return;
			}
			else{
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