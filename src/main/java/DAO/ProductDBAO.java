package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductDBAO {
	
	Connection con;
    private boolean conFree = true;
    
    // Database configuration
    public static String url = "jdbc:mysql://localhost:3306/gameview";
    public static String dbdriver = "com.mysql.cj.jdbc.Driver";
    public static String username = "root";
    public static String password = "123456";
    
    
    public ProductDBAO() throws Exception {
        try {
      
            Class.forName(dbdriver);
            con = DriverManager.getConnection(url, username, password);
            System.out.println("Successfully Connected!");
        } catch (Exception ex) {
            System.out.println("Exception in productDBAO: " + ex);
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
	
    public int getPrice(Long product_id) {
    	int price = 0;
    	try {
			String selectStatement = "select productprice " + "from product_table where product_id = ?";
			getConnection();
			PreparedStatement prepStmt = con.prepareStatement(selectStatement);
			prepStmt.setLong(1, product_id);
			ResultSet rs = prepStmt.executeQuery();
			if (rs.next()) {
				price = rs.getInt("productprice");
				prepStmt.close();
				releaseConnection();

			} else {
				prepStmt.close();
				releaseConnection();

			}

		} catch (SQLException ex) {
			releaseConnection();
			ex.printStackTrace();
		}
    	return price;
    }
    
    public boolean redeemProduct(Long product_id, Long user_id) {
    	boolean status = false;
    	int price = getPrice(product_id);
    	try {
//			String selectStatement = "select productprice " + "from product_table where product_id = ?";
//			getConnection();
//			PreparedStatement prepStmt = con.prepareStatement(selectStatement);
//			prepStmt.setLong(1, product_id);
//			ResultSet rs = prepStmt.executeQuery();
//			int price = 0;
//			if (rs.next()) {
//				price = rs.getInt("productprice");
//				prepStmt.close();
//				releaseConnection();
//			}
			String statement = "select coin " + "from profile_table where user_id = ?";
			getConnection();
			PreparedStatement prepStmt0 = con.prepareStatement(statement);
			prepStmt0.setLong(1, user_id);
			ResultSet rst = prepStmt0.executeQuery();
			releaseConnection();
			
			int coin;
			if(rst.next()) {
				coin = rst.getInt("coin");
				prepStmt0.close();
				if(coin>=price) {
					String sql = "update profile_table "
							+ "set coin=? "
							+ "where user_id=? ";
					getConnection();
					PreparedStatement prepStmt1 = con.prepareStatement(sql);
					coin = coin - price;
					prepStmt1.setInt(1, coin);
					prepStmt1.setLong(2, user_id);
					int x = prepStmt1.executeUpdate();

					if (x == 1) {
						status = true;
					}
					prepStmt1.close();
					releaseConnection();
				}
			}
		} catch (SQLException ex) {
			releaseConnection();
			ex.printStackTrace();
		}
    	return status;
    }
}
