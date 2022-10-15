package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import entity.Comment;
import entity.Reply;
public class ReplyDBAO {
	
	Connection con;
    private boolean conFree = true;
    
    // Database configuration
    public static String url = "jdbc:mysql://localhost:3306/gameview";
    public static String dbdriver = "com.mysql.cj.jdbc.Driver";
    public static String username = "root";
    public static String password = "123456";
    
    public ReplyDBAO() throws Exception {
        try {
      
            Class.forName(dbdriver);
            con = DriverManager.getConnection(url, username, password);
            System.out.println("Successfully Connected!");
        } catch (Exception ex) {
            System.out.println("Exception in replyDBAO: " + ex);
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
    
    public List<Reply> getAll(){
    	List<Reply> replys = new ArrayList<Reply>();
    	try {
    		String selectStatement = "select * from reply_table";
    		getConnection();
    		PreparedStatement prepStmt = con.prepareStatement(selectStatement);
    		ResultSet rs = prepStmt.executeQuery();	
    		
    		while(rs.next()) {
    			Reply rpl = new Reply(rs.getLong("reply_id"), rs.getDate("createtime"), rs.getString("content"), rs.getLong("user_id"), rs.getLong("post_id"));
    			replys.add(rpl);
    		}
    		prepStmt.close();
    	} catch(SQLException ex) {
    		releaseConnection();
            ex.printStackTrace();
    	}
    	releaseConnection();
    	return replys;
    }
    
    public boolean insertReply(Long commentId, Long userId, String content) {
    	boolean status = false;
    	try {
    		String selectStatement = "insert into reply_table(reply_id,post_id,user_id,createtime,content) values (?,?,?,?,?);";
    		getConnection();
    		PreparedStatement prepStmt = con.prepareStatement(selectStatement);
    		
    		Long maxID = (long) 0;
			String selectMaxid = "select MAX(reply_id) as maxid from reply_table";
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(selectMaxid);
			if (rs.next()) {
				maxID = rs.getLong("maxid");
				// System.out.println(maxID);
			}
			statement.close();

			Long reply_id = maxID + 1;
			
			prepStmt.setLong(1, reply_id);
    		prepStmt.setLong(2, commentId);
    		prepStmt.setLong(3, userId);
//    		prepStmt.setDate(4, new Date(new java.util.Date().getTime()));
    		prepStmt.setTimestamp(4, new java.sql.Timestamp(new java.util.Date().getTime()));
    		prepStmt.setString(5, content);
    		
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
