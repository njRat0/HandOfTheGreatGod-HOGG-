package game;

import java.awt.*;
import java.io.File;
import java.nio.file.*;
import java.util.List;
import java.io.IOException;

enum TypeOfScreenRender{
    FullScreen,
    OptionalWithBorders,
    OptionalWithoutBorders
}

public class Settings {
    private static File settingsData;
    public static int sizeOfUI = 1;
    public static Dimension sizeOfWindow;
    public static final int STANDART_WINDOW_SIZE_Y = 720;
    public static final int STANDART_WINDOW_SIZE_X = 1280;
    public static final float COEFFICIENT_OF_DIAGANOL_MOVING = 0.8f;
    public static float coeficientOfGameScreen = 1;
    public static Dimension screenSize;
    public static Dimension gameScreenSize;
    public static TypeOfScreenRender typeOfScreenRender;

    // static final public int originalTileSize = 16;
    // static public int screenScale = 3;
    //static final int tileSize = 32; // <----
    // final int maxScreenCol = 16;
    // final int maxScreenRow = 32;
    public static int maxFps = 30;

    public static void SetUpSettings(){
        screenSize = Toolkit.getDefaultToolkit().getScreenSize(); 
		gameScreenSize.width = screenSize.width;
		gameScreenSize.height = (int)((float)gameScreenSize.width / 16 * 9);
		coeficientOfGameScreen = (float)gameScreenSize.width / (float)Settings.STANDART_WINDOW_SIZE_X;
    }

    public void GetDataFromFile(){
        try{
            List<String> data = Files.readAllLines(Paths.get("res\\Data\\SettingsOfGame.txt"));
        }
        catch(IOException e){
            System.out.println("Error: cannot find settings data");
            settingsData = new File("res\\Data\\SettingsOfGame.txt");
            SetUpSettings();
        }
       
    }

    public void SaveDataToFile(){

    }
}




