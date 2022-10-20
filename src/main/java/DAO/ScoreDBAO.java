package DAO;

import java.sql.*;
import java.text.SimpleDateFormat;

import entity.Game;
import entity.Score;
import java.util.ArrayList;
import java.util.List;

public class ScoreDBAO {
	
	Connection con;
    private boolean conFree = true;
    
    // Database configuration
    public static String url = "jdbc:mysql://localhost:3306/gameview";
    public static String dbdriver = "com.mysql.cj.jdbc.Driver";
    public static String username = "root";
    public static String password = "123456";
    
    public ScoreDBAO() throws Exception {
        try {
      
            Class.forName(dbdriver);
            con = DriverManager.getConnection(url, username, password);
            System.out.println("Successfully Connected!");
        } catch (Exception ex) {
            System.out.println("Exception in scoreDBAO: " + ex);
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
    
    public boolean updateScore(Long gameId) {
    	boolean status = false;
    	int avgScore = 0;
    	int totalusers = 0;
    	try {
			 String selectStatement = "select AVG(score) as averageScore,COUNT(user_id) as totalUsers from scoring_table where game_id=?";
			 getConnection();  	
			 PreparedStatement prepStmt = con.prepareStatement(selectStatement);
			 prepStmt.setLong(1, gameId);  
		     ResultSet rs = prepStmt.executeQuery();
		     if (rs.next()) {
		    	 avgScore = rs.getInt("averageScore");
		    	 totalusers = rs.getInt("totalUsers");
		     }
		     prepStmt.close();
		     releaseConnection();
//		     System.out.println(avgScore);
//		     System.out.println(totalusers);
		     
		     String Statement = "update game_table " + "set totalscore=?,number_of_users_rated=? " + "where game_id=? ";
		     getConnection();
		     PreparedStatement prepStmt0 = con.prepareStatement(Statement);
			 prepStmt0.setInt(1, avgScore);
			 prepStmt0.setInt(2, totalusers);
			 prepStmt0.setLong(3, gameId);
			 int x = prepStmt0.executeUpdate();
			 if (x == 1) {
		    	 status = true;
		     }
			 prepStmt0.close();
		     
		} catch (SQLException ex) {
	         releaseConnection();
	         ex.printStackTrace();
	    }		        
	    releaseConnection();
	    return status;
    }
    
    public boolean checkUserId(Long userId, Long gameid) {
    	boolean status = false;
    	try {
    		String selectStatement = "select scoring_id from scoring_table where user_id=? and game_id=?";
    		getConnection();
    		PreparedStatement prepStmt = con.prepareStatement(selectStatement);
			
			prepStmt.setLong(1, userId); 
			prepStmt.setLong(2, gameid); 
			ResultSet rs = prepStmt.executeQuery();
            if (rs.next()) {
            	status = true;       
            } 
            prepStmt.close();
            releaseConnection();
            
    	} catch(SQLException ex) {
    		releaseConnection();
    		ex.printStackTrace();
    	}
    	//System.out.println(status);
    	return status;
    }
    
    public boolean insertReason(Long userId, Long gameId, int score, String reasons) {
    	boolean status = false;
    	try {
    		if(checkUserId(userId,gameId)==false) {
    			String selectStatement = "insert into scoring_table(scoring_id, game_id, user_id, score, reasons_for_scoring, createtime) values (?,?,?,?,?,?);";
        		getConnection();
        		PreparedStatement prepStmt = con.prepareStatement(selectStatement);
        		
        		Long maxID = (long) 0;
    			String selectMaxid = "select MAX(scoring_id) as maxid from scoring_table";
    			Statement statement = con.createStatement();
    			ResultSet rs = statement.executeQuery(selectMaxid);
    			if (rs.next()) {
    				maxID = rs.getLong("maxid");
    				// System.out.println(maxID);
    			}
    			statement.close();

    			Long scoring_id = maxID + 1;
    			
    			prepStmt.setLong(1, scoring_id);
        		prepStmt.setLong(2, gameId);
        		prepStmt.setLong(3, userId);
        		prepStmt.setInt(4, score);
        		prepStmt.setString(5, reasons);
        		prepStmt.setTimestamp(6, new java.sql.Timestamp(new java.util.Date().getTime()));
        		
        		int x = prepStmt.executeUpdate();
                if (x == 1) {
                	status = true;       
                } 
                prepStmt.close();
    		}else {
    			String selectStatement = "update scoring_table " + "set score=?,reasons_for_scoring=?,createtime=? " + "where user_id=? ";
    			getConnection();
    			PreparedStatement prepStmt = con.prepareStatement(selectStatement);
    		
    			prepStmt.setInt(1, score);
    			prepStmt.setString(2, reasons);
    			prepStmt.setTimestamp(3, new java.sql.Timestamp(new java.util.Date().getTime()));
    			prepStmt.setLong(4, userId);
    		
    			int x = prepStmt.executeUpdate();
    			if (x == 1) {
    				status = true;       
    			} 
    			prepStmt.close();
    		}
            releaseConnection();
            
    	} catch(SQLException ex) {
    		releaseConnection();
    		ex.printStackTrace();
    	}
    	return status;
    }
    
    public List<Score> findScoringByGame(Long id){
    	List<Score> scores = new ArrayList<Score>();
    	try {
    		String selectStatement = "select scoring_id,user_id,score,reasons_for_scoring,createtime from scoring_table where game_id=? order by createtime desc";
    		getConnection();
    		PreparedStatement prepStmt = con.prepareStatement(selectStatement);
    		prepStmt.setLong(1, id); 
    		ResultSet rs = prepStmt.executeQuery();	
   
    		while(rs.next()) {
    			String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rs.getTimestamp("createtime"));
    			Score score = new Score(rs.getLong("scoring_id"), rs.getLong("user_id"), rs.getInt("score"),rs.getString("reasons_for_scoring"),timeStamp);
    			scores.add(score);
    		}
    		prepStmt.close();
    	}catch(SQLException ex) {
    		releaseConnection();
            ex.printStackTrace();
    	}
    	releaseConnection();
    	return scores;
    }
}
