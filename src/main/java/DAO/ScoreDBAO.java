package DAO;

import java.sql.*;

import entity.Game;

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
    
    public int getScore() {
    	int avgScore = 0;
    	try {
			 String selectStatement = "select AVG(score) as averageScore from score_table";
			 getConnection();  	
			 PreparedStatement prepStmt = con.prepareStatement(selectStatement);
		     ResultSet rs = prepStmt.executeQuery();
		     while(rs.next()) {
		    	 avgScore = rs.getInt("averageScore");
		     }
		     prepStmt.close();
		     
		} catch (SQLException ex) {
	         releaseConnection();
	         ex.printStackTrace();
	    }		        
	    releaseConnection();
	    return avgScore;
    }
    
    public boolean insertReason(Long userId, Long gameId, int score, String reasons) {
    	boolean status = false;
    	try {
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
    		prepStmt.setDate(6, new Date(new java.util.Date().getTime()));
    		
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
