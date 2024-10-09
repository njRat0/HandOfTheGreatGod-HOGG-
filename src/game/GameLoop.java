/*** In The Name of Allah ***/
package game;

import java.awt.Color;
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
	private static Player player;
	public static ArrayList<InteractingObject> listOfInteractingObjects = new ArrayList<InteractingObject>();

	public static boolean isPause = false;

	public static boolean isChoosedClass;

	

	//ChoosingSkill system
	public static int probabilityOfAllSkills;
	public static ArrayList<MyButton> chooseSkillButtons = new ArrayList<MyButton>();
	public static int maxNumberOfActiveSkills = 2;
	public static int maxNumberOfPassiveSkills = 6;
	public static int countChoosingSlotsForAS = 3; //-->For active skills
	public static int countChoosingSlotsForPS = 3; //-->for passive skills
	//private int maxNumberOfUpgratingParameters = 2;
	public static boolean isChoosingSkills = true;

	public GameLoop(Frame frame) {
		canvas = frame;
	}
	
	/**
	 * This must be called before the game loop starts.
	 */
	public void init() {
		// Perform all initializations ...
		player = new Player();
		canvas.addKeyListener(player.getKeyListener());
		canvas.addMouseListener(player.getMouseListener());
		canvas.addMouseMotionListener(player.getMouseMotionListener());
		SetUp_MenuButtons();
		SetUp_SettingsButtons();
		isPause = true;
	}


	public static int timer = 0;
	public int levelOfDificulty = 0;

	public static int curLayout = 0; // 0 -> menu; 1 -> settings; 2 -> rules; 3 -> game
	public static MyButton[] menuButtons = new MyButton[4];
	public static MyButton[] settingsButtons = new MyButton[4];
	public static MyButton[] rulesButtons = new MyButton[1];
	public static void SetUp_MenuButtons(){
		for(int i = 0; i < menuButtons.length; i++){
			menuButtons[i] = new MyButton(player, TypeOfButton.Menu);
			menuButtons[i].id=i;
			menuButtons[i].borderSize = 3;
			menuButtons[i].colorBackground = new Color(125, 125, 125);
			menuButtons[i].colorBorders = new Color(0, 0, 0);
			menuButtons[i].colorOver = new Color(94, 94, 94);
			menuButtons[i].colorClick = new Color(0, 0, 0);
			menuButtons[i].SetSize(100, 50);
			menuButtons[i].SetLocation(Frame.gameCenterX -50 , Frame.gameCenterY -240 + 60 * i );
		}
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
		for(int i = 0; i < settingsButtons.length; i++){
			settingsButtons[i] = new MyButton(player, TypeOfButton.Menu);
			settingsButtons[i].id= i;
			settingsButtons[i].borderSize = 3;
			settingsButtons[i].colorBackground = new Color(125, 125, 125);
			settingsButtons[i].colorBorders = new Color(0, 0, 0);
			settingsButtons[i].colorOver = new Color(94, 94, 94);
			settingsButtons[i].colorClick = new Color(0, 0, 0);
			settingsButtons[i].SetSize(100, 50);
			settingsButtons[i].SetLocation(Frame.gameCenterX -50 , Frame.gameCenterY -240 + 60 * i );
		}
		settingsButtons[0].name = "ChangeResolution";
		settingsButtons[1].name = "Test1";
		settingsButtons[2].name = "Test2";
		settingsButtons[3].name = "Back";
        
		settingsButtons[2].goTo = 2;
		settingsButtons[3].goTo = 0;
	}

	@Override
	public void run() {
		boolean gameOver = false;
		isPause = true;
		while (!gameOver) {
			try {
				long start = System.currentTimeMillis();
				//System.out.println(player.mouseX + ", " + player.mouseY);
				if(curLayout == 0){
					for(MyButton button : GameLoop.menuButtons){
						button.update();
					}
					
					for(MyButton button : GameLoop.settingsButtons){
						button.update();
					}
					//continue;
				}
				if(isPause == false){
					timer++;
					//player.update();
					
				}
				canvas.render(player, listOfInteractingObjects);
				//gameOver = player.gameOver;
				//
				long delay = (1000 / FPS) - (System.currentTimeMillis() - start);
				if (delay > 0)
					Thread.sleep(delay);
			} catch (InterruptedException ex) {
			}
		}
		canvas.render(player, listOfInteractingObjects);
	}

}
