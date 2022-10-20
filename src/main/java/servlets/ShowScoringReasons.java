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
import DAO.ScoreDBAO;
import DAO.UserDBAO;
import entity.Score;
import entity.Game;
import entity.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class ShowScoringReasons
 */
@WebServlet("/ShowScoringReasons")
public class ShowScoringReasons extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowScoringReasons() {
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
				ScoreDBAO scoredbao = new ScoreDBAO();
				UserDBAO userdbao = new UserDBAO();
				List<Score> scores = scoredbao.findScoringByGame(game_id);
				JSONArray scorings = new JSONArray();
				for(int i=0;i<scores.size();i++) {
					JSONObject scoring = new JSONObject();
					Score score = scores.get(i);
					Long userid = score.getUserId();
					User user = userdbao.findByUserid(userid);
					scoring.put("user",JSONObject.fromObject(user));
					scoring.put("score",JSONObject.fromObject(score));
					scorings.add(scoring);
				}
				datajson.put("scorings",scorings);
				
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