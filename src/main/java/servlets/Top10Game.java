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

import DAO.GameDBAO;
import entity.Game;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class Top10Game
 */
@WebServlet("/Top10Game")
public class Top10Game extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Top10Game() {
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
			GameDBAO gamedbao = new GameDBAO();
			List<Game> topgames = new ArrayList<Game>(); 
			topgames = gamedbao.rankByScore();
			if(topgames==null) {
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
				for (int i = 0; i < topgames.size(); i++) {
					Game game = topgames.get(i);
					JSONObject gameobject = JSONObject.fromObject(game);
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