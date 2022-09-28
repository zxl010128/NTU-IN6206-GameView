package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import entity.Comment;
import entity.Notice;

public class Comment_dao {
	    Connection con;
	    private boolean conFree = true;
	    
	    // Database configuration
	    public static String url = "jdbc:mysql://localhost:3306/rent_system2";
	    public static String dbdriver = "com.mysql.jdbc.Driver";
	    public static String username = "root";
	    public static String password = "lffhdhd123456";
	    
	    public Comment_dao() throws Exception {
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
	    public List<Comment> findById(Long id) {
	    	  List<Comment> comments = new ArrayList<Comment>();
	    	  try {
		            String selectStatement = "select * from t_comment where post_id= ?";
		            getConnection();           
		            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
		            prepStmt.setLong(1, id);
		            ResultSet rs = prepStmt.executeQuery();
		            
		            while (rs.next()) {
		                Comment cmt = new Comment(rs.getLong("id"), rs.getString("content"), rs.getLong("user_id"),
		                        rs.getDate("create_time"));
		                comments.add(cmt);
		            }
		           
		            prepStmt.close();
		        } catch (SQLException ex) {
		            releaseConnection();
		            ex.printStackTrace();
		        }
		        
		        releaseConnection();
		       return comments;
	    	
	    }

		public boolean insert_comment(Long user_id, Long p_id, Long p_user_id, String content) {
        	boolean status = false;
	        try {
	String selectStatement = "insert into t_comment(create_time,content,user_id,post_id,post_user_id) values (?,?,?,?,?);";
	            getConnection();
	            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
	            prepStmt.setDate(1, new Date(new java.util.Date().getTime()));
	            prepStmt.setString(2, content);
	            prepStmt.setLong(3, user_id);
	            prepStmt.setLong(4, p_id);
	            prepStmt.setLong(5, p_user_id);
	          
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

		public List<Comment> findall() {
	    	  List<Comment> comments = new ArrayList<Comment>();
	    	  try {
		            String selectStatement = "select * from t_comment";
		            getConnection();           
		            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
		            ResultSet rs = prepStmt.executeQuery();		            
		            while (rs.next()) {
		                Comment cmt = new Comment(rs.getLong("id"), rs.getString("content"), rs.getLong("user_id"),
		                		rs.getLong("post_id"),
		                        rs.getDate("create_time"));
		                comments.add(cmt);
		            }
		           
		            prepStmt.close();
		        } catch (SQLException ex) {
		            releaseConnection();
		            ex.printStackTrace();
		        }
		        
		        releaseConnection();
		       return comments;
	    	
	    }

		public List<Comment> findByUserId(Long user_id) {
			  List<Comment> comments = new ArrayList<Comment>();
	    	  try {
		            String selectStatement = "select * from t_comment where user_id= ?";
		            getConnection();           
		            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
		            prepStmt.setLong(1, user_id);
		            ResultSet rs = prepStmt.executeQuery();
		            
		            while (rs.next()) {
		                Comment cmt = new Comment(rs.getLong("id"), rs.getString("content"), rs.getLong("user_id"),
		                		rs.getLong("post_id"),
		                        rs.getDate("create_time"));
		                comments.add(cmt);
		            }
		           
		            prepStmt.close();
		        } catch (SQLException ex) {
		            releaseConnection();
		            ex.printStackTrace();
		        }
		        
		        releaseConnection();
		       return comments;
		}

		public boolean delete(Long id2) {
			boolean status = false;
	        try {
	String selectStatement = "delete from t_comment where id = ?";
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

