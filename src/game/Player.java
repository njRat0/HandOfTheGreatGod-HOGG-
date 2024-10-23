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
	public static int[] sizeOfItemsInventory = new int[]{8,8};
	//public  static ArrayList<String> itemsInventory = new ArrayList<String>();
	public static String[][] itemsInventory = new String[8][8];
	public static int[][] itemsInventoryAmount = new int[8][8];
	public static int[] selectedItemForDraggCordinates = new int[2];

	public static void ChangeTheSizeOfInventory(int[] newSize){
		sizeOfItemsInventory = newSize;
		String[][] newItemsInventory = new String[sizeOfItemsInventory[1]][sizeOfItemsInventory[0]];
		int[][] newItemsInventoryAmount = new int[sizeOfItemsInventory[1]][sizeOfItemsInventory[0]];

		for(int y = 0; y < itemsInventory.length; y++){
			for(int x = 0; x< itemsInventory[0].length; x++){
				newItemsInventory[y][x] = itemsInventory[y][x];
				newItemsInventoryAmount[y][x] = itemsInventoryAmount[y][x];
			}
			
		}
		itemsInventory = newItemsInventory;
		itemsInventoryAmount = newItemsInventoryAmount;
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

