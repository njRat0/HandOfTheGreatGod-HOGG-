package game;
import java.util.List;
import java.awt.Color;
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
    public static boolean isRightClick = false;

    public static boolean isRightMenuOpen = false;
    public static boolean isEquipedItemsWindowOpen = false;
    public static MyButton[] rightClickMenuButtons;

    public static void InitNewItemsInventoryGrid(){
        cellsOfItemsInventory.clear();
        for (int y = 1; y < Player.sizeOfItemsInventory[1]; y++){
            for (int x = 0; x < Player.sizeOfItemsInventory[0]; x++){
                cellsOfItemsInventory.add(new InventoryCell(x, y, 64, 64));   
            }
        }
    }

    public static void UpdateInventory(){
        for(InventoryCell cell: cellsOfItemsInventory){
            cell.Update();
        }

        if(isRightMenuOpen){
            for(MyButton button : rightClickMenuButtons){
                button.update();
            }

            if(UserInputService.rightMousePress){
                isRightMenuOpen = false;
            }
        }

        if(Player.itemsInventory[selectedSlotCordinate[1]][selectedSlotCordinate[0]] != null){
            
            if (isLeftMouseDragging == true){
                LeftMouseDraggingTheItem();
            }
            else if(isRightMouseDragging){
                RightMouseDragging();
            }
            else if(isRightClick){
                isRightClick = false;
            }
        }
        else{
            isLeftMouseDragging = false;
            isRightClick = false;
            isRightMouseDragging = false;
        }

    }

    private static void LeftMouseDraggingTheItem(){
        if(UserInputService.leftMousePress == false){
            isLeftMouseDragging = false;
            isRightClick = false;
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
            if( isRightClick == false && selectedSlotCordinate[0] == endSlotCordinate[0] && selectedSlotCordinate[1] == endSlotCordinate[1]){
                System.out.println("right menu");
                SetUpRightClickMenuOfItem();
                isRightClick = true;
                isRightMouseDragging = false;
                isLeftMouseDragging = false;
            }
            else{
                isRightClick = false;
                isRightMouseDragging = false;
                isLeftMouseDragging = false;
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
    }

    private static void CombineTwoStacks(int amount){
        //System.out.println("combine two items: " + selectedSlotCordinate[0] + " " + selectedSlotCordinate[1] + "; " + endSlotCordinate[0] + " " + endSlotCordinate[1]);
        if(selectedSlotCordinate[0] != endSlotCordinate[0] || selectedSlotCordinate[1] != endSlotCordinate[1]){
            int maxAmountInStackOfItem = ItemsService.GetItemClass(Player.itemsInventory[endSlotCordinate[1]][endSlotCordinate[0]]).maxAmountInStack;
            int sAmount = maxAmountInStackOfItem - Player.itemsInventoryAmount[endSlotCordinate[1]][endSlotCordinate[0]];
            if (sAmount >= amount){
                System.out.println("Full addition");
                Player.itemsInventoryAmount[endSlotCordinate[1]][endSlotCordinate[0]] += amount;
                Player.itemsInventoryAmount[selectedSlotCordinate[1]][selectedSlotCordinate[0]] -= amount;
                if(Player.itemsInventoryAmount[selectedSlotCordinate[1]][selectedSlotCordinate[0]] == 0){
                    Player.itemsInventory[selectedSlotCordinate[1]][selectedSlotCordinate[0]] = null;
                }
            }
            else{
                System.out.println("half addition");
                Player.itemsInventoryAmount[endSlotCordinate[1]][endSlotCordinate[0]] += sAmount;
                Player.itemsInventoryAmount[selectedSlotCordinate[1]][selectedSlotCordinate[0]] -= sAmount;
                if(Player.itemsInventoryAmount[selectedSlotCordinate[1]][selectedSlotCordinate[0]] == 0){
                    Player.itemsInventory[selectedSlotCordinate[1]][selectedSlotCordinate[0]] = null;
                }
            }
        }
    }

    private static void SetUpRightClickMenuOfItem(){
        Item selectedItem = ItemsService.GetItemClass(Player.itemsInventory[selectedSlotCordinate[1]][selectedSlotCordinate[0]]);
        rightClickMenuButtons = new MyButton[selectedItem.optionsForRightClickMenuInInventory.size()];
        int globalOffset = -(int)(rightClickMenuButtons.length*25)/2;
        isRightMenuOpen = true;
		for(int i = 0; i < rightClickMenuButtons.length; i++){
			rightClickMenuButtons[i] = new MyButton(TypeOfButton.RightClickMenuInInventory, selectedItem.optionsForRightClickMenuInInventory.get(i));
			rightClickMenuButtons[i].id=i;
			rightClickMenuButtons[i].SetBorderSize((int)(1));
			rightClickMenuButtons[i].colorBackground = new Color(125, 125, 125);
			rightClickMenuButtons[i].colorBorders = new Color(0, 0, 0);
			rightClickMenuButtons[i].colorOver = new Color(94, 94, 94);
			rightClickMenuButtons[i].colorClick = new Color(0, 0, 0);
			rightClickMenuButtons[i].SetSize( 50, 25);
            rightClickMenuButtons[i].name = selectedItem.optionsForRightClickMenuInInventory.get(i);
			rightClickMenuButtons[i].SetLocation(735 + selectedSlotCordinate[0] * 74,selectedSlotCordinate[1] * 74 + globalOffset + 92);
			globalOffset += 25;
		}
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
        locationOnScreenY = 60 + (posY-1) * 74;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public void Update(){
        if(locationOnScreenX <= UserInputService.mouseX && locationOnScreenY <= UserInputService.mouseY && locationOnScreenX + sizeX >= UserInputService.mouseX && locationOnScreenY + sizeY >= UserInputService.mouseY) {
            if(isMouseOver == false){
                isMouseOver = true;
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
                Inventory.endSlotCordinate = new int[]{posX,posY};
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
                Inventory.endSlotCordinate = new int[]{posX,posY};
                Inventory.isRightMouseDragging = true;
            }
            else{
                Inventory.endSlotCordinate = new int[]{posX,posY};
            }
        }
    }
}
