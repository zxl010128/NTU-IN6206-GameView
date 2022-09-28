package dao;

import java.sql.*;
import java.sql.Date;

import javax.sql.*;
import javax.naming.*;
import java.util.*;

import entity.Notice;
import entity.User;

public class User_dao {
	    Connection con;
	    private boolean conFree = true;
	    
	    // Database configuration
	    public static String url = "jdbc:mysql://localhost:3306/rent_system2";
	    public static String dbdriver = "com.mysql.jdbc.Driver";
	    public static String username = "root";
	    public static String password = "lffhdhd123456";
	    
	    public User_dao() throws Exception {
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
	    public User findByname(String uname, String password) {
	    	boolean status = false;
	    	User realuser= new User();
	        try {
	            String selectStatement = "select * " + "from t_user where user_name = ? and user_pass = ?";
	            getConnection();
	            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
	            prepStmt.setString(1, uname);
	            prepStmt.setString(2, password); 
	            ResultSet rs = prepStmt.executeQuery();
	          
	            if (rs.next()) {
	            	User user = new User(rs.getLong("id"), rs.getString("user_name"), rs.getString("user_display_name"),
	            			rs.getString("user_pass"), rs.getString("user_avatar"),
	            			rs.getString("email"),rs.getString("phone"), rs.getString("sex"), rs.getString("role"),
	            			rs.getString("job"), rs.getString("country"),rs.getString("hobby"), rs.getInt("status")
	            			);	
	            	 realuser = user;
	            	 prepStmt.close();
	                 releaseConnection();
	                
	            }
	            else {
	            	 prepStmt.close();
	 	            releaseConnection();
	 	            realuser = null;
	 	            
	            }
	            
	        } catch (SQLException ex) {
	            releaseConnection();
	            ex.printStackTrace();
	        }
	        return realuser;
	       
	    }
	    public boolean findUsernameExists(String uname) {
	    	boolean status = false;
	        try {
	            String selectStatement = "select * " + "from t_user where user_name = ?";
	            getConnection();
	            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
	            prepStmt.setString(1, uname);
	            ResultSet rs = prepStmt.executeQuery();
	          
	            if (rs.next()) {
	            	 status = true;
	            	 prepStmt.close();
	                 releaseConnection();
	                
	            }
	            else {
	            	status = false;
	            	prepStmt.close();
	 	            releaseConnection();	 	            
	            }
	            
	        } catch (SQLException ex) {
	            releaseConnection();
	            ex.printStackTrace();
	        }
	    	return status;
	    }
        public boolean register (String uname, String email, String phone,String d_name,String pass,
        		                String role) {
        	boolean status = false;
	        try {
	String selectStatement = "insert into t_user(create_time,user_name,user_display_name,phone,email,user_pass,user_avatar,role) values (?,?,?,?,?,?,?,?);";
	            getConnection();
	            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
	            prepStmt.setDate(1, new Date(new java.util.Date().getTime()));
	            prepStmt.setString(2, uname);
	            prepStmt.setString(3, d_name);
	            prepStmt.setString(4, phone);
	            prepStmt.setString(5, email);
	            prepStmt.setString(6, pass);
	            prepStmt.setString(7, "/assets/img/default-avatar.jpg");
	            prepStmt.setString(8, role);
	          
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
        public User getById(Long id) {
        	 User user = new User();
	    	  try {
		            String selectStatement = "select * from t_user where id= ?";
		            getConnection();           
		            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
		            prepStmt.setLong(1, id);
		            ResultSet rs = prepStmt.executeQuery();
		            
		            while (rs.next()) {
		            	User us = new User(rs.getLong("id"), rs.getString("user_name"), rs.getString("user_display_name"),
		            			rs.getString("user_pass"), rs.getString("user_avatar"),
		            			rs.getString("email"),rs.getString("phone"), rs.getString("sex"), rs.getString("role"),
		            			rs.getString("job"), rs.getString("country"),rs.getString("hobby"), rs.getInt("status")
		            			);	  
		                user = us;
		            }
		           
		            prepStmt.close();
		        } catch (SQLException ex) {
		            releaseConnection();
		            ex.printStackTrace();
		        }
		        
		        releaseConnection();
		       return user;
        }
        public boolean change (String email, String phone, String d_name,String sex, 
        		String country,String hobby, String job, Long id) {
             boolean status = false;
             try {
        		String sql = "update t_user" + " " + "set create_time=?,phone=?,email=?,sex=?,hobby=?,job=?,country=?" + " where id=? ";
        		getConnection();
        		PreparedStatement prepStmt = con.prepareStatement(sql);
        		prepStmt.setDate(1, new Date(new java.util.Date().getTime()));
        		prepStmt.setString(2, phone);
        		prepStmt.setString(3, email);
        		prepStmt.setString(4, sex);
        		prepStmt.setString(5, hobby);
        		prepStmt.setString(6, job);
        		prepStmt.setString(7, country);
        		prepStmt.setLong(8, id);

        		int x = prepStmt.executeUpdate();

                if (x == 1) {
                	status = true;       
                } 
                prepStmt.close();
                releaseConnection();
                }catch (SQLException ex) {
                	releaseConnection();
                	ex.printStackTrace();
                }        	
        	return status;
        }
        public boolean reset (String password, Long id) {
             boolean status = false;
             try {
        		String sql = "update t_user" + " " + "set user_pass=?" + " where id=? ";
        		getConnection();
        		PreparedStatement prepStmt = con.prepareStatement(sql);
        		prepStmt.setString(1, password);
        		prepStmt.setLong(2, id);

        		int x = prepStmt.executeUpdate();

                if (x == 1) {
                	status = true;       
                } 
                prepStmt.close();
                releaseConnection();
                }catch (SQLException ex) {
                	releaseConnection();
                	ex.printStackTrace();
                }        	
        	return status;
        }
        public List<User> findAll() {
          	 List<User> user = new ArrayList<User>();
   	    	  try {
   		            String selectStatement = "select * from t_user where role!='admin' ";
   		            getConnection();           
   		            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
   		            ResultSet rs = prepStmt.executeQuery();		            
   		            while (rs.next()) {
   		            	User us = new User(rs.getInt("status"),rs.getLong("id"),rs.getString("user_name"), rs.getString("user_display_name"),
   		            			rs.getString("user_pass"),rs.getString("email"),rs.getString("phone"),rs.getString("hobby"),
   		            			rs.getString("job")
   		            			);	  
   		                user.add(us);
   		            }	           
   		            prepStmt.close();
   		        } catch (SQLException ex) {
   		            releaseConnection();
   		            ex.printStackTrace();
   		        }		        
   		        releaseConnection();
   		       return user;
          }

		public boolean disable(Long id2) {
			boolean status = false;
	        try {
	String selectStatement = "UPDATE t_user SET status=1 WHERE id=?";
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
		public boolean enable(Long id2) {
			boolean status = false;
	        try {
	String selectStatement = "UPDATE t_user SET status=0 WHERE id=?";
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
