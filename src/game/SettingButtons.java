package game;

public class SettingButtons {
    private static final String[] SCREEN_RESOLUTION_LIST = new String[]{
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
    private static int currentResolution = 0;

    private static void ChangeResolutionUp(){
        currentResolution += 1;
        if (currentResolution >8){
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
