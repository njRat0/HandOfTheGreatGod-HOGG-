package game;

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
    public static int currentResolution = 0;

    private static void ChangeResolutionUp(){
        currentResolution += 1;
        String[] curResolutionValue = SCREEN_RESOLUTION_LIST[currentResolution].split("x");
        if (currentResolution >8){
            currentResolution = 0;
        }
        else if (Integer.valueOf(curResolutionValue[0]) > Settings.screenSize.width && Integer.valueOf(curResolutionValue[1]) > Settings.screenSize.height ){
            currentResolution = 0;
            
        }
    }

    private static void ChangeResolutionDown(){
        currentResolution -= 1;
        if (currentResolution < 0){
            currentResolution = 8;
        }
    }

    public static void Activate(String name){
        if (name == "ChangeResolutionUp"){
            ChangeResolutionUp();
        }
        else if(name == "ChangeResolutionDown"){
            ChangeResolutionDown();
        }
    }
}
