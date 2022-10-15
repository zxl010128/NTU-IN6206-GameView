package DAO;

import javax.mail.Authenticator;



//import java.util.random.*;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.mail.*;
import javax.mail.PasswordAuthentication;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import javax.mail.Message;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import entity.User;

public class UserDBAO {

	Connection con;
	private boolean conFree = true;

	// Database configuration
	public static String url = "jdbc:mysql://localhost:3306/gameview";
	public static String dbdriver = "com.mysql.cj.jdbc.Driver";
	public static String username = "root";
	public static String password = "123456";

	public static void main(String[] args) { // test database connection
		try {
			UserDBAO user = new UserDBAO();
		} catch (Exception e) {
			System.out.println("Error!");
		}
	}

	public UserDBAO() throws Exception {
		try {

			Class.forName(dbdriver);
			con = DriverManager.getConnection(url, username, password);
			System.out.println("Successfully Connected!");
		} catch (Exception ex) {
			System.out.println("Exception in userDBAO: " + ex);
			throw new Exception("Couldn't open connection to database: " + ex.getMessage());
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

	public boolean ifEmailExists(String email) {
		boolean status = false;
		try {
			String selectStatement = "select user_id " + "from profile_table where email = ?";
			getConnection();
			PreparedStatement prepStmt = con.prepareStatement(selectStatement);
			prepStmt.setString(1, email);
			ResultSet rs = prepStmt.executeQuery();

			if (rs.next()) {
				status = true;
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
		return status;
	}
	
	public String getMD5(String password) {
		 try {
	            MessageDigest md = MessageDigest.getInstance("SHA");//��������ָ���㷨���Ƶ�ժҪ
	            md.update(password.getBytes());                    //ʹ��ָ�����ֽ��������ժҪ
	            byte mdBytes[] = md.digest();                 //���й�ϣ���㲢����һ���ֽ�����

	            String hash = "";
	            for(int i= 0;i<mdBytes.length;i++){           //ѭ���ֽ�����
	                int temp;
	                if(mdBytes[i]<0)                          //�����С��0���ֽ�,��ת��Ϊ����
	                    temp =256+mdBytes[i];
	                else
	                    temp=mdBytes[i];
	                if(temp<16)
	                    hash+= "0";
	                hash+=Integer.toString(temp,16);         //���ֽ�ת��Ϊ16���ƺ�ת��Ϊ�ַ���
	            }
	            return hash;
	        } catch (NoSuchAlgorithmException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }

	public boolean register(String username, String password, String phonenumber, String email,
			String dob, int gender) {
		boolean status = false;
		try {
			String selectStatement = "insert into profile_table (user_id,username,password,facepicture,phonenumber,email,dob,gender,coin,following_list,fans_list,bookmark_list,token,reset_code,createtime) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
			getConnection();
			PreparedStatement prepStmt = con.prepareStatement(selectStatement);

			// find current max user_id
			Long maxID = (long) 0;
			String selectMaxid = "select MAX(user_id) as maxid from profile_table";
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(selectMaxid);
			if (rs.next()) {
				maxID = rs.getLong("maxid");
				// System.out.println(maxID);
			}
			statement.close();

			Long user_id = maxID + 1;
					
			password = getMD5(password);
			prepStmt.setLong(1, user_id);
			prepStmt.setString(2, username);
			prepStmt.setString(3, password);
			prepStmt.setString(4, "");
			prepStmt.setString(5, phonenumber);
			prepStmt.setString(6, email);
			prepStmt.setString(7, dob);
			prepStmt.setInt(8, gender);
			prepStmt.setInt(9, 0);
			prepStmt.setString(10, "[]");
			prepStmt.setString(11, "[]");
			prepStmt.setString(12, "[]");

			// generate token
			prepStmt.setString(13, "");

			prepStmt.setString(14, "");
			prepStmt.setTimestamp(15, new java.sql.Timestamp(new java.util.Date().getTime()));

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

	public boolean changeProfile(String username, String facepicture, String password, String email, String phonenumber,
			int gender, String dob, Long user_id) {
		boolean status = false;
		try {
			String sql = "update profile_table "
					+ "set username=?,facepicture=?,password=?,email=?,phonenumber=?,gender=?,dob=? "
					+ "where user_id=? ";
			getConnection();
			PreparedStatement prepStmt = con.prepareStatement(sql);
			prepStmt.setString(1, username);
			prepStmt.setString(2, facepicture);
			prepStmt.setString(3, password);
			prepStmt.setString(4, email);
			prepStmt.setString(5, phonenumber);
			prepStmt.setInt(6, gender);
			prepStmt.setString(7, dob);
			prepStmt.setLong(8, user_id);

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

	// verify input old password when changing password
	public boolean verifyPassword(String password, Long user_id) {
		boolean status = false;
		try {
			String sql = "select password from profile_table where user_id=?";
			getConnection();
			PreparedStatement prepStmt = con.prepareStatement(sql);
			prepStmt.setLong(1, user_id);
			ResultSet rs = prepStmt.executeQuery();

			if (rs.next()) {
				String oldPass = rs.getString("password");
				if (oldPass == password) {
					status = true;
				}
			}
			prepStmt.close();
			releaseConnection();
		} catch (SQLException ex) {
			releaseConnection();
			ex.printStackTrace();
		}
		return status;
	}
	
	public boolean addCoin(Long userId, int num) {
		boolean status = false;
    	try {
    		String select = "select coin from profile_table where user_id = ?";
			getConnection();
			PreparedStatement prepStmt = con.prepareStatement(select);
			prepStmt.setLong(1, userId);
			ResultSet rs = prepStmt.executeQuery();
			int coin = 0;
			if (rs.next()) {
				coin = rs.getInt("coin") + num;
			}
			prepStmt.close();
			
    		String selectStatement = "update profile_table " + "set coin=? " + "where user_id=? ";
            PreparedStatement prepStmt0 = con.prepareStatement(selectStatement);
            prepStmt0.setInt(1, coin);
            prepStmt0.setLong(2, userId);
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

	
	 public User findByUserid(Long user_id) { 
		 User ruser= new User(); 
		 try { 
			 String selectStatement = "select user_id,username,facepicture,phonenumber,email,dob,gender " + "from profile_table where user_id = ?"; 
			 getConnection(); 
			 PreparedStatement prepStmt = con.prepareStatement(selectStatement); 
			 prepStmt.setLong(1,user_id); 
			 ResultSet rs = prepStmt.executeQuery(); 
			 if (rs.next()) { 
				 User user = new User(rs.getLong("user_id"), rs.getString("username"), rs.getString("facepicture"), rs.getString("phonenumber"), rs.getString("email"),rs.getString("dob"), rs.getInt("gender")); 
				 ruser = user;
				 prepStmt.close(); 
				 releaseConnection();
			 } else { 
				 prepStmt.close(); 
				 releaseConnection(); 
				 ruser = null;
			 }
	 } catch (SQLException ex) { 
		 releaseConnection(); 
		 ex.printStackTrace(); 
	}
	  return ruser; 
}


	public Long findByEmail(String email) {
		Long id = null;
		try {
			String selectStatement = "select user_id " + "from profile_table where email = ?";
			getConnection();
			PreparedStatement prepStmt = con.prepareStatement(selectStatement);
			prepStmt.setString(1, email);
			ResultSet rs = prepStmt.executeQuery();
			if (rs.next()) {
				id = rs.getLong("user_id");
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
		return id;
	}

	public String findEmailById(Long id) {
		String email="";
		try {
			String selectStatement = "select email " + "from profile_table where user_id = ?";
			getConnection();
			PreparedStatement prepStmt = con.prepareStatement(selectStatement);
			prepStmt.setLong(1, id);
			ResultSet rs = prepStmt.executeQuery();
			if (rs.next()) {
				email = rs.getString("email");
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
		return email;
	}

	public boolean loginByEmail(String email, String password) {
		boolean status = false;
		try {
			String selectStatement = "select password " + "from profile_table where email = ?";
			getConnection();
			PreparedStatement prepStmt = con.prepareStatement(selectStatement);
			prepStmt.setString(1, email);
			ResultSet rs = prepStmt.executeQuery();
			password = getMD5(password);
			if (rs.next()) {
				String pswd = rs.getString("password");
				if (pswd.equals(password)) {
					status = true;
				}
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
		return status;
	}

	public boolean loginByUsername(String username, String password) {
		boolean status = false;
		try {
			String selectStatement = "select password " + "from profile_table where username = ?";
			getConnection();
			PreparedStatement prepStmt = con.prepareStatement(selectStatement);
			prepStmt.setString(1, username);
			ResultSet rs = prepStmt.executeQuery();
			password = getMD5(password);
			if (rs.next()) {
				String pswd = rs.getString("password");
				// System.out.println(pswd);
				if (pswd.equals(password)) {
					status = true;
				}
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
		return status;
	}

	public boolean checkUsernameExists(String username) {
		boolean status = false;
		try {
			String selectStatement = "select username from profile_table where username = ?";
			getConnection();
			PreparedStatement prepStmt = con.prepareStatement(selectStatement);
			prepStmt.setString(1, username);
			ResultSet rs = prepStmt.executeQuery();
			if (rs.next()) {
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

	public boolean sendResetCode(String email) { 
		   boolean status = false; 
		   //generate new reset code 
		   Random r = new Random(); 
		   String resetcode = String.valueOf(r.nextInt(899999) + 100000); 
		   try { String selectStatement =
		   "update profile_table " +"set reset_code=? "+"where email=? ";
		   getConnection(); 
		   PreparedStatement prepStmt =con.prepareStatement(selectStatement);
		   prepStmt.setString(1, resetcode); 
		   prepStmt.setString(2, email);
		   
		   int x = prepStmt.executeUpdate(); 
		   prepStmt.close(); 
		   releaseConnection(); 
		   if(x == 0) 
		   { return status; } 
		   } 
		   catch (SQLException ex) {
		    releaseConnection();
		   ex.printStackTrace(); }
		   
		   try { String adminEmail = "w627661598@sina.cn"; 
		   String adminPassword = "00a55bdf55934c44";
		   java.util.Properties props = new Properties();
		   props.put("mail.smtp.auth","true"); 
		   props.put("mail.smtp.host", "smtp.sina.cn");
		   props.put("mail.transport.protocol", "smtp");
		   props.put("mail.user", adminEmail); 
		   props.put("mail.password",adminPassword);
		   Authenticator authenticator = new Authenticator() {
		   @Override 
		   protected PasswordAuthentication getPasswordAuthentication() {
		   String userName = props.getProperty("mail.user"); 
		   String password = props.getProperty("mail.password");
		   return new PasswordAuthentication(userName, password); 
		   	} 
		   };
		   
		   Session mailSession = Session.getInstance(props, authenticator); 
		   MimeMessage message = new MimeMessage(mailSession); 
		   String username =props.getProperty("mail.user"); 
		   InternetAddress from = new InternetAddress(username,"GameView"); 
		   message.setFrom(from); 
		   InternetAddress toAddress = new InternetAddress(email); 
		   message.setRecipient(Message.RecipientType.TO,toAddress); 
		   message.setSubject("GameView Validation Code");
		   message.setContent(resetcode, "text/html;charset=UTF-8");
		   Transport.send(message); status = true; 
		   }catch (Exception e){
		   e.printStackTrace(); }
		   
		   return status; 
	}

	public boolean validationCode(String email, String code) {
		boolean valid = false;
		try {
			String select = "select reset_code from profile_table where email = ?";
			getConnection();
			PreparedStatement prepStmt = con.prepareStatement(select);
			prepStmt.setString(1, email);
			ResultSet rs = prepStmt.executeQuery();
			if (rs.next()) {
				String resetcode = rs.getString("reset_code");
				if (resetcode.equals(code)) {
					valid = true;
				}
			}
			prepStmt.close();
			releaseConnection();
		} catch (SQLException ex) {
			releaseConnection();
			ex.printStackTrace();
		}
		return valid;
	}

	public boolean resetPassword(String email, String newPwd) {
		boolean status = false;
		try {
			String update = "update profile_table " + "set password=? " + "where email=? ";
			PreparedStatement prepStmt = con.prepareStatement(update);
			newPwd = getMD5(newPwd);
			prepStmt.setString(1, newPwd);
			prepStmt.setString(2, email);
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

	public String generateToken(String input) {
		String result = "";
		try {
			// generate token
			SecureRandom secureRandom = new SecureRandom();
			Base64.Encoder base64Encoder = Base64.getUrlEncoder();
			byte[] randomBytes = new byte[24];
			secureRandom.nextBytes(randomBytes);
			String token = base64Encoder.encodeToString(randomBytes);
			String selectStatement;
			if (input.contains("@")) {
				selectStatement = "update profile_table " + "set token=? " + "where email=? ";
			} else {
				selectStatement = "update profile_table " + "set token=? " + "where username=? ";
			}
			// System.out.println(token);
			getConnection();
			PreparedStatement prepStmt = con.prepareStatement(selectStatement);

			prepStmt.setString(1, token);
			prepStmt.setString(2, input);

			int x = prepStmt.executeUpdate();
			if (x == 1) {
				result = token;
			}
			prepStmt.close();
			releaseConnection();
		} catch (SQLException ex) {
			releaseConnection();
			ex.printStackTrace();
		}

		return result;
	}

	public Long identifyId(String token) {
		Long id = (long)0;
		try {
			String select = "select user_id from profile_table where token = ?";
			getConnection();
			PreparedStatement prepStmt = con.prepareStatement(select);
			prepStmt.setString(1, token);
			ResultSet rs = prepStmt.executeQuery();
			if (rs.next()) {
				id = rs.getLong("user_id");
			}
			prepStmt.close();
			releaseConnection();
		} catch (SQLException ex) {
			releaseConnection();
			ex.printStackTrace();
		}
		return id;
	}

//	public boolean newFollowing(Long followerid, Long followedid) {
//		boolean status = false;
//		try {
//			String select = "select following_list from profile_table where user_id = ?";
//			getConnection();
//			PreparedStatement prepStmt1 = con.prepareStatement(select);
//			prepStmt1.setLong(1, followerid);
//			ResultSet rs = prepStmt1.executeQuery();
//			String followinglist = "";
//			if (rs.next()) {
//				followinglist = rs.getString("following_list");
//				if (followinglist == "") {
//					followinglist = String.valueOf(followedid);
//				} else {
//					followinglist = followinglist + "," + String.valueOf(followedid);
//				}
//				prepStmt1.close();
//			} else {
//				prepStmt1.close();
//				releaseConnection();
//				return status;
//			}
//
//			String update = "update profile_table " + "set following_list=? " + "where user_id=? ";
//			PreparedStatement prepStmt2 = con.prepareStatement(update);
//			prepStmt2.setString(1, followinglist);
//			prepStmt2.setLong(2, followerid);
//			int x = prepStmt2.executeUpdate();
//			if (x == 1) {
//				status = true;
//			}
//			prepStmt2.close();
//			releaseConnection();
//		} catch (SQLException ex) {
//			releaseConnection();
//			ex.printStackTrace();
//		}
//		return status;
//	}
//
//	public boolean unfollow(Long followerid, Long followedid) {
//		boolean status = false;
//		try {
//			String select = "select following_list from profile_table where user_id = ?";
//			getConnection();
//			PreparedStatement prepStmt1 = con.prepareStatement(select);
//			prepStmt1.setLong(1, followerid);
//			ResultSet rs = prepStmt1.executeQuery();
//			String followinglist = "";
//			if (rs.next()) {
//				followinglist = rs.getString("following_list");
//				prepStmt1.close();
//			} else {
//				prepStmt1.close();
//				releaseConnection();
//				return status;
//			}
//
//			String update = "update profile_table " + "set following_list=? " + "where user_id=? ";
//			PreparedStatement prepStmt2 = con.prepareStatement(update);
//
//			List<String> list = Arrays.asList(followinglist.split(","));
//			list.remove(String.valueOf(followedid));
//			followinglist = String.join(",", list);
//
//			prepStmt2.setString(1, followinglist);
//			prepStmt2.setLong(2, followerid);
//			int x = prepStmt2.executeUpdate();
//			if (x == 1) {
//				status = true;
//			}
//			prepStmt2.close();
//			releaseConnection();
//		} catch (SQLException ex) {
//			releaseConnection();
//			ex.printStackTrace();
//		}
//		return status;
//	}

//	public boolean removeFans(Long followerid, Long followedid) {
//		boolean status = false;
//		try {
//			String select = "select fans_list from profile_table where user_id = ?";
//			getConnection();
//			PreparedStatement prepStmt1 = con.prepareStatement(select);
//			prepStmt1.setLong(1, followedid);
//			ResultSet rs = prepStmt1.executeQuery();
//			String fanslist = "";
//			if (rs.next()) {
//				fanslist = rs.getString("fans_list");
//				prepStmt1.close();
//			} else {
//				prepStmt1.close();
//				releaseConnection();
//				return status;
//			}
//
//			String update = "update profile_table " + "set fans_list=? " + "where user_id=? ";
//			PreparedStatement prepStmt2 = con.prepareStatement(update);
//
//			List<String> list = Arrays.asList(fanslist.split(","));
//			list.remove(String.valueOf(followerid));
//			fanslist = String.join(",", list);
//
//			prepStmt2.setString(1, fanslist);
//			prepStmt2.setLong(2, followedid);
//			int x = prepStmt2.executeUpdate();
//			if (x == 1) {
//				status = true;
//			}
//			prepStmt2.close();
//			releaseConnection();
//		} catch (SQLException ex) {
//			releaseConnection();
//			ex.printStackTrace();
//		}
//		return status;
//	}

	// ������ǩid��Long

	public boolean newBookmark(Long userid, Long id) {
		boolean status = false;
		try {
			String select = "select Bookmark_list from profile_table where user_id = ?";
			getConnection();
			PreparedStatement prepStmt1 = con.prepareStatement(select);
			prepStmt1.setLong(1, userid);
			ResultSet rs = prepStmt1.executeQuery();
			String bookmarklist = "";
			if (rs.next()) {
				bookmarklist = rs.getString("Bookmark_list");

				if (bookmarklist.equals("[]")) {
					bookmarklist = "["+String.valueOf(id)+"]";
				} else {
					bookmarklist=bookmarklist.replace("]",",");
					bookmarklist = bookmarklist + String.valueOf(id)+ "]";
				}

				prepStmt1.close();
			} else {
				prepStmt1.close();
				releaseConnection();
				return status;
			}

			String update = "update profile_table " + "set bookmark_list=? " + "where user_id=? ";
			PreparedStatement prepStmt2 = con.prepareStatement(update);
			prepStmt2.setString(1, bookmarklist);
			prepStmt2.setLong(2, userid);
			int x = prepStmt2.executeUpdate();
			if (x == 1) {
				status = true;
			}
			prepStmt2.close();
			releaseConnection();
		} catch (SQLException ex) {
			releaseConnection();
			ex.printStackTrace();
		}
		return status;
	}

	public boolean removeBookmark(Long userid, Long id) {
		boolean status = false;
		try {
			String select = "select bookmark_list from profile_table where user_id = ?";
			getConnection();
			PreparedStatement prepStmt1 = con.prepareStatement(select);
			prepStmt1.setLong(1, userid);
			ResultSet rs = prepStmt1.executeQuery();
			String bookmarklist = "";
			if (rs.next()) {
				bookmarklist = rs.getString("bookmarklist");
				if(bookmarklist.indexOf(",")==-1) {
					bookmarklist = "[]";
				}else {
					bookmarklist.replace(","+id, "");
				}
				prepStmt1.close();
			} else {
				prepStmt1.close();
				releaseConnection();
				return status;
			}

			String update = "update profile_table " + "set bookmarklist=? " + "where user_id=? ";
			PreparedStatement prepStmt2 = con.prepareStatement(update);

			List<String> list = Arrays.asList(bookmarklist.split(","));
			list.remove(String.valueOf(id));
			bookmarklist = String.join(",", list);

			prepStmt2.setString(1, bookmarklist);
			prepStmt2.setLong(2, userid);
			int x = prepStmt2.executeUpdate();
			if (x == 1) {
				status = true;
			}
			prepStmt2.close();
			releaseConnection();
		} catch (SQLException ex) {
			releaseConnection();
			ex.printStackTrace();
		}
		return status;
	}

}
