package game;

import java.awt.event.*;

import javax.swing.JPanel;

public class UserInputService {
    public static boolean keyUP, keyDOWN, keyRIGHT, keyLEFT;
	public static boolean mousePress;
	public static int mouseX, mouseY;
    private static MouseHandler mouseHandler;
    private static KeyHandler keyHandler;

    public UserInputService() {
        mouseHandler = new MouseHandler();
        keyHandler = new KeyHandler();
        //addMouseListener(mouseHandler); 
    }

    //--->Mouse handler
    class MouseHandler extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();
            mousePress = true;
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            mousePress = false;
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();
        }
        @Override
        public void mouseDragged(MouseEvent e){
            mouseX = e.getX();
            mouseY = e.getY();
        }
		
	}

    public static MouseListener getMouseListener() {
		return mouseHandler;
	}
	public static MouseMotionListener getMouseMotionListener() {
		return mouseHandler;
	}

    //--->Keys hadler
    public static KeyListener getKeyListener() {
		return keyHandler;
	}
	
	/**
	 * The keyboard handler.
	 */
	class KeyHandler extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode())
			{
				case KeyEvent.VK_W:
					keyUP = true;
					break;
				case KeyEvent.VK_S:
					keyDOWN = true;
					break;
				case KeyEvent.VK_A:
					keyLEFT = true;
					break;
				case KeyEvent.VK_D:
					keyRIGHT = true;
					break;
				case KeyEvent.VK_ESCAPE:
					GameLoop.isPause = (GameLoop.isPause == true)? false:true;
					break;
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			switch (e.getKeyCode())
			{
				case KeyEvent.VK_W:
					keyUP = false;
					break;
				case KeyEvent.VK_S:
					keyDOWN = false;
					break;
				case KeyEvent.VK_A:
					keyLEFT = false;
					break;
				case KeyEvent.VK_D:
					keyRIGHT = false;
					break;
			}
		}

	}
}