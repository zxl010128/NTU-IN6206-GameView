package dao;

public class register {


protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	// TODO Auto-generated method stub
	
	response.setContentType("text/html");
            PrintWriter out = response.getWriter();
	String id = request.getParameter("email");
	String password = request.getParameter("psw");
	boolean result = false ;
	
	try {
		AccountDBAO account = new AccountDBAO();
		result = account.create(id, password);
	}
	catch (Exception e)
	{
	 e.printStackTrace();		
	}		
			
	if (result){
		System.out.print("Registration is scccessful");
		out.println("<br> <b>Registration is scccessful</b>");
		//request.getRequestDispatcher("/bookstore").forward(request,response);
		return;
	}
	else { 		
		response.sendRedirect("Register.jsp");
		return;
	}
   }
}
