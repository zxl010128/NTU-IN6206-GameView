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
import DAO.ProductDBAO;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class Redeem
 */
@WebServlet("/Redeem")
public class Redeem extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Redeem() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		

		Long product_id = Long.parseLong(request.getParameter("product_id")); 

		try { 
			PrintWriter out = response.getWriter();
			UserDBAO userdbao = new UserDBAO();
			Long id = userdbao.identifyId(request.getParameter("token"));
			ProductDBAO productdbao = new ProductDBAO();
			boolean x = productdbao.redeemProduct(product_id,id);
			System.out.println(x);
			if (x == false) { 
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("data", "");
				jsonObject.put("message", "fail");
				jsonObject.put("status_code", 403); 
				out.write(jsonObject.toString()); 
				out.flush(); 
				out.close();
				return;
			} 
			
			//find email by id
			String email = userdbao.findEmailById(id);
			
			// send product key
			boolean y = productdbao.sendProduct(email);
			
			if(y == false) {
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
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("data", "");
				jsonObject.put("status_code", 200); 
				jsonObject.put("message", "success"); 
				out.write(jsonObject.toString()); 
				out.flush();
				out.close();
				return; }
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
