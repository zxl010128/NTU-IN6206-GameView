package servlets;

import java.io.IOException;

import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.GameDBAO;
import DAO.UserDBAO;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class GenderRatio
 */
@WebServlet("/GenderRatio")
public class GenderRatio extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GenderRatio() {
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
			UserDBAO userdbao = new UserDBAO();
			int femaleusers = userdbao.countFemaleUsers();
			int maleusers = userdbao.countMaleUsers();
			int unknownusers = userdbao.countUnknownUsers();
			if (femaleusers==-1||maleusers==-1||unknownusers==-1){
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
				JSONObject datajson=new JSONObject(); 
				datajson.put("Female Users", femaleusers);
				datajson.put("Male Users", maleusers);
				datajson.put("Unknown Users", unknownusers);
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