/*** In The Name of Allah ***/
package game;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
//import java.util.List;

//import javax.swing.JButton;
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

	private long lastRender;
	private ArrayList<Float> fpsHistory;

	private BufferStrategy bufferStrategy;
	
	public Frame(String title) {
		super(title);
		setResizable(false);
		//setExtendedState(JFrame.MAXIMIZED_BOTH); 
		SetUpFrame();
		gameCenterY = gameHeight/2;
		gameCenterX = gameWidth/2;
		lastRender = -1;
		fpsHistory = new ArrayList<>(100);
	}

	public void SetUpFrame(){
		windowSizeX = Settings.screenSize.width;
		windowSizeY = Settings.screenSize.height;
		gameWidth = Settings.gameScreenSize.width ;
		gameHeight = Settings.gameScreenSize.height ; 
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
				g2d.setFont(g2d.getFont().deriveFont(Font.BOLD).deriveFont(12.0f * Settings.coeficientOfGameScreen));
				int strWidth = g2d.getFontMetrics().stringWidth(str);
				g2d.drawString(str, button.GetLocationX() + (button.GetSizeX() - strWidth) / 2,  button.GetLocationY() + button.GetSizeY()/2+ 4 * Settings.coeficientOfGameScreen);
			}
		}
		else if(GameLoop.curLayout == 1){
			int strWidth;
			g2d.setColor(Color.WHITE);
			g2d.setFont(g2d.getFont().deriveFont(Font.BOLD).deriveFont(32.0f * Settings.coeficientOfGameScreen));
			String strSettings = "Settings";
			strWidth = g2d.getFontMetrics().stringWidth(strSettings);
			g2d.drawString(strSettings, 40 , 80 );
			
			g2d.setFont(g2d.getFont().deriveFont(Font.BOLD).deriveFont(14.0f * Settings.coeficientOfGameScreen));
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
				g2d.setFont(g2d.getFont().deriveFont(Font.BOLD).deriveFont(12.0f * Settings.coeficientOfGameScreen));
				strWidth = g2d.getFontMetrics().stringWidth(str);
				g2d.drawString(str, button.GetLocationX() + (button.GetSizeX() - strWidth) / 2,  button.GetLocationY() + button.GetSizeY()/2+ 4 * Settings.coeficientOfGameScreen);
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
	}
}
