/*** In The Name of Allah ***/
package game;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Frame extends JFrame {
	public static int windowSizeX;
	public static int windowSizeY;
	public static int gameHeight;                  // 720p game resolution
	public static int gameWidth;  // wide aspect ratio
	public static int gameCenterY;
	public static int gameCenterX;
	public static float coeficient;
	public static int startPosOfGameX;
	public static int startPosOfGameY;

	private static BufferedImage inventoryCellImage;
	private static BufferedImage inventoryTrashBinCellImage;
	private static ArrayList<BufferedImage> inventoryEquippingCellsImage = new ArrayList<BufferedImage>();
	private static Dictionary<String, BufferedImage> imageOfInventoryCell = new Hashtable<String,BufferedImage>();

	public static void InitDictionaryOfImageOfInventoryCell(){
		try{
			imageOfInventoryCell.put("Empty", ImageIO.read(new File("res\\UI\\InventoryCell.png")));
			imageOfInventoryCell.put("TrashBin", ImageIO.read(new File("res\\UI\\InventoryTrashBinCell.png")));
			imageOfInventoryCell.put("Helm", ImageIO.read(new File("res\\UI\\InventoryHelmCell.png")));
			imageOfInventoryCell.put("ChestArmor", ImageIO.read(new File("res\\UI\\InventoryChestArmorCell.png")));
			imageOfInventoryCell.put("Leggings", ImageIO.read(new File("res\\UI\\InventoryLeggingsCell.png")));
			imageOfInventoryCell.put("Boots", ImageIO.read(new File("res\\UI\\InventoryBootsCell.png")));
			imageOfInventoryCell.put("Weapon", ImageIO.read(new File("res\\UI\\InventoryWeaponCell.png")));
		}
		catch(IOException e){
			System.out.println("Error: inventory cells images weren`t found");
		}
	}

	private long lastRender;
	private ArrayList<Float> fpsHistory;

	private BufferStrategy bufferStrategy;
	
	public Frame(String title) {
		super(title);
		InitDictionaryOfImageOfInventoryCell();
		setResizable(false);
		//setExtendedState(JFrame.MAXIMIZED_BOTH); 
		SetUpFrame();
		gameCenterY = gameHeight/2;
		gameCenterX = gameWidth/2;
		lastRender = -1;
		fpsHistory = new ArrayList<>(100);
		setFocusTraversalKeysEnabled(false);
	}

	public void SetUpFrame(){
		windowSizeX = Settings.screenSize.width;
		windowSizeY = Settings.screenSize.height;
		gameWidth = Settings.gameScreenSize.width ;
		gameHeight = Settings.gameScreenSize.height ; 
		gameCenterY = gameHeight/2;
		gameCenterX = gameWidth/2;
		if (Settings.typeOfScreenRender == TypeOfScreenRender.FullScreen){
			setExtendedState(JFrame.MAXIMIZED_BOTH);
			gameWidth += 15;
			gameHeight += 15;      
		}
		else if (Settings.typeOfScreenRender == TypeOfScreenRender.OptionalWithoutBorders) {
			windowSizeX = gameWidth;
			windowSizeY = gameHeight;
		}
		startPosOfGameX = (int)((windowSizeX - gameWidth)/2);
		startPosOfGameY = (int)((windowSizeY - gameHeight)/2);
		if (Settings.typeOfScreenRender == TypeOfScreenRender.FullScreen){
			startPosOfGameX += 8;
			startPosOfGameY += 8;
		}

		setSize(windowSizeX, windowSizeY);
		System.out.println(getSize());
		
		GameLoop.SetUp_MenuButtons();
		GameLoop.SetUp_SettingsButtons();

		
		//setLocationRelativeTo(null);
		// setVisible(true);
	}

	/**
	 * This must be called once after the JFrame is shown:
	 *    frame.setVisible(true);
	 * and before any rendering is started.
	 */
	public void initBufferStrategy() {
		// Triple-buffering
		createBufferStrategy(3);
		bufferStrategy = getBufferStrategy();
	}

	
	/**
	 * Game rendering with triple-buffering using BufferStrategy.
	 */
	public void render(Player player) {
		// Get a new graphics context to render the current frame
		// Render single frame
		do {
			// The following loop ensures that the contents of the drawing buffer
			// are consistent in case the underlying surface was recreated
			do {
				// Get a new graphics context every time through the loop
				// to make sure the strategy is validated
				Graphics2D graphics = (Graphics2D) bufferStrategy.getDrawGraphics();
				try {
					doRendering(graphics, player);
				} finally {
					// Dispose the graphics
					graphics.dispose();
				}
				// Repeat the rendering if the drawing buffer contents were restored
			} while (bufferStrategy.contentsRestored());

			// Display the buffer
			bufferStrategy.show();
			// Tell the system to do the drawing NOW;
			// otherwise it can take a few extra ms and will feel jerky!
			Toolkit.getDefaultToolkit().sync();

		// Repeat the rendering if the drawing buffer was lost
		} while (bufferStrategy.contentsLost());
	}
	
	private static int numberOfCycle = 0;
	public static void MassageToPlayer(Graphics2D g2d){

	}
	/**
	 * Rendering all game elements based on the game player.
	 */
	public static MyButton[] listOfMenuButtons = new MyButton[3];
	private void doRendering(Graphics2D g2d, Player player) {
		// Draw background
		g2d.setColor(Color.BLACK);
		g2d.fillRect(startPosOfGameX, startPosOfGameY, gameWidth, gameHeight);
		
		
		if(GameLoop.curLayout == 0){
			for(MyButton button : GameLoop.menuButtons){
				button.toDraw(g2d);
				String str = button.name;
				g2d.setColor(Color.WHITE);
				g2d.setFont(g2d.getFont().deriveFont(Font.BOLD).deriveFont(12.0f ));
				int strWidth = g2d.getFontMetrics().stringWidth(str);
				g2d.drawString(str, button.GetLocationX() + (button.GetSizeX() - strWidth) / 2,  button.GetLocationY() + button.GetSizeY()/2+ 4 );
			}
		}
		else if(GameLoop.curLayout == 1){
			int strWidth;
			g2d.setColor(Color.WHITE);
			g2d.setFont(g2d.getFont().deriveFont(Font.BOLD).deriveFont(32.0f ));
			String strSettings = "Settings";
			strWidth = g2d.getFontMetrics().stringWidth(strSettings);
			g2d.drawString(strSettings, 40 , 80 );
			
			g2d.setFont(g2d.getFont().deriveFont(Font.BOLD).deriveFont(14.0f ));
			String strResolution = "Resolution: ";
			strWidth = g2d.getFontMetrics().stringWidth(strResolution);
			g2d.drawString(strResolution, 120 - strWidth/2, 120);
			String strCurrentResolution = SettingButtons.SCREEN_RESOLUTION_LIST[SettingButtons.currentResolution];
			strWidth = g2d.getFontMetrics().stringWidth(strCurrentResolution);
			g2d.drawString(strCurrentResolution, 360 - strWidth/2, 120 );
			
			String strTypeOfScreenRender = "Type of screen render: ";
			strWidth = g2d.getFontMetrics().stringWidth(strTypeOfScreenRender);
			g2d.drawString(strTypeOfScreenRender, 120 - strWidth/2, (14 + 10+ 120));
			String strCurrentTypeOfScreenRender = SettingButtons.TYPES_SCREEN_RENDER[SettingButtons.currentTypeOfScreen];
			strWidth = g2d.getFontMetrics().stringWidth(strCurrentTypeOfScreenRender);
			g2d.drawString(strCurrentTypeOfScreenRender, 360 - strWidth/2,  (14 + 10+ 120));
			
			for(MyButton button : GameLoop.settingsButtons){
				button.toDraw(g2d);
				String str = button.name;
				g2d.setFont(g2d.getFont().deriveFont(Font.BOLD).deriveFont(12.0f ));
				strWidth = g2d.getFontMetrics().stringWidth(str);
				g2d.drawString(str, button.GetLocationX() + (button.GetSizeX() - strWidth) / 2,  button.GetLocationY() + button.GetSizeY()/2+ 4 );
			}
		}
		else if(GameLoop.curLayout == 2){
			for(MyButton button : GameLoop.rulesButtons){
				button.toDraw(g2d);
			}
		}
		else if(GameLoop.curLayout == 3){
			if(true){
				// Print FPS info
				long currentRender = System.currentTimeMillis();
				if (lastRender > 0) {
					fpsHistory.add(1000.0f / (currentRender - lastRender));
					if (fpsHistory.size() > 100) {
						fpsHistory.remove(0); // remove oldest
					}
					float avg = 0.0f;
					for (float fps : fpsHistory) {
						avg += fps;
					}
					avg /= fpsHistory.size();
					String str = String.format("Average FPS = %.1f , Last Interval = %d ms",
					avg, (currentRender - lastRender));
					g2d.setColor(Color.CYAN);
					g2d.setFont(g2d.getFont().deriveFont(18.0f));
					int strWidth = g2d.getFontMetrics().stringWidth(str);
					int strHeight = g2d.getFontMetrics().getHeight();
					g2d.drawString(str, (gameWidth - strWidth) / 2, strHeight + 25 + startPosOfGameY);
				}
				lastRender = currentRender;
			}
			else{
				String str = "GAME OVER";
				g2d.setColor(Color.WHITE);
				g2d.setFont(g2d.getFont().deriveFont(Font.BOLD).deriveFont(64.0f));
				int strWidth = g2d.getFontMetrics().stringWidth(str);
				g2d.drawString(str, (gameWidth - strWidth) / 2, gameHeight / 2);
			}
		}
		else if(GameLoop.curLayout == 5){
			g2d.setColor(new Color(64, 64, 64));
			g2d.fillRect(640, 0, 640, gameHeight);

			for(int i =0; i < Inventory.cellsOfItemsInventory.size(); i++){
				InventoryCell invCell = Inventory.cellsOfItemsInventory.get(i);
				if(invCell.type != TypeOfInventoryCell.Empty){
					if(Player.itemsInventory[invCell.indexY][invCell.indexX] == null){
						g2d.drawImage(imageOfInventoryCell.get(invCell.type.toString()), invCell.locationOnScreenX, invCell.locationOnScreenY, invCell.sizeX, invCell.sizeY, null);
					}
					else{
						g2d.drawImage(imageOfInventoryCell.get("Empty"), invCell.locationOnScreenX, invCell.locationOnScreenY, invCell.sizeX, invCell.sizeY, null);
					}
				}
				else{
					g2d.drawImage(imageOfInventoryCell.get(invCell.type.toString()), invCell.locationOnScreenX, invCell.locationOnScreenY, invCell.sizeX, invCell.sizeY, null);
				}
				
				if (Player.itemsInventory[invCell.indexY][invCell.indexX] != null){
					g2d.drawImage(ItemsService.GetItemClass(Player.itemsInventory[invCell.indexY][invCell.indexX]).icon, invCell.locationOnScreenX, invCell.locationOnScreenY, invCell.sizeX, invCell.sizeY,null);
					g2d.setColor(Color.white);

					int amountOfItem = Player.itemsInventoryAmount[invCell.indexY][invCell.indexX];
					if (amountOfItem > 1){
						String strAmountOfItem = String.valueOf(amountOfItem);
						int strWidth = g2d.getFontMetrics().stringWidth(strAmountOfItem);
						g2d.drawString(strAmountOfItem, invCell.locationOnScreenX + invCell.sizeX - strWidth, invCell.locationOnScreenY+55);
					}
				}
			}

			if(Inventory.isLeftMouseDragging){
				g2d.drawImage(imageOfInventoryCell.get(Inventory.selectedInventoryCell.type.toString()), Inventory.selectedInventoryCell.locationOnScreenX, Inventory.selectedInventoryCell.locationOnScreenY, Inventory.selectedInventoryCell.sizeX, Inventory.selectedInventoryCell.sizeY, null);
				g2d.drawImage(ItemsService.GetItemClass(Player.itemsInventory[Inventory.selectedSlotCordinate[1]][Inventory.selectedSlotCordinate[0]]).icon, UserInputService.mouseX -32, UserInputService.mouseY -32, 64,64,null);
			}
			else if(Inventory.isRightMouseDragging){
				g2d.drawImage(imageOfInventoryCell.get(Inventory.selectedInventoryCell.type.toString()), Inventory.selectedInventoryCell.locationOnScreenX, Inventory.selectedInventoryCell.locationOnScreenY, Inventory.selectedInventoryCell.sizeX, Inventory.selectedInventoryCell.sizeY, null);				
				g2d.drawImage(ItemsService.GetItemClass(Player.itemsInventory[Inventory.selectedSlotCordinate[1]][Inventory.selectedSlotCordinate[0]]).icon, Inventory.selectedInventoryCell.locationOnScreenX, Inventory.selectedInventoryCell.locationOnScreenY, Inventory.selectedInventoryCell.sizeX, Inventory.selectedInventoryCell.sizeY,null);
				g2d.setColor(Color.white);
				if (Player.itemsInventoryAmount[Inventory.selectedSlotCordinate[1]][Inventory.selectedSlotCordinate[0]] > 2){
					String strAmountOfItem = String.valueOf(Player.itemsInventoryAmount[Inventory.selectedSlotCordinate[1]][Inventory.selectedSlotCordinate[0]] / 2);
					int strWidth = g2d.getFontMetrics().stringWidth(strAmountOfItem);
					g2d.drawString(strAmountOfItem, Inventory.selectedInventoryCell.locationOnScreenX + Inventory.selectedInventoryCell.sizeX - strWidth, Inventory.selectedInventoryCell.locationOnScreenY + 55);
				}

				g2d.drawImage(ItemsService.GetItemClass(Player.itemsInventory[Inventory.selectedSlotCordinate[1]][Inventory.selectedSlotCordinate[0]]).icon, UserInputService.mouseX-32, UserInputService.mouseY-32, 64,64,null);
			}

			if(Inventory.isRightMenuOpen){
				for(MyButton button : Inventory.rightClickMenuButtons){
					button.toDraw(g2d);
					String str = button.name;
					g2d.setColor(Color.WHITE);
					g2d.setFont(g2d.getFont().deriveFont(Font.BOLD).deriveFont(12.0f));
					int strWidth = g2d.getFontMetrics().stringWidth(str);
					g2d.drawString(str, button.GetLocationX() + (button.GetSizeX() - strWidth) / 2,  button.GetLocationY() + button.GetSizeY()/2+ 4 );
				}
			}
		}
	}
}
