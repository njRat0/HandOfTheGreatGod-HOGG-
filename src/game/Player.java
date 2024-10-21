/*** In The Name of Allah ***/
package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class holds the state of game and all of its elements.
 * This class also handles user inputs, which affect the game state.
 * 
 * @author Seyed Mohammad Ghaffarian
 */
public class Player extends Character{
	
	//parameters
	public  static ArrayList<String> itemsInventory = new ArrayList<String>();

	//public  static HashMap<String, Integer> itemsInventory = new HashMap<String, Integer>();

	public Player() {
		
	}
	
	/**
	 * The method which updates the game state.
	 */
	public void update() {

	}

	public void TakeDamage(float amount){
		
	}

	public void TakeHeale(float amount){
		
	}

	public void toDraw(Graphics2D g2d){
	
	}
}

