package entity;

import java.sql.Timestamp;
import java.util.Date;

public class Comment {
	
	private Long id; // comment id
	
	private int totalLike; // number of likes
	
	private int totalDislike; // number of dislikes
	
	private int totalLove; //  number of marks
	
	private String content; // comment content
	
	private String createTime; // comment post time
	
	private int isGetCoin; // if get coin
	
	private Long userId;
	
	private Long gameId;
	
	private Game game;
	
	public Comment(Long id, int totalLike, int totalDislike, int totalLove, String content, String createTime, int isGetCoin, Long userId, Long gameId) {
		this.id = id;
		this.totalLike = totalLike;
		this.totalDislike = totalDislike;
		this.totalLove = totalLove;
		this.content = content;
		this.createTime = createTime;
		this.isGetCoin = isGetCoin;
		this.userId = userId;
		this.gameId = gameId;
	}
	
	public Comment(Long id, int totalLike, int totalDislike, int totalLove, String content, String createTime) {
		this.id = id;
		this.totalLike = totalLike;
		this.totalDislike = totalDislike;
		this.totalLove = totalLove;
		this.content = content;
		this.createTime = createTime;
	}
	
	public Comment(Long id, String content, String createTime,Long userId) {
		this.id = id;
		this.content = content;
		this.createTime = createTime;
		this.userId=userId;
	}
	
	public Comment(Long id, String content, String createTime) {
		this.id = id;
		this.content = content;
		this.createTime = createTime;
	}
	
	public Comment(String content,int totalLike,String createTime) {
		
		this.totalLike = totalLike;
		
		this.content = content;
		this.createTime = createTime;
	}
	
	public Comment(Long id,String content,int totalLike,String createTime) {
		this.id=id;
		this.totalLike = totalLike;
		this.content = content;
		this.createTime = createTime;
	}
	
	public Comment() {
		
	}
	
	public Long getID() {
		return id;
	}
	
	public void setID(Long id) {
		this.id = id;
	}
	
	public String getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	public int getTotalLike() {
		return totalLike;
	}
	
	public void setTotalLike(int TotalLike) {
		this.totalLike = TotalLike;
	}
	
	public void updateLike() { // get like
		totalLike++;
	}
	
	public int getTotoalDislike() {
		return totalDislike;
	}
	
	public void setTotalDislike(int totalDislike) {
		this.totalDislike = totalDislike;
	}
	
	public void updateDislike() { // get dislike
		totalDislike++;
	}
	
	public int getTotalLove() {
		return totalLove;
	}
	
	public void setTotalLove(int totalLove) {
		this.totalLove = totalLove;
	}
	
	public void updateLove() { // get love
		totalLove++;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public int getIsGetCoin() {
		return isGetCoin;
	}
	
	public void setIsGetCoin() { // reset coin
		isGetCoin = 0;
	}
	
	public void updateIsGetCoin() { // get coin already
		isGetCoin = 1;
	}
	
    public Long getUserId() {
        return userId;
    }

    
    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}