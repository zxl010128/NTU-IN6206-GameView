package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.House;
import entity.Notice;

public class Announcements_dao {
	 Connection con;
	    private boolean conFree = true;
	    
	    // Database configuration
	    public static String url = "jdbc:mysql://localhost:3306/rent_system2";
	    public static String dbdriver = "com.mysql.jdbc.Driver";
	    public static String username = "root";
	    public static String password = "lffhdhd123456";
	    
	    public Announcements_dao() throws Exception {
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
	    public List<Notice> findAll() {
	    	List<Notice> notices = new ArrayList<Notice>();
	        try {
	            String selectStatement = "select * from t_news";
	            getConnection();           
	            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
	            ResultSet rs = prepStmt.executeQuery();
	            
	            while (rs.next()) {
	                Notice nc =	  new Notice(rs.getLong("id"), rs.getString("title"),
	                				rs.getString("summary"), rs.getDate("create_time"));	                
	                notices.add(nc);
	            }
	            
	            prepStmt.close();
	        } catch (SQLException ex) {
	            releaseConnection();
	            ex.printStackTrace();
	        }
	        
	        releaseConnection();
	        
	        return notices;
	    	
	    }
	    public Notice findById(long id) {
	    	  Notice notice = new Notice();
	    	  try {
		            String selectStatement = "select * from t_news where id= ?";
		            getConnection();           
		            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
		            prepStmt.setLong(1, id);
		            ResultSet rs = prepStmt.executeQuery();
		            
		            while (rs.next()) {
		                Notice nc =	  new Notice(rs.getString("title"),rs.getLong("id"), rs.getString("summary"),
		                				rs.getString("content"), rs.getDate("create_time"));	  
		                notice = nc;
		            }
		           
		            prepStmt.close();
		        } catch (SQLException ex) {
		            releaseConnection();
		            ex.printStackTrace();
		        }
		        
		        releaseConnection();
		       return notice;
	    	
	    }

		public boolean add(String title, String summary, String content) {
			boolean status = false;
	        try {
	String selectStatement = "insert into t_news(create_time, title, summary, content) values (?,?,?,?);";
	            getConnection();
	            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
	            prepStmt.setDate(1, new Date(new java.util.Date().getTime()));
	            prepStmt.setString(2, title);
	            prepStmt.setString(3, summary);
	            prepStmt.setString(4, content);	       
	          
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

		public boolean update(Long id2, String title, String summary, String content) {
			boolean status = false;
	        try {
	String selectStatement = "UPDATE t_news SET title=?, summary=?, content=? WHERE id=?";
	            getConnection();
	            PreparedStatement prepStmt = con.prepareStatement(selectStatement);	           
	            prepStmt.setString(1, title);
	            prepStmt.setString(2, summary);
	            prepStmt.setString(3, content);	       
	            prepStmt.setLong(4, id2);
	            
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

		public boolean delete(Long id2) {
			boolean status = false;
	        try {
	String selectStatement = "delete from t_news where id = ?";
	            getConnection();
	            PreparedStatement prepStmt = con.prepareStatement(selectStatement);	           
	            prepStmt.setLong(1, id2);
	            
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
