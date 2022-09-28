package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Notice;
import entity.Post;

public class Post_dao {
	    Connection con;
	    private boolean conFree = true;
	    
	    // Database configuration
	    public static String url = "jdbc:mysql://localhost:3306/rent_system2";
	    public static String dbdriver = "com.mysql.jdbc.Driver";
	    public static String username = "root";
	    public static String password = "lffhdhd123456";
	    
	    public Post_dao() throws Exception {
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
	    public List<Post> findall() {
	    	List<Post> posts = new ArrayList<Post>();
	        try {
	            String selectStatement = "select * from t_post";
	            getConnection();           
	            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
	            ResultSet rs = prepStmt.executeQuery();
	            
	            while (rs.next()) {
	                Post pt =	  new Post(rs.getLong("id"), rs.getString("title"),
	                				rs.getString("summary"), rs.getDate("create_time"));	                
	                posts.add(pt);
	            }
	            
	            prepStmt.close();
	        } catch (SQLException ex) {
	            releaseConnection();
	            ex.printStackTrace();
	        }
	        
	        releaseConnection();
	        
	        return posts;
	    	
	    }
	    public Post findById(Long id) {
	    	 Post post = new Post();
	    	  try {
		            String selectStatement = "select * from t_post where id= ?";
		            getConnection();           
		            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
		            prepStmt.setLong(1, id);
		            ResultSet rs = prepStmt.executeQuery();
		            
		            while (rs.next()) {
		                Post pt = new Post(rs.getString("title"),rs.getLong("id"), 
		                				rs.getString("content"), rs.getDate("create_time"),rs.getLong("user_id"));	  
		                post = pt;
		            }
		           
		            prepStmt.close();
		        } catch (SQLException ex) {
		            releaseConnection();
		            ex.printStackTrace();
		        }
		        
		        releaseConnection();
		       return post;
	    	
	    }
	    public List<Post> findall1() {
	    	List<Post> posts = new ArrayList<Post>();
	        try {
	            String selectStatement = "select * from t_post";
	            getConnection();           
	            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
	            ResultSet rs = prepStmt.executeQuery();
	            
	            while (rs.next()) {
	                Post pt =	  new Post(rs.getString("title"),rs.getLong("id"), 
            				rs.getString("content"), rs.getDate("create_time"),rs.getLong("user_id"));	                
	                posts.add(pt);
	            }
	            
	            prepStmt.close();
	        } catch (SQLException ex) {
	            releaseConnection();
	            ex.printStackTrace();
	        }
	        
	        releaseConnection();
	        
	        return posts;
	    	
	    }
	    public List<Post> findAllById(Long id) {
	    	List<Post> posts = new ArrayList<Post>();
	        try {
	            String selectStatement = "select * from t_post where user_id = ?";
	            getConnection();           
	            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
	            prepStmt.setLong(1, id);
	            ResultSet rs = prepStmt.executeQuery();
	            while (rs.next()) {
	                Post pt =	  new Post(rs.getString("title"),rs.getLong("id"), 
            				rs.getString("content"), rs.getDate("create_time"),rs.getLong("user_id"));	                
	                posts.add(pt);
	            }
	            
	            prepStmt.close();
	        } catch (SQLException ex) {
	            releaseConnection();
	            ex.printStackTrace();
	        }
	        
	        releaseConnection();
	        
	        return posts;
	    	
	    }
	    public boolean insert_post(Long user_id, String title, String content) {
        	boolean status = false;
	        try {
	String selectStatement = "insert into t_post(create_time,content,user_id,title,summary,post_type) values (?,?,?,?,?,?);";
	            getConnection();
	            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
	            prepStmt.setDate(1, new Date(new java.util.Date().getTime()));
	            prepStmt.setString(2, content);
	            prepStmt.setLong(3, user_id);
	            prepStmt.setString(4, title);
	            prepStmt.setString(5, title);
	            prepStmt.setString(6, "qiu_zu");
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
	String selectStatement = "delete from t_post where id = ?";
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

