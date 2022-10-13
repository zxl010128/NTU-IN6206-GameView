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
			UserDBAO userdbao = new UserDBAO();
			Cookie[] cookie = request.getCookies();
			Long id = (long)0;
			for(int i=0;i<cookie.length;i++) {
				if("token".equals(cookie[i].getName())) {
					id = userdbao.identifyId(cookie[i].getValue());
					if(id == 0) {
						JSONObject jsonObject=new JSONObject(); 
						jsonObject.put("message", "Please login ");
						PrintWriter out = response.getWriter(); 
						out.write(jsonObject.toString()); 
						out.flush();    
						return; 
					}
				}
			}
			PrintWriter out = response.getWriter(); 
			ProductDBAO productdbao = new ProductDBAO();
			boolean x = productdbao.redeemProduct(product_id,id);
			System.out.println(x);
			if (x == false) { 
				JSONObject jsonObject=new JSONObject() ; 
				jsonObject.put("message", "Redeem failed! "); 
				out.write(jsonObject.toString()); 
				out.flush(); 
				return; 
			} 
				JSONObject jsonObject=new JSONObject(); 
				jsonObject.put("message", "Redeem Succeeded! "); 
				out.write(jsonObject.toString()); 
				out.flush();    
				return;  
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
