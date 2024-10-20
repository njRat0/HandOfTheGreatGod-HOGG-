package game;
import java.awt.EventQueue;
import javax.swing.JFrame;

public class Main {
	public static Frame frame;
	public static GameLoop game;
	public static UserInputService userInputService;

	// public static void RestartFrame(){
	// 	frame.dispose();
	// 	frame = new Frame("ThroughTheEssence");
	// 	frame.setLocationRelativeTo(null); // put frame at center of screen
	// 	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	// 	frame.setVisible(true);
	// 	frame.initBufferStrategy();

	// 	game.RestartFrame(frame);
	// }
    public static void main(String[] args) {
		// Initialize the global thread-pool
		ThreadPool.init();
		
		// Show the game menu ...
		
		// After the palyer clicks 'PLAY' ...dd
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				userInputService = new UserInputService();
				Settings.Init();
				Settings.SetUpDefaultSettings();
				ItemsService.Init();
				frame = new Frame("ThroughTheEssence");
				frame.setLocationRelativeTo(null); // put frame at center of screen
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				frame.setVisible(true);
				frame.initBufferStrategy();
				//frame.getContentPane().add(new UserInputService());
				// Create and execute the game-loop
				game = new GameLoop(frame);
				SettingButtons.FindCurrentResolution();
				game.init();
				ThreadPool.execute(game);
				// and the game starts ...
			}
		});
    }
}
