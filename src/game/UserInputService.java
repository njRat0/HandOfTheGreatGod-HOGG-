package game;

import java.awt.event.*;

import javax.swing.JPanel;

public class UserInputService {
    public static boolean keyUP, keyDOWN, keyRIGHT, keyLEFT;
	public static boolean leftMousePress;
	public static boolean rightMousePress;
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

            if (e.getButton() == MouseEvent.BUTTON1){
				leftMousePress = true;
			} else if (e.getButton() == MouseEvent.BUTTON2){
				System.out.println("Middle button clicked");
			} else if (e.getButton() == MouseEvent.BUTTON3) {
				rightMousePress = true;
			}
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            leftMousePress = false;
			rightMousePress = false;
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
					ItemsService.AddItemToInventoryOfPlayer("RegularSword", 1);
					ItemsService.AddItemToInventoryOfPlayer("IronHelm", 1);
					ItemsService.AddItemToInventoryOfPlayer("IronChestArmor", 1);
					ItemsService.AddItemToInventoryOfPlayer("IronLeggings", 1);
					ItemsService.AddItemToInventoryOfPlayer("IronBoots", 1);
					break;
				case KeyEvent.VK_S:
					keyDOWN = true;
					ItemsService.AddItemToInventoryOfPlayer("RedSoul", 50);
					break;
				case KeyEvent.VK_A:
					keyLEFT = true;
					GameLoop.player.ChangeAttribute("Strength", 1);
					break;
				case KeyEvent.VK_D:
					keyRIGHT = true;	
					break;
				case KeyEvent.VK_TAB:
					GameLoop.GoToItemsInventory();
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