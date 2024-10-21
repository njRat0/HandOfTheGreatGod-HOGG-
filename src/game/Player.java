/*** In The Name of Allah ***/
package game;

import java.awt.Graphics2D;
import java.util.*;

/**
 * This class holds the state of game and all of its elements.
 * This class also handles user inputs, which affect the game state.
 * 
 * @author Seyed Mohammad Ghaffarian
 */
public class Player extends Character{
	
	//parameters
	public static int size = 8;
	//public  static ArrayList<String> itemsInventory = new ArrayList<String>();
	public static String[][] itemsInventory = new String[8][8];
	public static int[][] itemsInventoryAmount = new int[8][8];

	public static void ChangeTheSizeOfInventory(int size){
		String[][] newItemsInventory = new String[size][8];

		for(int i = 0; i < 8; i++){
			newItemsInventory[i] = itemsInventory[i];
		}
		itemsInventory = newItemsInventory;
	}

	public Player() {
		// InitItemsInventory();
		// items.add(itemsInventory);
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

