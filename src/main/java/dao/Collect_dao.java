package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Collect_dao {
	  Connection con;
	    private boolean conFree = true;
	    
	    // Database configuration
	    public static String url = "jdbc:mysql://localhost:3306/rent_system2";
	    public static String dbdriver = "com.mysql.jdbc.Driver";
	    public static String username = "root";
	    public static String password = "lffhdhd123456";
	    
	    public Collect_dao() throws Exception {
	        try {
	      
	            Class.forName(dbdriver);
	            con = DriverManager.getConnection(url, username, password);
	            
	        } catch (Exception ex) {
	            System.out.println("Exception in userDBAO: " + ex);
	            throw new Exception("Couldn't open connection to database: " +
	                    ex.getMessage());
	        }
	    }
	    
	    public void remove() {
	        try {
	            con.close();
	        } catch (SQLException ex) {
	            System.out.println(ex.getMessage());
	        }
	    }
	    
	    protected synchronized Connection getConnection() {
	        while (conFree == false) {
	            try {
	                wait();
	            } catch (InterruptedException e) {
	            }
	        }
	        
	        conFree = false;
	        notify();
	        
	        return con;
	    }
	    
	    protected synchronized void releaseConnection() {
	        while (conFree == true) {
	            try {
	                wait();
	            } catch (InterruptedException e) {
	            }
	        }
	        
	        conFree = true;
	        notify();
	    }

		public boolean find_collect(Long user_id, Long house_id) {
			// TODO Auto-generated method stub
			boolean status = false;
	        try {
	            String selectStatement = "select * " + "from t_mark where user_id = ? and house_id = ?";
	            getConnection();
	            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
	            prepStmt.setLong(1, user_id);
	            prepStmt.setLong(2, house_id);
	            ResultSet rs = prepStmt.executeQuery();	          
	            if (rs.next()) {
	            	 status = true;
	            	 prepStmt.close();
	                 releaseConnection();
	                
	            }
	            else {
	            	status = false;
	            	prepStmt.close();
	 	            releaseConnection();	 	            
	            }
	            
	        } catch (SQLException ex) {
	            releaseConnection();
	            ex.printStackTrace();
	        }
	    	return status;
		}

		public boolean insert_collect(Long user_id, Long house_id) {
        	boolean status = false;
	        try {
	String selectStatement = "insert into t_mark(create_time,user_id,house_id) values (?,?,?);";
	            getConnection();
	            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
	            prepStmt.setDate(1, new Date(new java.util.Date().getTime()));
	            prepStmt.setLong(2, user_id);
	            prepStmt.setLong(3, house_id);
	          
	            int x = prepStmt.executeUpdate();
	            
	            if (x == 1) {
	            	status = true;       
	            } 
	            
	            prepStmt.close();
	            releaseConnection();
	            
	        } catch (SQLException ex) {
	            releaseConnection();
	            ex.printStackTrace();
	        }        	
        	return status;
		}
		public List<Long> find_houseId(Long user_id) {
			List<Long> house_Id = new ArrayList<Long>();
	        try {
	String selectStatement = "SELECT * FROM t_mark WHERE user_id = ? ";
	            getConnection();
	            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
	            prepStmt.setLong(1, user_id);
	            ResultSet rs = prepStmt.executeQuery();	          
	            while (rs.next()) {
	                Long hs =
	                		rs.getLong("house_id");
	                house_Id.add(hs);
	            }
	            prepStmt.close();
	            releaseConnection();
	            
	        } catch (SQLException ex) {
	            releaseConnection();
	            ex.printStackTrace();
	        }        	
        	return house_Id;
		}

		public boolean delete(Long u_id, Long id2) {
			boolean status = false;
	        try {
	String selectStatement = "delete from t_mark where user_id = ? and house_id= ?";
	            getConnection();
	            PreparedStatement prepStmt = con.prepareStatement(selectStatement);	           
	            prepStmt.setLong(1, u_id);
	            prepStmt.setLong(2, id2);
	            int x = prepStmt.executeUpdate();
	            
	            if (x == 1) {
	            	status = true;       
	            } 
	            
	            prepStmt.close();
	            releaseConnection();
	            
	        } catch (SQLException ex) {
	            releaseConnection();
	            ex.printStackTrace();
	        }        	
        	return status;
		}
	    
	   
}
