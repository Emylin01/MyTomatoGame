
package tomato.engine;

import java.awt.image.BufferedImage;
import java.sql.SQLException;

import tomato.database.DatabaseManager;
/**
 * Main class where the games are coming from.
 * : Controls game mechanics and manages game states.
 *
 */

public class GameEngine {
	private String currentPlayer;
	private String difficultyLevel;
	
	/**
	 * Each player has their own game engine.
	 * 
	 * @param player
	 */
	public GameEngine(String currentPlayer, String difficultyLevel) {
		this.currentPlayer = currentPlayer;
		this.difficultyLevel = difficultyLevel;
        this.score = 0;
	}

	public String getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(String currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	int counter = 0;
	int score = 0;
	GameServer theGames = new GameServer();
	Game current = null;
	

	/**
	 * Retrieves a game. This basic version only has two games that alternate.
	 */
	public BufferedImage nextGame() {
		current = theGames.getRandomGame();
		return current.getImage();

	}

	/**
	 * Checks if the parameter i is a solution to the game URL. If so, score is
	 * increased by one.
	 * 
	 * @param game
	 * @param i
	 * @return
	 */
	public boolean checkSolution( int i) {
		if (i == current.getSolution()) {
			score++;
			return true;
		} else {
			return false;
		}
	}
	public void endGame(int score) {
	    try {
	        DatabaseManager databaseManager = new DatabaseManager();
	        
	        // Update the high score based on the difficulty level
	        switch (difficultyLevel) {
	            case "Easy":
	                databaseManager.updateHighScoreEasy(currentPlayer, score);
	                break;
	            case "Medium":
	                databaseManager.updateHighScoreMedium(currentPlayer, score);
	                break;
	            case "Hard":
	                databaseManager.updateHighScoreHard(currentPlayer, score);
	                break;
	            default:
	                // Handle the case where the difficulty level is unknown
	                break;
	        }
	        
	        databaseManager.closeConnection(); // Close the database connection
	    } catch (SQLException e) {
	        e.printStackTrace();
	        // Handle the SQLException, such as displaying an error message
	    }
	}
	public void endGame() {
	    try {
	        DatabaseManager databaseManager = new DatabaseManager();
	        
	        // Update the high score based on the difficulty level
	        switch (difficultyLevel) {
	            case "Easy":
	                databaseManager.updateHighScoreEasy(currentPlayer, score);
	                break;
	            case "Medium":
	                databaseManager.updateHighScoreMedium(currentPlayer, score);
	                break;
	            case "Hard":
	                databaseManager.updateHighScoreHard(currentPlayer, score);
	                break;
	            default:
	                // Handle the case where the difficulty level is unknown
	                break;
	        }
	        
	        databaseManager.closeConnection(); // Close the database connection
	    } catch (SQLException e) {
	        e.printStackTrace();
	        // Handle the SQLException, such as displaying an error message
	    }
	}




	/**
	 * Retrieves the score.
	 * 
	 * @param player
	 * @return
	 */
	public int getScore() {
		return score;
	}

}
