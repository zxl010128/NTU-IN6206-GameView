package entity;

import java.util.Date;

public class Reply {
	
	private Long id; // reply id
	
	private Date createTime; // reply post time
	
	private String content; // reply content
	
	private Long userId;
	
	private Long CommentId;
	
	//private Long replyToId;
	
	private User user;
	
	private Comment comment;
	
	public Reply(Long id, Date createTime, String content, Long userId, Long CommentId) {
		this.id = id;
		this.createTime = createTime;
		this.content = content;
		this.userId = userId;
		this.CommentId = CommentId;
		//this.replyToId = replyToId;
	}
	
	public Reply(Long id, Date createTime, String content) {
		this.id = id;
		this.createTime = createTime;
		this.content = content;
	}
	
	public Long getID() {
		return id;
	}
	
	public void setID(Long id) {
		this.id = id;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(Date createTIme) {
		this.createTime = createTime;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public Comment getComment() {
        return comment;
    }

    public void setCommnet(Comment comment) {
        this.comment = comment; 
    }
}
