package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.Game;
import entity.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
			 String selectStatement = "select game_id, gamename, gamepicture, category, introduction, totalscore, number_of_users_rated " + "from game_table where category = ?"; 
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
			 String selectStatement = "select game_id, gamename, gamepicture, category, introduction, totalscore, number_of_users_rated " + "from game_table where game_id = ?"; 
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
    
    public JSONArray findByName(String name) {
    	JSONArray gamesjson = new JSONArray(); 
		try {
			name=name.replace(" ", "%");
			name="%"+name+"%";
			 String selectStatement = "select game_id, gamepicture,gamename " + "from game_table where gamename LIKE '"+name+"'"; 
			 getConnection();  	
			 PreparedStatement prepStmt = con.prepareStatement(selectStatement);
		     ResultSet rs = prepStmt.executeQuery();
		     while(rs.next()) {
		    	Game game = new Game(rs.getLong("game_id"),rs.getString("gamepicture"),rs.getString("gamename"));
				JSONObject gamejs = new JSONObject();
				gamejs.put("game", JSONObject.fromObject(game));
				gamesjson.add(gamejs);
		     }
		     prepStmt.close();
		     
		} catch (SQLException ex) {
	         releaseConnection();
	         ex.printStackTrace();
	    }		        
	    releaseConnection();
        return gamesjson;
    }
    
    public List<Game> rankByScore(){
    	List<Game> games = new ArrayList<Game>();
    	try {
    		String selectStatement = "select game_id,gamename,totalscore,gamepicture from game_table order by totalscore desc limit 10";
    		getConnection();
    		PreparedStatement prepStmt = con.prepareStatement(selectStatement);
    		ResultSet rs = prepStmt.executeQuery();	
   
    		while(rs.next()) {
    			Game gm = new Game(rs.getLong("game_id"),rs.getString("gamename"), rs.getInt("totalscore"), rs.getString("gamepicture"));
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
    
    public List<String> getCategories(){
    	List<String> categories = new ArrayList<String>();
    	try {
    		String selectStatement = "select DISTINCT category from game_table";
    		getConnection();
    		PreparedStatement prepStmt = con.prepareStatement(selectStatement);
    		ResultSet rs = prepStmt.executeQuery();	
    		while(rs.next()) {
    			String category = rs.getString("category");
    			categories.add(category);
    		}
    		prepStmt.close();
    	}catch(SQLException ex) {
    		releaseConnection();
            ex.printStackTrace();
    	}
    	releaseConnection();
    	return categories;
    }
    
    public List<Long> getCategoryId(String category){
    	List<Long> categoryid = new ArrayList<Long>();
    	try {
    		String selectStatement = "select game_id from game_table where category=?";
    		getConnection();
    		PreparedStatement prepStmt = con.prepareStatement(selectStatement);
    		prepStmt.setString(1, category);
    		ResultSet rs = prepStmt.executeQuery();	
    		while(rs.next()) {
    			Long id = rs.getLong("game_id");
    			categoryid.add(id);
    		}
    		prepStmt.close();
    	}catch(SQLException ex) {
    		releaseConnection();
            ex.printStackTrace();
    	}
    	releaseConnection();
    	return categoryid;
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
    
    public int countGames() {
    	int games = -1;
    	try {
    		String selectStatement = "select COUNT(game_id) as games from game_table";
    		getConnection();
    		PreparedStatement prepStmt = con.prepareStatement(selectStatement);
    		ResultSet rs = prepStmt.executeQuery();	
    		
    		if(rs.next()) {
    			games = rs.getInt("games");
    		}
    		prepStmt.close();
    	}catch(SQLException ex) {
    		releaseConnection();
            ex.printStackTrace();
    	}
    	releaseConnection();
    	return games;
    }
    
    public JSONArray averageScores(List<String> categories) {
		JSONArray scorejson = new JSONArray();
		try {
			getConnection();
			for(int i=0;i<categories.size();i++) {
				String category = categories.get(i);
				String selectStatement = "select AVG(totalscore) as avgscore from game_table where category=?";
	    		PreparedStatement prepStmt = con.prepareStatement(selectStatement);
	    		prepStmt.setString(1, category);
	    		ResultSet rs = prepStmt.executeQuery();	
	    		double avgscore = 0;
	    		if(rs.next()) {
	    			avgscore = rs.getDouble("avgscore");
	    		}
	    		prepStmt.close();
	    		JSONObject score = new JSONObject();
	    		score.put("category", category);
	    		score.put("avgscore", avgscore);
	    		scorejson.add(score);
			}
			
    		
    	}catch(SQLException ex) {
    		releaseConnection();
            ex.printStackTrace();
    	}
    	releaseConnection();
		return scorejson;
	}
    
}
    	
