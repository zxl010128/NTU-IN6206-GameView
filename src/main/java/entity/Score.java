package entity;

import java.util.Date;

public class Score {
	
	private Long id;
	
	private int score;
	
	private String reason;
	
	private Date createTime;
	
	private Long userId;
	
	private Long gameId;
	
	private User user;
	
	private Game game;
	
	public Score(Long id, int score) {
		this.id = id;
		this.score = score;
	}
	
	public Score(Long id, int score, String reason) {
		this.id = id;
		this.score = score;
		this.reason = reason;
	}
	
	public Score(Long id, int score, String reason, Long userId, Long gameId) {
		this.id = id;
		this.score = score;
		this.reason = reason;
		this.userId = userId;
		this.gameId = gameId;
	}
	
	public Score() {
		
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public String getReason() {
		return reason;
	}
	
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

}
