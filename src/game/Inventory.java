package game;
import java.util.List;
import java.util.ArrayList;

enum TypeOfInventory{
    CardsInventory,
    ItemsInventory
}

public class Inventory {
    private static List<InventoryCell> cellsOfItemsInventory = new ArrayList<InventoryCell>();
    public static int[] selectedItemCordinate = new int[]{0,0};
    public static int[] endItemOfDragging = new int[]{0,0};
    public static boolean isDraging = false;

    public static void InitNewItemsInventoryGrid(){
        cellsOfItemsInventory.clear();
        for (int y = 0; y < Player.sizeOfItemsInventory[1]; y++){
            for (int x = 0; x < Player.sizeOfItemsInventory[0]; x++){
                cellsOfItemsInventory.add(new InventoryCell(x, y, 64, 64));   
            }
        }
    }

    public static void UpdateInventory(){
        for(InventoryCell cell: cellsOfItemsInventory){
            cell.Update();
        }

        if (isDraging == true){
            DraggingTheItem();
        }
    }

    private static void DraggingTheItem(){
        if(UserInputService.leftMousePress == false){
            isDraging = false;
            String savedItemName = Player.itemsInventory[selectedItemCordinate[1]][selectedItemCordinate[0]];
            int savedItemAmount = Player.itemsInventoryAmount[selectedItemCordinate[1]][selectedItemCordinate[0]];

            Player.itemsInventory[selectedItemCordinate[1]][selectedItemCordinate[0]] = Player.itemsInventory[endItemOfDragging[1]][endItemOfDragging[0]];
            Player.itemsInventoryAmount[selectedItemCordinate[1]][selectedItemCordinate[0]] = Player.itemsInventoryAmount[endItemOfDragging[1]][endItemOfDragging[0]];
            Player.itemsInventory[endItemOfDragging[1]][endItemOfDragging[0]] = savedItemName;
            Player.itemsInventoryAmount[endItemOfDragging[1]][endItemOfDragging[0]] = savedItemAmount;
        }
    }

    public static void SwapTwoItems(){

    }
}

class InventoryCell{
    private int posX;
    private int posY;
    private int sizeX;
    private int sizeY;
    private int locationOnScreenX;
    private int locationOnScreenY;

    private boolean isMouseOver =  false;
    private boolean isPressed = false;
    
    public InventoryCell(int posX,int posY, int sizeX, int sizeY){
        this.posX = posX;
        this.posY = posY;
        locationOnScreenX = 660 + posX * 74;
        locationOnScreenY = 60 + posY * 74;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public void Update(){
        if(locationOnScreenX <= UserInputService.mouseX && locationOnScreenY <= UserInputService.mouseY && locationOnScreenX + sizeX >= UserInputService.mouseX && locationOnScreenY + sizeY >= UserInputService.mouseY) {
            if(isMouseOver == false){
                isMouseOver = true;
                System.out.println("Mouse over");
            }
        }
        else{
            if(isMouseOver == true){
                isMouseOver = false;
                isPressed = false;
            }
        }

        if(UserInputService.leftMousePress == true && isMouseOver == true ){
            isPressed = true;
            if(Inventory.isDraging == false){
                Inventory.selectedItemCordinate = new int[]{posX,posY};
                Inventory.isDraging = true;
            }
            else{
                Inventory.endItemOfDragging = new int[]{posX,posY};
            }
        }
        
        if(isMouseOver == true){
            if(isPressed){
                isPressed = false;
            }
        }
    }
}
