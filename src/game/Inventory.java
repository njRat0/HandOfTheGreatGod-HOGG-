package game;
import java.util.List;
import java.util.ArrayList;

enum TypeOfInventory{
    CardsInventory,
    ItemsInventory
}

public class Inventory {
    private static List<InventoryCell> cellsOfItemsInventory = new ArrayList<InventoryCell>();
    
    public static void InitNewItemsInventoryGrid(){
        for (int y = 0; y < Player.sizeOfItemsInventory[1]; y++){
            for (int x = 0; x < Player.sizeOfItemsInventory[0]; x++){
                cellsOfItemsInventory.add(new InventoryCell(x, y, x, y));   
            }
        }
    }

    public static void UpdateInventory(){

    }
}

class InventoryCell{
    private int posX;
    private int posY;
    private int sizeX;
    private int sizeY;
    private int locationOnScreenX;
    private int locationOnScreenY;
    
    public InventoryCell(int posX,int posY, int sizeX, int sizeY){
        this.posX = posX;
        this.posY = posY;
        locationOnScreenX = 660 + posX * 74;
        locationOnScreenY = 60 + posY * 74;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public void Update(){
        // if(locationOnScreenX <= UserInputService.mouseX && locationOnScreenY <= UserInputService.mouseY && locationOnScreenX + sizeX >= UserInputService.mouseX && locationOnScreenY + sizeY >= UserInputService.mouseY) {
        //     if(isMouseOver == false){
        //         isMouseOver = true;
        //         curColor = colorOver;
        //     }
        // }
        // else{
        //     if(isMouseOver == true){
        //         isMouseOver = false;
        //         curColor = colorBackground;
        //     }
        // }

        // if(UserInputService.leftMousePress == true && isMouseOver == true ){
        //     curColor = colorClick; 
        //     isPressed = true;
        // }
    }
}
