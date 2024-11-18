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
	public static int[] sizeOfItemsInventory = new int[]{8,10};
	//public  static ArrayList<String> itemsInventory = new ArrayList<String>();
	public static Dictionary<String, String> eqiupedItems = new Hashtable<String, String>(){{
		
	}};
	public static String[][] itemsInventory = new String[10][8];
	public static int[][] itemsInventoryAmount = new int[10][8];
	public static String TrashBinCell;
	public static int TrashBinCellAmount;

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

	private void InitEquipedItemsDictionary(){
		eqiupedItems.put("Helm", "null");
		eqiupedItems.put("ChestArmor", "null");
		eqiupedItems.put("Leggings", "null");
		eqiupedItems.put("Boots", "null");
		eqiupedItems.put("Weapon1", "null");
		eqiupedItems.put("Weapon2", "null");
		eqiupedItems.put("Ring1", "null");
		eqiupedItems.put("Ring2", "null");
		eqiupedItems.put("Armlet", "null");
		eqiupedItems.put("Necklace", "null");
	}

	public Player() {
		super(null);
		InitEquipedItemsDictionary();
	}
	
	/**
	 * The method which updates the game state.
	 */

	public void TakeDamage(float amount){
		
	}

	public void TakeHeale(float amount){
		
	}

	@Override
	public void ToDraw(Graphics2D g2d) {
		// TODO Auto-generated method stub
	}

	@Override
	public void Update() {
		System.out.println(attributes.get("Strength"));
		System.out.println(parameters.get("MaxHP"));
	}
}

