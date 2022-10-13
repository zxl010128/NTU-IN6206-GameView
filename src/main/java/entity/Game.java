package entity;

public class Game {
	
	private Long id; // game id
	
	private String gameName; // game name
	
	private String gamePicture; // game picture
	
	private String category; // game category
	
	private String introduction; // game introduction
	
	private int score; // game total scores
	
	private int rateNum; // number of users rated
	
	public Game(Long id, String gameName, String gamePicture, String category, String introduction, int score, int rateNum) {
		this.id = id;
		this.gameName = gameName;
		this.gamePicture = gamePicture;
		this.category = category;
		this.introduction = introduction;
		this.score = score;
		this.rateNum = rateNum;
	}
	
	public Game(Long id, String gameName, String gamePicture, String category, int score) {
		this.id = id;
		this.gameName = gameName;
		this.gamePicture = gamePicture;
		this.category = category;
		this.score = score;
	}
	
	public Game(String gameName, int score) {
		this.gameName = gameName;
		this.score = score;
	}
	
	public Game(){
		
	}
	
	public Long getId() {
        return id;
    }
	
	public void setId(Long id) {
        this.id = id;
    }
	
	public String getGameName() {
		return gameName;
	}
	
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	
	public String getGamePicture() {
		return gamePicture;
	}
	
	public void setGamePicture(String gamePicture) {
		this.gamePicture = gamePicture;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getIntroduction() {
		return introduction;
	}
	
	public void setIntroduction(String content) {
		this.introduction = content;
	}
	
	public int getScore() {
		return score;
	}
    
	public void setScore(int score) {
		this.score = score;
	}
	
	public int getRateNum() {
		return rateNum;
	}
	
	public void setRateNum(int rateNum) {
		this.rateNum = rateNum;
	}
}
