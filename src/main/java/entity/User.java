package entity;


public class User {
	
	private Long id; // user id
	
	public Long getId() {
        return id;
    }
	
	public void setId(Long id) {
		this.id = id;
	}
	
	private String userName; // user name
	
	public String getUserName() {
        return userName;
    }
	
	public void setUserName(String userName) {
        this.userName = userName;
    }
	
	private String facepic;
	
	public String getfacepic() {
		return facepic;
	}
	
	public void setfacepic(String facepic) {
		this.facepic = facepic;
	}
	
	private String password;
	
	public String getpassword() {
		return password;
	}
	
	public void setpassword(String password) {
		this.password = password;
	}
	
	private String email;
	
	public String getemail() {
		return email;
	}
	
	public void set(String email) {
		this.email = email;
	}
	
	private String phone;
	
	public String getphone() {
		return phone;
	}
	
	public void setphone(String phone) {
		this.phone = phone;
	}
	
	private Integer gender;
	
	public Integer getgender() {
		return gender;
	}
	
	public void setgender(Integer gender) {
		this.gender = gender;
	}
	
	private String dob;
	
	public String getdob() {
		return dob;
	}
	
	public void setdob(String dob) {
		this.dob = dob;
	}
	
	private Integer coin;
	
	public Integer getcoin() {
		return coin;
	}
	
	
//	private String following_list;
//	
//	public String getfollowing_list() {
//		return following_list;
//	}
//	
//	private String fans_list;
//	
//	public String getfans_list() {
//		return fans_list;
//	}
	
	private String bookmark_list;
	
	public String getbookmark_list() {
		return bookmark_list;
	}
	
	private String token;
	
	public String gettoken() {
		return token;
	}
	
	public void settoken(String token) {
		this.token = token;
	}
	
	private String reset_code;
	
	public String getreset_code() {
		return reset_code;
	}
	
	public void setreset_code(String reset_code) {
		this.reset_code = reset_code;
	}
	
	private String createTime;
	
	public String getcreateTime() {
		return createTime;
	}
	
	public String like_list;
	
	public String getlikelist() {
		return like_list;
	}
	
//	public String post_list;
//	
//	public String getpostlist() {
//		return post_list;
//	}
	
	public User(Long id, String userName, String facepic, String phone, String email, String dob, int gender,String createTime,String bookmark_list,String like_list) {
		this.id = id;
		this.userName = userName;
		this.facepic = facepic;
		this.dob = dob;
		this.gender = gender;
		this.phone = phone;
		this.email = email;	
		this.createTime = createTime;
		this.bookmark_list=bookmark_list;
		this.like_list=like_list;
//		this.post_list=post_list;
	}
	
	public User(Long id, String facepic, String userName) {
		this.id = id;
		this.userName = userName;
		this.facepic = facepic;
	}
	
	public User() {
		
	}
	
}
