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
import net.sf.json.JSONArray;

import DAO.GameDBAO;
import entity.Game;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class AllGames
 */
@WebServlet("/AllGames")
public class AllGames extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AllGames() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		List<Game> games = new ArrayList<Game>(); 
		List<String> categories = new ArrayList<String>();
		JSONArray array = new JSONArray();
		try {
			GameDBAO gamedbao = new GameDBAO();
			categories = gamedbao.getCategories();
			for(int i=0;i<categories.size();i++) {
				String category = categories.get(i);
				games = gamedbao.findByCategory(category);
				JSONObject game = new JSONObject();
				game.put("category", category);
				game.put("games", JSONArray.fromObject(games));
				array.add(game);
			}
			
			if(array.size()==0) {
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
				JSONObject json = new JSONObject();
				json.put("data", array);
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