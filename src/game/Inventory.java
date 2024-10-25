package game;
import java.util.List;
import java.util.ArrayList;

enum TypeOfInventory{
    CardsInventory,
    ItemsInventory
}

public class Inventory {
    private static List<InventoryCell> cellsOfItemsInventory = new ArrayList<InventoryCell>();
    public static int[] selectedSlotCordinate = new int[]{0,0};
    public static int[] endSlotCordinate= new int[]{0,0};
    public static boolean isLeftMouseDragging = false;
    public static boolean isRightMouseDragging = false;

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

        if (isLeftMouseDragging == true){
            LeftMouseDraggingTheItem();
        }
        else if(isRightMouseDragging){
            RightMouseDragging();
        }
    }

    private static void LeftMouseDraggingTheItem(){
        if(UserInputService.leftMousePress == false){
            isLeftMouseDragging = false;
            int maxAmountInStackOfItem = ItemsService.GetItemClass(Player.itemsInventory[selectedSlotCordinate[1]][selectedSlotCordinate[0]]).maxAmountInStack;
            if(Player.itemsInventory[selectedSlotCordinate[1]][selectedSlotCordinate[0]].equals(Player.itemsInventory[endSlotCordinate[1]][endSlotCordinate[0]]) && Player.itemsInventoryAmount[selectedSlotCordinate[1]][selectedSlotCordinate[0]] < maxAmountInStackOfItem && Player.itemsInventoryAmount[endSlotCordinate[1]][endSlotCordinate[0]] < maxAmountInStackOfItem){
                CombineTwoStacks(Player.itemsInventoryAmount[selectedSlotCordinate[1]][selectedSlotCordinate[0]]);
            }
            else{
                String savedItemName = Player.itemsInventory[selectedSlotCordinate[1]][selectedSlotCordinate[0]];
                int savedItemAmount = Player.itemsInventoryAmount[selectedSlotCordinate[1]][selectedSlotCordinate[0]];

                Player.itemsInventory[selectedSlotCordinate[1]][selectedSlotCordinate[0]] = Player.itemsInventory[endSlotCordinate[1]][endSlotCordinate[0]];
                Player.itemsInventoryAmount[selectedSlotCordinate[1]][selectedSlotCordinate[0]] = Player.itemsInventoryAmount[endSlotCordinate[1]][endSlotCordinate[0]];
                Player.itemsInventory[endSlotCordinate[1]][endSlotCordinate[0]] = savedItemName;
                Player.itemsInventoryAmount[endSlotCordinate[1]][endSlotCordinate[0]] = savedItemAmount;
            }
        }
    }

    private static void RightMouseDragging(){
        if(UserInputService.rightMousePress == false){
            isRightMouseDragging = false;
            int maxAmountInStackOfItem = ItemsService.GetItemClass(Player.itemsInventory[selectedSlotCordinate[1]][selectedSlotCordinate[0]]).maxAmountInStack;
            if(Player.itemsInventory[selectedSlotCordinate[1]][selectedSlotCordinate[0]].equals(Player.itemsInventory[endSlotCordinate[1]][endSlotCordinate[0]]) && Player.itemsInventoryAmount[endSlotCordinate[1]][endSlotCordinate[0]] < maxAmountInStackOfItem){
                CombineTwoStacks(Player.itemsInventoryAmount[selectedSlotCordinate[1]][selectedSlotCordinate[0]]/ 2);
            }
            else if(Player.itemsInventory[endSlotCordinate[1]][endSlotCordinate[0]] == null){
                int amount = Player.itemsInventoryAmount[selectedSlotCordinate[1]][selectedSlotCordinate[0]] / 2;
                if(amount == 0){
                    Player.itemsInventory[endSlotCordinate[1]][endSlotCordinate[0]] = Player.itemsInventory[selectedSlotCordinate[1]][selectedSlotCordinate[0]];
                    Player.itemsInventory[selectedSlotCordinate[1]][selectedSlotCordinate[0]] = null;
                    Player.itemsInventoryAmount[selectedSlotCordinate[1]][selectedSlotCordinate[0]] = 0;
                    Player.itemsInventoryAmount[endSlotCordinate[1]][endSlotCordinate[0]] = 1;
                }
                else{
                    Player.itemsInventory[endSlotCordinate[1]][endSlotCordinate[0]] = Player.itemsInventory[selectedSlotCordinate[1]][selectedSlotCordinate[0]];
                    Player.itemsInventoryAmount[selectedSlotCordinate[1]][selectedSlotCordinate[0]] -= amount;
                    Player.itemsInventoryAmount[endSlotCordinate[1]][endSlotCordinate[0]] = amount;
                }
            }
        }
    }

    private static void CombineTwoStacks(int amount){

    }

    private static void RightClickOnItemCell(){

    }

    private static void DivideTheStack(){

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

        if((UserInputService.leftMousePress == true && isMouseOver == true)){
            isPressed = true;
            if(Inventory.isLeftMouseDragging == false){
                Inventory.selectedSlotCordinate = new int[]{posX,posY};
                Inventory.isLeftMouseDragging = true;
            }
            else{
                Inventory.endSlotCordinate = new int[]{posX,posY};
            }
        }
        else if(UserInputService.rightMousePress == true && isMouseOver == true){
            isPressed = true;
            if(Inventory.isRightMouseDragging == false){
                Inventory.selectedSlotCordinate = new int[]{posX,posY};
                Inventory.isRightMouseDragging = true;
            }
            else{
                Inventory.endSlotCordinate = new int[]{posX,posY};
            }
        }
    }
}
