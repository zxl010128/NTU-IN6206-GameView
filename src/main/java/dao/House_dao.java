package dao;

import java.util.ArrayList;
import java.util.List;

import entity.Comment;
import entity.House;
import entity.Notice;

import java.sql.*;
import java.sql.Date;
import javax.sql.*;

import javax.naming.*;
import java.util.*;

public class House_dao {
	    Connection con;
	    private boolean conFree = true;
	    
	    // Database configuration
	    public static String url = "jdbc:mysql://localhost:3306/rent_system2";
	    public static String dbdriver = "com.mysql.jdbc.Driver";
	    public static String username = "root";
	    public static String password = "lffhdhd123456";
	    
	    public House_dao() throws Exception {
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
	   public List<House> findrecent_houses(String type, Integer limit) {
		   List<House> houses = new ArrayList<House>();
	        
	        try {
	            String selectStatement = "select * from t_house where status = 1 and rent_type = ? order by create_time desc limit ?";
	            getConnection();           
	            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
	            prepStmt.setString(1, type);
	            prepStmt.setInt(2, limit); 
	            ResultSet rs = prepStmt.executeQuery();
	            
	            while (rs.next()) {
	                House hs =
	                		new House(rs.getLong("id"), rs.getString("thumbnail_url"),
	                        rs.getString("title"), rs.getString("address"), rs.getInt("month_rent"),
	                        rs.getInt("bedroom_num"),rs.getInt("toilet_num"));
	                houses.add(hs);
	            }
	            
	            prepStmt.close();
	        } catch (SQLException ex) {
	            releaseConnection();
	            ex.printStackTrace();
	        }
	        
	        releaseConnection();
	        
	        return houses;
		   
	   }
	   public List<House> findByParameter(String address, String region, String rentType,
			                            String priceRange) {
		   List<House> houses = new ArrayList<House>();
		   Integer minPrice = 0;
	       Integer maxPrice = 300;
	       if ( priceRange!=null && priceRange.length() !=0) {
	           String[] arr = priceRange.split(";");
	           if (arr.length == 2) {
	                    minPrice = Integer.valueOf(arr[0]);
	                    maxPrice = Integer.valueOf(arr[1]);
	                }
	            }
	       String ss = "%" + address + "%";
		   String sql = "select * from t_house where 1=1 and status = 1";
		   String add = " and address like ?";
	       String reg = " and region = ?";
	       String rentTy = " and rent_type = ?";
	       String min = " and month_rent > ?";
	       String max = " and month_rent < ?";
	       StringBuffer stringBuffer = new StringBuffer(sql);
	       ArrayList params = new ArrayList<>();
	       if (address!=null && address.length()!=0) {	    	   
	    	   params.add(ss);
	    	   stringBuffer.append(add);
	       }
	       if (region!=null && region.length()!=0) {
	    	   params.add(region);
	    	   stringBuffer.append(reg);
	       }
	       if (rentType!=null && rentType.length()!=0) {
	    	   params.add(rentType);
	    	   stringBuffer.append(rentTy);
	       }
	       if (priceRange!=null && priceRange.length() !=0) {
	    	   params.add(minPrice);
	    	   params.add(maxPrice);
	    	   stringBuffer.append(min);
	    	   stringBuffer.append(max);
	       }
	       getConnection();           
           try {
			PreparedStatement prepStmt = con.prepareStatement(stringBuffer.toString());
			if (!params.isEmpty()) {
                for (int i = 0; i < params.size(); i++) {
                	prepStmt.setObject(i + 1, params.get(i));
                }
            }
			ResultSet rs = prepStmt.executeQuery();
            
            while (rs.next()) {
                House hs =
                		new House(rs.getLong("id"), rs.getString("thumbnail_url"),
                        rs.getString("title"), rs.getString("address"), rs.getInt("month_rent"),
                        rs.getInt("bedroom_num"),rs.getInt("toilet_num"));
                houses.add(hs);
            }
            prepStmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
           
           releaseConnection();
	       return houses;
		   
	   }

	public House findById(Long id2) {
		  House house = new House();
    	  try {
	            String selectStatement = "select * from t_house where id= ?";
	            getConnection();           
	            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
	            prepStmt.setLong(1, id2);
	            ResultSet rs = prepStmt.executeQuery();
	            
	            while (rs.next()) {
	                House hs =	  new House(rs.getLong("id"), rs.getLong("user_id"), rs.getString("thumbnail_url"),
	                        rs.getString("title"), rs.getString("address"), rs.getInt("month_rent"),
	                        rs.getInt("bedroom_num"),rs.getInt("toilet_num"),rs.getInt("kitchen_num"),
	                        rs.getInt("living_room_num"),rs.getDouble("area"), rs.getInt("has_air_conditioner"),
	                        rs.getInt("build_year"),rs.getInt("status"),rs.getString("certificate_no"),
	                        rs.getString("direction"), rs.getInt("floor"), rs.getInt("max_floor"),
	                        rs.getInt("has_elevator"),rs.getString("contact_name"),
	                        rs.getString("contact_phone"),rs.getString("content"),rs.getString("rent_type"),
	                        rs.getString("slide_url"));	  
	                house = hs;
	            }
	           
	            prepStmt.close();
	        } catch (SQLException ex) {
	            releaseConnection();
	            ex.printStackTrace();
	        }
	        
	        releaseConnection();
	       return house;
	}

	public List<House> findBycerandid(Long userId, String certificateNo, String type) {
		List<House> houses = new ArrayList<House>();
		 try {
	            String selectStatement = "select * from t_house where user_id= ? and certificate_no = ? and rent_type = ? ";
	            getConnection();           
	            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
	            prepStmt.setLong(1, userId);
	            prepStmt.setString(2, certificateNo);
	            prepStmt.setString(3, type);
	            ResultSet rs = prepStmt.executeQuery();
	            
	            while (rs.next()) {
	                House hs = new House(rs.getLong("id"), rs.getString("thumbnail_url"),
	                        rs.getString("title"), rs.getInt("month_rent"));
	                houses.add(hs);
	            }
	           
	            prepStmt.close();
	        } catch (SQLException ex) {
	            releaseConnection();
	            ex.printStackTrace();
	        }
	        
	        releaseConnection();
	       return houses;
	}
	public House findByid2(Long id2) {
		  House house = new House();
    	  try {
	            String selectStatement = "select * from t_house where id= ?";
	            getConnection();           
	            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
	            prepStmt.setLong(1, id2);
	            ResultSet rs = prepStmt.executeQuery();
	            
	            while (rs.next()) {
	                House hs =	  new House(rs.getLong("id"), rs.getString("longitude_latitude"), rs.getString("address"));	  
	                house = hs;
	            }
	           
	            prepStmt.close();
	        } catch (SQLException ex) {
	            releaseConnection();
	            ex.printStackTrace();
	        }
	        
	        releaseConnection();
	       return house;
		
	}

	public List<House> findall() {
		   List<House> houses = new ArrayList<House>();
	        
	        try {
	            String selectStatement = "select * from t_house";
	            getConnection();           
	            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
	            ResultSet rs = prepStmt.executeQuery();
	            
	            while (rs.next()) {
	                House hs =
	                		new House(rs.getLong("id"), rs.getString("thumbnail_url"),
	                        rs.getString("title"), rs.getString("address"), rs.getInt("status"));
	                houses.add(hs);
	            }
	            
	            prepStmt.close();
	        } catch (SQLException ex) {
	            releaseConnection();
	            ex.printStackTrace();
	        }
	        
	        releaseConnection();
	        
	        return houses;
	}

	public List<House> findByUserId(Long user_id) {
		 List<House> houses = new ArrayList<House>();
	        
	        try {
	            String selectStatement = "select * from t_house where user_id= ? ";
	            getConnection();           
	            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
	            prepStmt.setLong(1, user_id);
	            ResultSet rs = prepStmt.executeQuery();
	            
	            while (rs.next()) {
	                House hs =
	                		new House(rs.getLong("id"), rs.getString("thumbnail_url"),
	                        rs.getString("title"), rs.getString("address"), rs.getInt("status"));
	                houses.add(hs);
	            }
	            
	            prepStmt.close();
	        } catch (SQLException ex) {
	            releaseConnection();
	            ex.printStackTrace();
	        }
	        
	        releaseConnection();
	        
	        return houses;
	}
	  public List<House> findByHouseId(List<Long> house_Id) {
			List<House> houses = new ArrayList<House>();
			int n = house_Id.size();	
			for(int a = 0 ;a < n; a++) {
				try {
					Long search_id = house_Id.get(a);
					String selectStatement = "select * from t_house where id = ? ";
					            getConnection();
					            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
					            prepStmt.setLong(1, search_id);
					            ResultSet rs = prepStmt.executeQuery();	  
					            if(rs.next()) {
					            	House hs =
					                		new House(rs.getLong("id"), rs.getString("thumbnail_url"),
					                        rs.getString("title"), rs.getString("address"), rs.getInt("month_rent"),
					                        rs.getInt("bedroom_num"),rs.getInt("toilet_num"));
					                houses.add(hs);
					            }		                
					            prepStmt.close();         
					        } catch (SQLException ex) {
					            releaseConnection();
					            ex.printStackTrace();
					        } 	
				releaseConnection();
			}
	               	
      	return houses;
			
			}


}
