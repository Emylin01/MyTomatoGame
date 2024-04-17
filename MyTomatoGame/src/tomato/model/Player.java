package tomato.model;

public class Player {
	private String username;
    private int score;
    private int level;
    private int lives;

    // Constructor
    public Player(String username) {
        this.username = username;
        this.score = 0;
        this.level = 1;
        this.lives = 3; // Default number of lives
    }

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    // Method to increment the score
    public void incrementScore(int points) {
        this.score += points;
    }

    // Method to decrement the number of lives
    public void decrementLives() {
        if (this.lives > 0) {
            this.lives--;
        }
    }

    // Method to reset player data
    public void reset() {
        this.score = 0;
        this.level = 1;
        this.lives = 3;
    }

    @Override
    public String toString() {
        return "Player{" +
                "username='" + username + '\'' +
                ", score=" + score +
                ", level=" + level +
                ", lives=" + lives +
                '}';
    }

}
