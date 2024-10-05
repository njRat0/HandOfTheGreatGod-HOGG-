package game;

import java.awt.*;
import java.io.File;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

enum TypeOfScreenRender{
    FullScreen,
    OptionalWithBorders,
    OptionalWithoutBorders
}

public class Settings {
    public static int sizeOfUI = 1;
    public static final int STANDART_WINDOW_SIZE_Y = 720;
    public static final int STANDART_WINDOW_SIZE_X = 1280;
    public static final float COEFFICIENT_OF_DIAGANOL_MOVING = 0.8f;
    public static float coeficientOfGameScreen = 1;
    public static Dimension screenSize = new Dimension();
    public static Dimension gameScreenSize = new Dimension();
    public static TypeOfScreenRender typeOfScreenRender;

    public static int maxFps = 30;

    public static void Init(){
        System.out.println("settings init");
        File f = new File("data\\SettingsOfGame.txt");
        if(f.exists() && !f.isDirectory()) { 
            System.out.println("file exist");
            GetDataFromFile();
        }
        else{
            //settingsData = new File("data\\SettingsOfGame.txt");
            try{
                f.createNewFile();
                SetUpSettings();
                SaveDataToFile();
            }
            catch (IOException e){
                System.out.println("The settings data file are already created");
            }
        }
    }

    public static void SetUpSettings(){
        screenSize = Toolkit.getDefaultToolkit().getScreenSize(); 
		gameScreenSize.width = screenSize.width;
		gameScreenSize.height = (int)((float)gameScreenSize.width / 16 * 9);
		coeficientOfGameScreen = (float)gameScreenSize.width / (float)Settings.STANDART_WINDOW_SIZE_X;
        typeOfScreenRender = TypeOfScreenRender.OptionalWithoutBorders;
        SaveDataToFile();
    }

    public static void GetDataFromFile(){
        try{
            List<String> data = Files.readAllLines(Paths.get("data\\SettingsOfGame.txt"));
            System.out.println(data);
            screenSize.width = Integer.valueOf(data.get(0).substring(data.get(0).indexOf(": ") + 2));
            screenSize.height = Integer.valueOf(data.get(1).substring(data.get(1).indexOf(": ")+ 2));
            gameScreenSize.width = Integer.valueOf(data.get(2).substring(data.get(2).indexOf(": ")+ 2));
            gameScreenSize.height = Integer.valueOf(data.get(3).substring(data.get(3).indexOf(": ")+ 2));
            coeficientOfGameScreen = Integer.valueOf(data.get(4).substring(data.get(4).indexOf(": ")+ 2));
            typeOfScreenRender = TypeOfScreenRender.valueOf(data.get(5).substring(data.get(5).indexOf(": ")+ 2));
        }
        catch(IOException e){
            System.out.println("Error: cannot find settings data");
            Init();
        }
       
    }

    public static void SaveDataToFile(){
        try{
            //List<String> data = Files.readAllLines(Paths.get("res\\Data\\SettingsOfGame.txt"));
            List<String> data = new ArrayList<String>();
            data.add("screenSizeX: " + (int)screenSize.getWidth());
            data.add("screenSizeY: " + (int)screenSize.getHeight());
            data.add("gameScreenSizeX: " + (int)gameScreenSize.getWidth());
            data.add("gameScreenSizeY: " + (int)gameScreenSize.getHeight());
            data.add("coeficientOfGameScreen: " + (int)coeficientOfGameScreen);
            data.add("typeOfScreenRender: " + typeOfScreenRender);
            Files.writeString(Paths.get("data\\SettingsOfGame.txt"),  String.join("\n", data));
        }
        catch(IOException e){
            System.out.println("Error: cannot find settings data");
            Init();
        }
    }
}




