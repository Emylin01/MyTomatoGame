package tomato.engine;

import java.awt.image.BufferedImage;
//Represents a game entity, containing an image and its solution.
public class Game {
	BufferedImage image = null; 	
	int solution = -1;

	public Game(BufferedImage image, int solution) {
		super();
		this.image = image;
		this.solution = solution;
	}
	
	/**
	 * The image of the game. 
	 * @return the location of the game.
	 */
	public BufferedImage getImage() {
		return image;
	}

	/**
	 * @return The solution of the game.
	 */
	public int getSolution() {
		return solution;
	}
	
	
	
	


}
