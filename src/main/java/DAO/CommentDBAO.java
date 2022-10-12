package DAO;

import java.sql.*;


import java.util.ArrayList;
import java.util.List;

import entity.Comment;
import entity.Game;

public class CommentDBAO {
	Connection con;
    private boolean conFree = true;
    
    // Database configuration
    public static String url = "jdbc:mysql://localhost:3306/gameview";
    public static String dbdriver = "com.mysql.cj.jdbc.Driver";
    public static String username = "root";
    public static String password = "123456";
    
//    public static void main(String[] args) { //test database connection
//    	try {
//    		UserDBAO user = new UserDBAO();
//    	}catch (Exception e) {
//    		System.out.println("Error!");
//    	}
//    }
    
    public CommentDBAO() throws Exception {
        try {
      
            Class.forName(dbdriver);
            con = DriverManager.getConnection(url, username, password);
            System.out.println("Successfully Connected!");
        } catch (Exception ex) {
            System.out.println("Exception in commentDBAO: " + ex);
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
    
    public List<Comment> getAll(){
    	List<Comment> comments = new ArrayList<Comment>();
    	try {
    		String selectStatement = "select * from comment_table";
    		getConnection();
    		PreparedStatement prepStmt = con.prepareStatement(selectStatement);
    		ResultSet rs = prepStmt.executeQuery();	
    		
    		while(rs.next()) {
    			Comment cmt = new Comment(rs.getLong("post_id"), rs.getInt("totallike"), rs.getInt("totaldislike"), rs.getInt("totallove"), rs.getString("content"), rs.getDate("createtime"), rs.getInt("is_get_coin"), rs.getLong("user_id"), rs.getLong("game_id"));
    			comments.add(cmt);
    		}
    		prepStmt.close();
    	} catch(SQLException ex) {
    		releaseConnection();
            ex.printStackTrace();
    	}
    	releaseConnection();
    	return comments;
    }
    
    public Comment getById(Long id) {
    	Comment comment = new Comment();  
		try {
			 String selectStatement = "select *  where id = ?";
			 getConnection();  	
			 PreparedStatement prepStmt = con.prepareStatement(selectStatement);
		     prepStmt.setLong(1, id);
		     ResultSet rs = prepStmt.executeQuery();
		     while(rs.next()) {
		    	 Comment cmt = new Comment(rs.getLong("post_id"), rs.getInt("totallike"), rs.getInt("totaldislike"), rs.getInt("totallove"), rs.getString("content"), rs.getDate("createtime"));
		    	   comment = cmt;
		     }
		     prepStmt.close();
		     
		} catch (SQLException ex) {
	         releaseConnection();
	         ex.printStackTrace();
	    }		        
	    releaseConnection();
        return comment;
    }
    
    public boolean insertComment(Long userId, Long gameId, String content) {
    	boolean status = false;
    	try {
    		String selectStatement = "insert into comment_table(post_id, game_id, user_id, totallike, totaldislike, totallove, content, createtime, is_get_coin) values (?,?,?,0,0,0,?,?,0);";
    		getConnection();
    		PreparedStatement prepStmt = con.prepareStatement(selectStatement);
    		
    		Long maxID = (long) 0;
			String selectMaxid = "select MAX(post_id) as maxid from comment_table";
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(selectMaxid);
			if (rs.next()) {
				maxID = rs.getLong("maxid");
				// System.out.println(maxID);
			}
			statement.close();

			Long post_id = maxID + 1;
			
			prepStmt.setLong(1, post_id);
    		prepStmt.setLong(2, gameId);
    		prepStmt.setLong(3, userId);
    		prepStmt.setString(4, content);
    		prepStmt.setDate(5, new Date(new java.util.Date().getTime()));
    		
    		int x = prepStmt.executeUpdate();
            if (x == 1) {
            	status = true;       
            } 
            
            prepStmt.close();
            releaseConnection();
            
    	} catch(SQLException ex) {
    		releaseConnection();
    		ex.printStackTrace();
    	}
    	return status;
    }
    
    public boolean deleteComment(Long id) {
    	boolean status = false;
    	try {
    		String selectStatement = "delete from comment_table where id = ?;";
    		getConnection();
            PreparedStatement prepStmt = con.prepareStatement(selectStatement);	           
            prepStmt.setLong(1, id);
            
            int x = prepStmt.executeUpdate();
            if (x == 1) {	
            	status = true;       
            } 
            
            prepStmt.close();
            releaseConnection();
    		
    	} catch(SQLException ex) {
    		releaseConnection();
            ex.printStackTrace();
    	}
    	return status;
    }
}
