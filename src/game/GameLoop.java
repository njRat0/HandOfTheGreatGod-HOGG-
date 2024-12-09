/*** In The Name of Allah ***/
package game;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;


/**
 * A very simple structure for the main game loop.
 * THIS IS NOT PERFECT, but works for most situations.
 * Note that to make this work, none of the 2 methods 
 * in the while loop (update() and render()) should be 
 * long running! Both must execute very quickly, without 
 * any waiting and blocking!
 * 
 * Detailed discussion on different game loop design
 * patterns is available in the following link:
 *    http://gameprogrammingpatterns.com/game-loop.html
 */
public class GameLoop implements Runnable {
	
	/**
	 * Frame Per Second.
	 * Higher is better, but any value above 24 is fine.
	 */
	public static final int FPS = 30;
	
	public static Random r = new Random();

	private Frame canvas;
	public static Player player;

	public static boolean isPause = false;


	public GameLoop(Frame frame) {
		canvas = frame;
	}

	public void RestartFrame(Frame frame){
		canvas = frame;
		// canvas.addKeyListener(UserInputService.getKeyListener());
		// canvas.addMouseListener(UserInputService.getMouseListener());
		// canvas.addMouseMotionListener(UserInputService.getMouseMotionListener());
		SetUp_MenuButtons();
		SetUp_SettingsButtons();
		canvas.SetUpFrame();
	}
	
	/**
	 * This must be called before the game loop starts.
	 * 
	 */
	public void init(){
		// Perform all initializations ...
		player = new Player();
		canvas.addKeyListener(UserInputService.getKeyListener());
		canvas.addMouseListener(UserInputService.getMouseListener());
		canvas.addMouseMotionListener(UserInputService.getMouseMotionListener());
		SetUp_MenuButtons();
		SetUp_SettingsButtons();
		//canvas.SetUpFrame();
		isPause = true;
	}


	public static int timer = 0;
	public int levelOfDificulty = 0;

	public static int curLayout = 0; // 0 -> menu; 1 -> settings; 2 -> rules; 3 -> game; 4 -> Battle; 5 -> ItemsInventory; 6 -> CardsInventory
	public static MyButton[] menuButtons = new MyButton[4];
	public static MyButton[] settingsButtons = new MyButton[5];
	public static MyButton[] rulesButtons = new MyButton[1];
	public static void SetUp_MenuButtons(){
		int globalOffset = -(int)(menuButtons.length*35*Settings.coeficientOfGameScreen/2);
		for(int i = 0; i < menuButtons.length; i++){
			menuButtons[i] = new MyButton(TypeOfButton.Menu, null);
			menuButtons[i].id=i;
			menuButtons[i].SetBorderSize((int)(3));
			menuButtons[i].colorBackground = new Color(125, 125, 125);
			menuButtons[i].colorBorders = new Color(0, 0, 0);
			menuButtons[i].colorOver = new Color(94, 94, 94);
			menuButtons[i].colorClick = new Color(0, 0, 0);
			menuButtons[i].SetSize(100, 50);
			menuButtons[i].SetLocation(Frame.gameCenterX - menuButtons[i].GetSizeX()/2, Frame.gameCenterY - menuButtons[i].GetSizeY()/2 + globalOffset);
			globalOffset += menuButtons[i].GetSizeY() + 10;
		}
		//menuButtons[1].SetLocation(1000, 1000);
		menuButtons[0].name = "Start";
		menuButtons[1].name = "Settings";
		menuButtons[2].name = "Rules";
		menuButtons[3].name = "Exit";

		menuButtons[0].goTo = 3;
		menuButtons[1].goTo = 1;
		menuButtons[2].goTo = 2;
		menuButtons[3].goTo = -1;
	}

	public static void SetUp_SettingsButtons(){
		int globalOffset = 0;
		for(int i = 0; i < settingsButtons.length; i++){
			settingsButtons[i] = new MyButton(TypeOfButton.Settings, null);
			settingsButtons[i].id= i;
			settingsButtons[i].SetBorderSize((int)(3));
			settingsButtons[i].SetSize(50, 25);
			settingsButtons[i].colorBackground = new Color(125, 125, 125);
			settingsButtons[i].colorBorders = new Color(0, 0, 0);
			settingsButtons[i].colorOver = new Color(94, 94, 94);
			settingsButtons[i].colorClick = new Color(0, 0, 0);
		}
		
		settingsButtons[0].name = ">";
		settingsButtons[1].name = "<";
		settingsButtons[2].name = ">";
		settingsButtons[3].name = "<";
		settingsButtons[4].name = "Back";

		settingsButtons[0].SetLocation(480 ,95 + settingsButtons[0].GetSizeY()/2);
		settingsButtons[1].SetLocation(240,95 + settingsButtons[1].GetSizeY()/2);
		settingsButtons[2].SetLocation(480, 24 + 95+ settingsButtons[2].GetSizeY()/2);
		settingsButtons[3].SetLocation(240, 24 + 95+ settingsButtons[3].GetSizeY()/2 );

		settingsButtons[0].nameOfFunction = "ChangeResolutionUp";
		settingsButtons[1].nameOfFunction = "ChangeResolutionDown";
		settingsButtons[2].nameOfFunction = "ChangeTypeOfScreenRenderUp";
		settingsButtons[3].nameOfFunction = "ChangeTypeOfScreenRenderDown";

		settingsButtons[4].SetSize(150, 50);
		settingsButtons[4].SetLocation(500, 500);
		//settingsButtons[4].SetLocation((int)((settingsButtons[4].GetSizeX()/2 + 10) * Settings.coeficientOfGameScreen), (int)((1270 - settingsButtons[4].GetSizeY()/2) * Settings.coeficientOfGameScreen));
		settingsButtons[4].goTo = 0;
		//settingsButtons[3].goTo = 0;
	}

	@Override
	public void run() {
		boolean gameOver = false;
		isPause = true;
		while (!gameOver) {
			try {
				long start = System.currentTimeMillis();
				player.Update();
				if(curLayout == 0){
					for(MyButton button : GameLoop.menuButtons){
						button.update();
					}
					//continue;
				}
				else if(curLayout == 1){
					for(MyButton button : GameLoop.settingsButtons){
						button.update();
					}
				}
				else if(curLayout == 5){
					//System.out.println("WORKS");
					Inventory.UpdateInventory();
				}
				if(isPause == false){
					timer++;
					//player.update();
					
				}
				canvas.render(player);
				//gameOver = player.gameOver;
				//
				long delay = (1000 / FPS) - (System.currentTimeMillis() - start);
				if (delay > 0)
					Thread.sleep(delay);
			} catch (InterruptedException ex) {
			}
		}
		canvas.render(player);
	}

	//Button detecting functions
	public static void GoToItemsInventory(){
		System.out.println("inventory open");
		curLayout = 5;
	}
}
