/*** In The Name of Allah ***/
package game;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
//import java.util.List;

//import javax.swing.JButton;
import javax.swing.JFrame;

public class Frame extends JFrame {
	public static int gameHeight;                  // 720p game resolution
	public static int gameWidth;  // wide aspect ratio
	public static int gameCenterY;
	public static int gameCenterX;
	public static float coeficient;

	private long lastRender;
	private ArrayList<Float> fpsHistory;

	private BufferStrategy bufferStrategy;
	
	public Frame(String title) {
		super(title);
		setResizable(false);
		//setExtendedState(JFrame.MAXIMIZED_BOTH); 
		SetUp_WindowSizeParameters();
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize(); 
		setSize(size.width + 10, size.height + 10);
		lastRender = -1;
		fpsHistory = new ArrayList<>(100);
	}


	public static void SetUp_WindowSizeParameters(){
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize(); 
		gameWidth = size.width + 5;
		gameHeight = (int)((float)gameWidth / 16 * 9);      
		gameCenterY = gameHeight/2;
		gameCenterX = gameWidth/2;
		Settings.coeficientOfScreen = (float)gameWidth / (float)Settings.STANDART_WINDOW_SIZE_X;
		//coeficientY = (float)gameHeight / (float)Settings.STANDART_WINDOW_SIZE_Y;
		
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
	public void render(Player player, ArrayList<InteractingObject> interactingObjects) {
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
					doRendering(graphics, player, interactingObjects);
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
	
	/**
	 * Rendering all game elements based on the game player.
	 */
	public static MyButton[] listOfMenuButtons = new MyButton[3];
	private void doRendering(Graphics2D g2d, Player player, ArrayList<InteractingObject> interactingObjects) {
		// Draw background
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, gameWidth, gameHeight);
		
		
		if(GameLoop.curLayout == 0){
			for(MyButton button : GameLoop.menuButtons){
				button.toDraw(g2d);
				String str = button.name;
				g2d.setColor(Color.WHITE);
				g2d.setFont(g2d.getFont().deriveFont(Font.BOLD).deriveFont(12.0f));
				int strWidth = g2d.getFontMetrics().stringWidth(str);
				g2d.drawString(str, button.GetLocationX() + (100 - strWidth) / 2,  button.GetLocationY() + 28);
			}
		}
		else if(GameLoop.curLayout == 1){
			for(MyButton button : GameLoop.settingsButtons){
				button.toDraw(g2d);
			}
		}
		else if(GameLoop.curLayout == 2){
			for(MyButton button : GameLoop.rulesButtons){
				button.toDraw(g2d);
			}
		}
		else if(GameLoop.curLayout == 3){
			if(!player.isDead){
				//Draw player and enemies
				//player.toDraw(g2d);
				if(interactingObjects != null){
					for(InteractingObject object : interactingObjects){
						object.toDraw(g2d);
					}
				}
				//>>>>UI rendering
					//hp bar
				g2d.setColor(Color.black);
				g2d.fillRect(8, 31, (int)(player.maxHP + 0.5f)  + 5, 25);
				g2d.setColor(Color.red);
				g2d.fillRect(8, 34, (int)(player.curHP + 0.5f), 19);
					//level indicator
				String str1 = "Level: " + player.level;
				g2d.setColor(Color.WHITE);
				g2d.setFont(g2d.getFont().deriveFont(Font.BOLD).deriveFont(12.0f));
				g2d.drawString(str1, 18, 75);
					//exp bar
				g2d.setColor(Color.black);
				g2d.fillRect(0, gameHeight - 30, gameWidth, 30);
				g2d.setColor(Color.BLUE);
				g2d.fillRect(0, gameHeight - 27, (int)((float)(player.curEXP) / player.expForLevelUp  * gameWidth), 16);
					//timer
				String strTimer = "Seconds: " + (int)(GameLoop.timer / Settings.maxFps);
				g2d.setColor(Color.WHITE);
				g2d.setFont(g2d.getFont().deriveFont(Font.BOLD).deriveFont(15f));
				int strWidthTimer = g2d.getFontMetrics().stringWidth(strTimer);
				g2d.drawString(strTimer, gameWidth - strWidthTimer - 40, 50);
				//<<<
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
					g2d.drawString(str, (gameWidth - strWidth) / 2, strHeight + 25);
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
	}
}
