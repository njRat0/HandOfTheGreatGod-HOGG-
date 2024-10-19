package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

enum TypeOfButton{
    Menu,
    Settings
}



class MyButton{
    //>>>class button
    public String description = "null";
    public BufferedImage icon;
    public boolean hasOwnFuctionality = false;
    public Method mouseClickMethod;
    public String nameOfFunction = null;
    //<<<

    //>>>menu button
    public int goTo = 0;
    //<<<

    public String name = "null";

    private boolean isMouseOver =  false;
    private boolean isPressed = false;
    public TypeOfButton type;

    private Color curColor = new Color(0,0,0);
    public Color colorOver = new Color(179, 250, 160);
    public Color colorClick = new Color(152, 184, 144);
    public Color colorBackground = new Color(30, 136, 56);
    public Color colorBorders = new Color(30, 136, 56);
    private int borderSize = 2;
    public int id;
    // public int locX = 0;
    // public int locY = 0;
    private Player player;

    private Rectangle rectangleOfButton = new Rectangle();

    public void SetLocation(int x, int y){
        rectangleOfButton.x = (int)(x * Settings.coeficientOfGameScreen);
        rectangleOfButton.y = (int)(y * Settings.coeficientOfGameScreen);
    }

    public void SetBorderSize(int value){
        borderSize = (int)(value * Settings.coeficientOfGameScreen);
    }

    public int GetLocationX(){
        return rectangleOfButton.x;
    }

    public int GetLocationY(){
        return rectangleOfButton.y;
    }

    public int GetSizeX(){
        return rectangleOfButton.width;
    }

    public int GetSizeY(){
        return rectangleOfButton.height;
    }

    public void SetSize(int x, int y){
        rectangleOfButton.width = (int)(x * Settings.coeficientOfGameScreen);
        rectangleOfButton.height = (int)(y * Settings.coeficientOfGameScreen);
    }
    //rectangleOfButton.x <= player.mouseX && player.mouseX <= rectangleOfButton.x + rectangleOfButton.width && rectangleOfButton.y <= GameLoop.mouseY && GameLoop.mouseY <= rectangleOfButton.y + rectangleOfButton.height
    public void update(){
        if(rectangleOfButton.contains(UserInputService.mouseX, UserInputService.mouseY)) {
            if(isMouseOver == false){
                isMouseOver = true;
                curColor = colorOver;
            }
        }
        else{
            if(isMouseOver == true){
                isMouseOver = false;
                curColor = colorBackground;
            }
        }

        if(UserInputService.mousePress == true && isMouseOver == true ){
            curColor = colorClick; 
            isPressed = true;
        }
        else{
            if(isMouseOver == true){
                curColor = colorOver;
                if(isPressed){
                    isPressed = false;
                    if (nameOfFunction != null){
                        switch (type) {
                            case Settings:
                                SettingButtons.Activate(nameOfFunction);
                                UserInputService.mousePress = false; 
                                break;
                            default:
                                System.out.println("Button type error");
                                UserInputService.mousePress = false; 
                                break;
                        }
                    }
                    else{
                        System.out.println("go to: " + goTo);
                        switch (type) {
                            case Menu:
                                GameLoop.curLayout = goTo;   
                                UserInputService.mousePress = false; 
                                break;
                            case Settings:
                                GameLoop.curLayout = goTo;   
                                UserInputService.mousePress = false; 
                                break;
                            default:
                                System.out.println("Button type error");
                                UserInputService.mousePress = false; 
                                break;
                        }
                    }
                }
            }
            else{
                curColor = colorBackground;
            }
        }
    }

    
    public MyButton(Player player, TypeOfButton type, String nameOfFunction){
        this.nameOfFunction = nameOfFunction;
        this.player = player;
        this.type = type;
        //this.setLayout(new FlowLayout());
        curColor = colorBackground;
        //GameFrame.add(this);
    }

    public void toDraw(Graphics2D g2d){
        g2d.setColor(colorBorders);
        g2d.fillRect(rectangleOfButton.x, rectangleOfButton.y, rectangleOfButton.width, rectangleOfButton.height);
        g2d.setColor(curColor);
        g2d.fillRect(rectangleOfButton.x + borderSize, rectangleOfButton.y + borderSize, rectangleOfButton.width - borderSize * 2 , rectangleOfButton.height - borderSize *2);
    }
}