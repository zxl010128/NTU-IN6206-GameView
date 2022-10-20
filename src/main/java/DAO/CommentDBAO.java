package DAO;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import DAO.UserDBAO;
import entity.Comment;
import entity.Game;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
    			String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rs.getTimestamp("createtime"));
    			Comment cmt = new Comment(rs.getLong("post_id"), rs.getInt("totallike"), rs.getInt("totaldislike"), rs.getInt("totallove"), rs.getString("content"), timeStamp, rs.getInt("is_get_coin"), rs.getLong("user_id"), rs.getLong("game_id"));
//    			Comment cmt = new Comment(rs.getLong("post_id"), rs.getInt("totallike"), rs.getInt("totaldislike"), rs.getInt("totallove"), rs.getString("content"), rs.getDate("createtime"), rs.getInt("is_get_coin"), rs.getLong("user_id"), rs.getLong("game_id"));
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
       String selectStatement = "select * from comment_table where post_id = ?";
       getConnection();   
       PreparedStatement prepStmt = con.prepareStatement(selectStatement);
          prepStmt.setLong(1, id);
          ResultSet rs = prepStmt.executeQuery();
          while(rs.next()) {
           String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rs.getTimestamp("createtime"));
//           Comment cmt = new Comment(rs.getLong("post_id"), rs.getInt("totallike"), rs.getInt("totaldislike"), rs.getInt("totallove"), rs.getString("content"), rs.getDate("createtime"));
           Comment cmt = new Comment(rs.getLong("post_id"), rs.getInt("totallike"), rs.getInt("totaldislike"), rs.getInt("totallove"), rs.getString("content"), timeStamp);
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
//    		prepStmt.setDate(5, new Date(new java.util.Date().getTime()));
    		prepStmt.setTimestamp(5, new java.sql.Timestamp(new java.util.Date().getTime()));
    		
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
    
    public boolean addLike(Long commentId) {
    	boolean status = false;
    	try {
    		String select = "select totallike,is_get_coin from comment_table where post_id = ?";
			getConnection();
			PreparedStatement prepStmt = con.prepareStatement(select);
			prepStmt.setLong(1, commentId);
			ResultSet rs = prepStmt.executeQuery();
			int totalLike = 0;
			int isgetcoin = 0;
			if (rs.next()) {
				totalLike = rs.getInt("totallike") + 1;
				isgetcoin = rs.getInt("is_get_coin");
			}
			prepStmt.close();
			if(totalLike == 1000 & isgetcoin== 0) {
				String selectStatement = "update comment_table " + "set totallike=?,is_get_coin=? " + "where post_id=? ";
				PreparedStatement prepStmt0 = con.prepareStatement(selectStatement);
				prepStmt0.setInt(1, totalLike);
				prepStmt0.setInt(2, 1);
				prepStmt0.setLong(3, commentId);
           
				int x = prepStmt0.executeUpdate();
				if (x == 1) {	
            	status = true;       
				} 
				prepStmt0.close();
			}
			else {
				String selectStatement = "update comment_table " + "set totallike=? " + "where post_id=? ";
				PreparedStatement prepStmt0 = con.prepareStatement(selectStatement);
				prepStmt0.setInt(1, totalLike);
				prepStmt0.setLong(2, commentId);
           
				int x = prepStmt0.executeUpdate();
				if (x == 1) {	
            	status = true;       
				} 
				prepStmt0.close(); 
            }
			
            releaseConnection();
    		
    	} catch(SQLException ex) {
    		releaseConnection();
            ex.printStackTrace();
    	}
    	return status;
    }
    
    public boolean addDislike(Long commentId) {
    	boolean status = false;
    	try {
    		String select = "select totaldislike from comment_table where post_id = ?";
			getConnection();
			PreparedStatement prepStmt = con.prepareStatement(select);
			prepStmt.setLong(1, commentId);
			ResultSet rs = prepStmt.executeQuery();
			int totalDislike = 0;
			if (rs.next()) {
				totalDislike = rs.getInt("totaldislike") + 1;
			}
			prepStmt.close();
			
    		String selectStatement = "update comment_table " + "set totaldislike=? " + "where post_id=? ";
            PreparedStatement prepStmt0 = con.prepareStatement(selectStatement);
            prepStmt0.setInt(1, totalDislike);
            prepStmt0.setLong(2, commentId);
            int x = prepStmt0.executeUpdate();
            if (x == 1) {	
            	status = true;       
            } 
            prepStmt0.close();
            releaseConnection();
    		
    	} catch(SQLException ex) {
    		releaseConnection();
            ex.printStackTrace();
    	}
    	return status;
    }
    
    public boolean addLove(Long commentId) {
    	boolean status = false;
    	try {
    		String select = "select totallove from comment_table where post_id = ?";
			getConnection();
			PreparedStatement prepStmt = con.prepareStatement(select);
			prepStmt.setLong(1, commentId);
			ResultSet rs = prepStmt.executeQuery();
			int totalLove = 0;
			if (rs.next()) {
				totalLove = rs.getInt("totallove") + 1;
			}
			prepStmt.close();
			
    		String selectStatement = "update comment_table " + "set totallove=? " + "where post_id=? ";
            PreparedStatement prepStmt0 = con.prepareStatement(selectStatement);
            prepStmt0.setInt(1, totalLove);
            prepStmt0.setLong(2, commentId);
            int x = prepStmt0.executeUpdate();
            if (x == 1) {	
            	status = true;       
            } 
            prepStmt0.close();
            releaseConnection();
    		
    	} catch(SQLException ex) {
    		releaseConnection();
            ex.printStackTrace();
    	}
    	return status;
    }
    
    public boolean deleteLove(Long commentId) {
    	boolean status = false;
    	try {
    		String select = "select totallove from comment_table where post_id = ?";
			getConnection();
			PreparedStatement prepStmt = con.prepareStatement(select);
			prepStmt.setLong(1, commentId);
			ResultSet rs = prepStmt.executeQuery();
			int totalLove = 0;
			if (rs.next()) {
				totalLove = rs.getInt("totallove") - 1;
			}
			prepStmt.close();
			
    		String selectStatement = "update comment_table " + "set totallove=? " + "where post_id=? ";
            PreparedStatement prepStmt0 = con.prepareStatement(selectStatement);
            prepStmt0.setInt(1, totalLove);
            prepStmt0.setLong(2, commentId);
            int x = prepStmt0.executeUpdate();
            if (x == 1) {	
            	status = true;       
            } 
            prepStmt0.close();
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
    
    public List<Game> rankByPosts(){
    	List<Game> games = new ArrayList<Game>();
    	try {
    		String selectStatement = "select game_table.gamename,game_table.gamepicture,(select COUNT(post_id) from comment_table where comment_table.game_id=game_table.game_id) posts from game_table order by posts desc limit 10";
    		getConnection();
    		PreparedStatement prepStmt = con.prepareStatement(selectStatement);
    		ResultSet rs = prepStmt.executeQuery();	
   
    		while(rs.next()) {
    			Game gm = new Game(rs.getString("gamename"),rs.getString("gamepicture"),rs.getInt("posts"));
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
    
    public List<Comment> rankByLikes(){
    	List<Comment> comments = new ArrayList<Comment>();
    	try {
    		String selectStatement = "select content,totallike,createtime from comment_table order by totallike desc limit 10";
    		getConnection();
    		PreparedStatement prepStmt = con.prepareStatement(selectStatement);
    		ResultSet rs = prepStmt.executeQuery();	
   
    		while(rs.next()) {
    			String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rs.getTimestamp("createtime"));
    			Comment comment = new Comment(rs.getString("content"), rs.getInt("totallike"), timeStamp);
    			comments.add(comment);
    		}
    		prepStmt.close();
    	}catch(SQLException ex) {
    		releaseConnection();
            ex.printStackTrace();
    	}
    	releaseConnection();
    	return comments;
    }
    
    public int isGetCoin(Long post_id) {
    	int isgetcoin=0;
    	try {
    		String select = "select is_get_coin from comment_table where post_id = ?";
			getConnection();
			PreparedStatement prepStmt = con.prepareStatement(select);
			prepStmt.setLong(1, post_id);
			ResultSet rs = prepStmt.executeQuery();
//			int totalLove = 0;
			if (rs.next()) {
				isgetcoin=rs.getInt("is_get_coin");
			}
			prepStmt.close();
            releaseConnection();
    		
    	} catch(SQLException ex) {
    		releaseConnection();
            ex.printStackTrace();
    	}
    	return isgetcoin;
    }
    
    public List<Comment> findCommentsByUser(Long id){
    	List<Comment> comments = new ArrayList<Comment>();
    	try {
			 String selectStatement = "select post_id,content,createtime " + "from comment_table where user_id = ?"; 
			 getConnection();  	
			 PreparedStatement prepStmt = con.prepareStatement(selectStatement);
		     prepStmt.setLong(1, id);
		     ResultSet rs = prepStmt.executeQuery();
		     while(rs.next()) {
		    	 String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rs.getTimestamp("createtime"));
		    	 Comment comment = new Comment(rs.getLong("post_id"),rs.getString("content"),timeStamp);
		    	 comments.add(comment);
		     }
		     prepStmt.close();
		     
		} catch (SQLException ex) {
	         releaseConnection();
	         ex.printStackTrace();
	    }		        
	    releaseConnection();
    	return comments;
    }
    
    public int totalComments(List<Long> ids) {
		int totalcomments = 0;
		try {
			getConnection();
			for(int i=0;i<ids.size();i++) {
				Long id = ids.get(i);
				String selectStatement = "select COUNT(post_id) as commentnum from comment_table where game_id=?";
	    		PreparedStatement prepStmt = con.prepareStatement(selectStatement);
	    		prepStmt.setLong(1, id);
	    		ResultSet rs = prepStmt.executeQuery();	
	    		
	    		if(rs.next()) {
	    			totalcomments +=rs.getInt("commentnum");
	    		}
	    		prepStmt.close();
			}
    	}catch(SQLException ex) {
    		releaseConnection();
            ex.printStackTrace();
    	}
    	releaseConnection();
		return totalcomments;
	}
    
    public List<Comment> findCommentByGame(Long id){
        List<Comment> comments = new ArrayList<Comment>();
        try {
         String selectStatement = "select post_id,content,createtime,user_id from comment_table where game_id=? order by createtime desc";
         getConnection();
         PreparedStatement prepStmt = con.prepareStatement(selectStatement);
         prepStmt.setLong(1, id); 
         ResultSet rs = prepStmt.executeQuery();
         
         while(rs.next()) {
          String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rs.getTimestamp("createtime"));
          Comment comment = new Comment(rs.getLong("post_id"),rs.getString("content"),timeStamp,rs.getLong("user_id"));
          comments.add(comment);
         }
         prepStmt.close();
         
        }catch (SQLException ex) {
             releaseConnection();
             ex.printStackTrace();
        }
        releaseConnection();
        return comments;
       }
    
}
