package game;
import java.util.*;

public class SettingButtons {
    public static final String[] SCREEN_RESOLUTION_LIST = new String[]{
        "640x360",
        "854x480",
        "960x540",
        "1024x576",
        "1280x720",
        "1366x768",
        "1600x900",
        "1920x1080",
        "2560x1440"
    };
    public static final String[] TYPES_SCREEN_RENDER = new String[]{
        "FullScreen",
        "OptionalWithBorders",
        "OptionalWithoutBorders"
    };

    public static int currentResolution = 0;
    public static int currentTypeOfScreen = 0;
    public static void FindCurrentTypeOfScreenRender(){
        currentTypeOfScreen = Arrays.asList(TYPES_SCREEN_RENDER).indexOf(Settings.typeOfScreenRender.toString());
    }
    public static void FindCurrentResolution(){
        currentResolution = Arrays.asList(SCREEN_RESOLUTION_LIST).indexOf((int)(Settings.gameScreenSize.getWidth()) + "x" + (int)(Settings.gameScreenSize.getHeight()));
    }

    private static void ChangeResolutionUp(){
        currentResolution += 1;
        String[] curResolutionValue = SCREEN_RESOLUTION_LIST[currentResolution].split("x");
        if (currentResolution >8){
            currentResolution = 0;
        }
        else if (Integer.valueOf(curResolutionValue[0]) > Settings.screenSize.width && Integer.valueOf(curResolutionValue[1]) > Settings.screenSize.height ){
            currentResolution = 0;
        }

        String[] strFromCurrentResolution = SCREEN_RESOLUTION_LIST[currentResolution].split("x");
        Settings.gameScreenSize.width = Integer.valueOf(strFromCurrentResolution[0]);
        Settings.gameScreenSize.height = Integer.valueOf(strFromCurrentResolution[1]);

        Settings.SetUpCurrentSettings();
    }

    private static void ChangeResolutionDown(){
        currentResolution -= 1;
        if (currentResolution < 0){
            currentResolution = 8;
        }

        String[] strFromCurrentResolution = SCREEN_RESOLUTION_LIST[currentResolution].split("x");
        Settings.gameScreenSize.width = Integer.valueOf(strFromCurrentResolution[0]);
        Settings.gameScreenSize.height = Integer.valueOf(strFromCurrentResolution[1]);

        Settings.SetUpCurrentSettings();
    }

    private static void ChangeTypeOfScreenRenderUp(){
        currentTypeOfScreen += 1;
        if (currentTypeOfScreen > 2){
            currentTypeOfScreen = 0;
        }
        Settings.typeOfScreenRender = TypeOfScreenRender.valueOf(TYPES_SCREEN_RENDER[currentTypeOfScreen]);

        Settings.SetUpCurrentSettings();
    }

    private static void ChangeTypeOfScreenRenderDown(){
        currentTypeOfScreen -= 1;
        if (currentTypeOfScreen <0){
            currentTypeOfScreen = 0;
        }
        Settings.typeOfScreenRender = TypeOfScreenRender.valueOf(TYPES_SCREEN_RENDER[currentTypeOfScreen]);

        Settings.SetUpCurrentSettings();
    }

    public static void Activate(String name){
        if (name == "ChangeResolutionUp"){
            ChangeResolutionUp();
        }
        else if(name == "ChangeResolutionDown"){
            ChangeResolutionDown();
        }
        else if (name == "ChangeTypeOfScreenRenderUp"){
            ChangeTypeOfScreenRenderUp();
        }
        else if(name == "ChangeTypeOfScreenRenderDown"){
            ChangeTypeOfScreenRenderUp();
        }
    }
}
