package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.Comment;
import entity.Game;

public class GameDBAO {
	Connection con;
    private boolean conFree = true;
    
    // Database configuration
    public static String url = "jdbc:mysql://localhost:3306/gameview";
    public static String dbdriver = "com.mysql.cj.jdbc.Driver";
    public static String username = "root";
    public static String password = "123456";
    
    
    public GameDBAO() throws Exception {
        try {
      
            Class.forName(dbdriver);
            con = DriverManager.getConnection(url, username, password);
            System.out.println("Successfully Connected!");
        } catch (Exception ex) {
            System.out.println("Exception in gameDBAO: " + ex);
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
    
    public List<Game> findByCategory(String category) {
    	List<Game> games = new ArrayList<Game>();  
		try {
			 String selectStatement = "select game_id, gamename, gamepicture, category, introduction, totalscore, number_of_user_rated " + "from game_table where category = ?"; 
			 getConnection();  	
			 PreparedStatement prepStmt = con.prepareStatement(selectStatement);
		     prepStmt.setString(1, category);
		     ResultSet rs = prepStmt.executeQuery();
		     while(rs.next()) {
		    	 Game gm = new Game(rs.getLong("game_id"),rs.getString("gamename"),rs.getString("gamepicture"),rs.getString("category"),rs.getString("introduction"),rs.getInt("totalscore"),rs.getInt("number_of_users_rated"));
		    	   games.add(gm);
		     }
		     prepStmt.close();
		     
		} catch (SQLException ex) {
	         releaseConnection();
	         ex.printStackTrace();
	    }		        
	    releaseConnection();
        return games;
    }
    
    public Game findById(Long id) {
    	Game game = new Game();  
		try {
			 String selectStatement = "select game_id, gamename, gamepicture, category, introduction, totalscore, number_of_user_rated " + "from game_table where game_id = ?"; 
			 getConnection();  	
			 PreparedStatement prepStmt = con.prepareStatement(selectStatement);
		     prepStmt.setLong(1, id);
		     ResultSet rs = prepStmt.executeQuery();
		     while(rs.next()) {
		    	 Game gm = new Game(rs.getLong("game_id"),rs.getString("gamename"),rs.getString("gamepicture"),rs.getString("category"),rs.getString("introduction"),rs.getInt("totalscore"),rs.getInt("number_of_users_rated"));
		    	   game = gm;
		     }
		     prepStmt.close();
		     
		} catch (SQLException ex) {
	         releaseConnection();
	         ex.printStackTrace();
	    }		        
	    releaseConnection();
        return game;
    }
    
    public List<Game> rankByScore(String category){
    	List<Game> games = new ArrayList<Game>();
    	try {
    		String selectStatement = "select gamename,totalscore from game_table where category=? order by totalscore limit 10";
    		getConnection();
    		PreparedStatement prepStmt = con.prepareStatement(selectStatement);
    		ResultSet rs = prepStmt.executeQuery();	
   
    		while(rs.next()) {
    			Game gm = new Game(rs.getString("gamename"), rs.getInt("totalscore"));
    			games.add(gm);
    		}
    		prepStmt.close();
    	}catch(SQLException ex) {
    		releaseConnection();
            ex.printStackTrace();
    	}
    	releaseConnection();
    	return games;
    }
    
    public List<Game> getAll() {
    	List<Game> games = new ArrayList<Game>();
    	try {
    		String selectStatement = "select * from game_table";
    		getConnection();
    		PreparedStatement prepStmt = con.prepareStatement(selectStatement);
    		ResultSet rs = prepStmt.executeQuery();	
    		
    		while(rs.next()) {
    			Game gm = new Game(rs.getLong("game_id"), rs.getString("gamename"), rs.getString("gamepicture"), rs.getString("category"), rs.getInt("totalscore"));
    			games.add(gm);
    		}
    		prepStmt.close();
    	}catch(SQLException ex) {
    		releaseConnection();
            ex.printStackTrace();
    	}
    	releaseConnection();
    	return games;
    }
}
    	
