package game;
import java.util.List;
import java.awt.Color;
import java.util.ArrayList;

enum TypeOfInventory{
    CardsInventory,
    ItemsInventory
}

enum TypeOfInventoryCell{
    InventoryCell,
    EquippingCell,
    TrashBinCell
}

public class Inventory {
    private static List<InventoryCell> cellsOfItemsInventory = new ArrayList<InventoryCell>();
    public static int[] selectedSlotCordinate = new int[]{0,0};
    public static int[] endSlotCordinate= new int[]{0,0};
    public static int selectedSlotEquippingCell = -1;
    public static int endSlotEquippingCell = -1;
    public static boolean selectedSlotTrashBinCell = false;
    public static boolean endSlotTrashBinCell = false;
    public static boolean isLeftMouseDragging = false;
    public static boolean isRightMouseDragging = false;
    public static boolean isRightClick = false;
    public static String equippingParams_TypeNameOfCarringItem = null;

    public static String[] listOfNamesOfEquippingItemsCell = new String[]{
        "Helm",
        "ChestArmor",
        "Leggings",
        "Boots",
        "Weapon",
        "Weapon",
        "Ring",
        "Ring",
        "Armlet",
        "Necklace"
    };

    public static boolean isRightMenuOpen = false;
    public static boolean isEquipedItemsWindowOpen = false;
    public static MyButton[] rightClickMenuButtons;

    public static void InitNewItemsInventoryGrid(){
        cellsOfItemsInventory.clear();
        for (int y = 0; y < Player.sizeOfItemsInventory[1]; y++){
            for (int x = 0; x < Player.sizeOfItemsInventory[0]; x++){
                cellsOfItemsInventory.add(new InventoryCell(x, y, 64, 64, TypeOfInventoryCell.InventoryCell));   
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
            if(selectedSlotTrashBinCell || endSlotTrashBinCell){
                if(selectedSlotTrashBinCell != endSlotTrashBinCell){
                    if(selectedSlotTrashBinCell == true){
                        if(Player.TrashBinCell.equals(Player.itemsInventory[endSlotCordinate[1]][endSlotCordinate[0]]) && ItemsService.GetItemClass(Player.TrashBinCell).maxAmountInStack != 1){
                            CombineTwoStacks(Player.TrashBinCellAmount);
                        }
                        else{
                            String savedItemName = Player.TrashBinCell;
                            int savedItemAmount = Player.TrashBinCellAmount;

                            Player.TrashBinCell= Player.itemsInventory[endSlotCordinate[1]][endSlotCordinate[0]];
                            Player.TrashBinCellAmount= Player.itemsInventoryAmount[endSlotCordinate[1]][endSlotCordinate[0]];
                            Player.itemsInventory[endSlotCordinate[1]][endSlotCordinate[0]] = savedItemName;
                            Player.itemsInventoryAmount[endSlotCordinate[1]][endSlotCordinate[0]] = savedItemAmount;
                        }
                    }
                    else{
                        int maxAmountInStackOfItem = ItemsService.GetItemClass(Player.itemsInventory[selectedSlotCordinate[1]][selectedSlotCordinate[0]]).maxAmountInStack;
                        if(Player.TrashBinCell.equals(Player.itemsInventory[selectedSlotCordinate[1]][selectedSlotCordinate[0]]) && Player.itemsInventoryAmount[endSlotCordinate[1]][endSlotCordinate[0]] < maxAmountInStackOfItem){
                            CombineTwoStacks(Player.TrashBinCellAmount);
                        }
                        else{
                            Player.TrashBinCell= Player.itemsInventory[endSlotCordinate[1]][endSlotCordinate[0]];
                            Player.TrashBinCellAmount= Player.itemsInventoryAmount[endSlotCordinate[1]][endSlotCordinate[0]];
                            Player.itemsInventory[endSlotCordinate[1]][endSlotCordinate[0]] = savedItemName;
                            Player.itemsInventoryAmount[endSlotCordinate[1]][endSlotCordinate[0]] = savedItemAmount;
                        }
                    }
                }
            }
            else if(selectedSlotEquippingCell != -1 || endSlotEquippingCell != -1){

            }
            else{

            }
            
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

            selectedSlotTrashBinCell = false;
            endSlotTrashBinCell = false;
            selectedSlotEquippingCell = -1;
            endSlotEquippingCell = -1;
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
        if(selectedSlotTrashBinCell || endSlotTrashBinCell){
            if(selectedSlotTrashBinCell != endSlotTrashBinCell){
                if(selectedSlotTrashBinCell == true){
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
                else{
                    
                }
            }
        }
        else if((selectedSlotEquippingCell != -1 || endSlotEquippingCell != -1) && !(selectedSlotEquippingCell == endSlotEquippingCell)){

        }
        else if(selectedSlotCordinate[0] != endSlotCordinate[0] || selectedSlotCordinate[1] != endSlotCordinate[1]){
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
    public TypeOfInventoryCell type;
    public int equipping_index = -1;

    private boolean isMouseOver =  false;
    private boolean isPressed = false;
    
    public InventoryCell(int posX,int posY, int sizeX, int sizeY, TypeOfInventoryCell type){
        this.type = type;
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
            switch (type) {
                case InventoryCell:
                    if(Inventory.isLeftMouseDragging == false){
                        Inventory.selectedSlotCordinate = new int[]{posX,posY};
                        Inventory.endSlotCordinate = new int[]{posX,posY};
                        Inventory.isLeftMouseDragging = true;
                    }
                    else{
                        Inventory.endSlotCordinate = new int[]{posX,posY};
                        Inventory.endSlotEquippingCell = -1;
                        Inventory.endSlotTrashBinCell = false;
                    }
                    break;
                case EquippingCell:
                    if(Inventory.isLeftMouseDragging == false){
                        Inventory.selectedSlotEquippingCell = equipping_index;
                        Inventory.endSlotEquippingCell = equipping_index;
                        Inventory.isLeftMouseDragging = true;
                    }
                    else{
                        Inventory.endSlotEquippingCell = equipping_index;
                        Inventory.endSlotTrashBinCell = false;
                    }
                    break;
                case TrashBinCell:
                    if(Inventory.isLeftMouseDragging == false){
                        Inventory.selectedSlotTrashBinCell = true;
                        Inventory.endSlotTrashBinCell = true;
                        Inventory.isLeftMouseDragging = true;
                    }
                    else{
                        Inventory.endSlotEquippingCell = -1;
                        Inventory.endSlotTrashBinCell = true;
                    }
                    break;
                default:
                    System.out.println("Error: the type of cell do not exist");
                    break;
            }
        }
        else if(UserInputService.rightMousePress == true && isMouseOver == true){
            isPressed = true;
            switch (type) {
                case InventoryCell:
                    if(Inventory.isRightMouseDragging == false){
                        Inventory.selectedSlotCordinate = new int[]{posX,posY};
                        Inventory.endSlotCordinate = new int[]{posX,posY};
                        Inventory.isRightMouseDragging = true;
                    }
                    else{
                        Inventory.endSlotCordinate = new int[]{posX,posY};
                        Inventory.endSlotEquippingCell = -1;
                        Inventory.endSlotTrashBinCell = false;
                    }
                    break;
                case EquippingCell:
                    if(Inventory.isRightMouseDragging == false){
                        Inventory.selectedSlotEquippingCell = equipping_index;
                        Inventory.endSlotEquippingCell = equipping_index;
                        Inventory.isRightMouseDragging = true;
                    }
                    else{
                        Inventory.endSlotEquippingCell = equipping_index;
                        Inventory.endSlotTrashBinCell = false;
                    }
                    break;
                case TrashBinCell:
                    if(Inventory.isRightMouseDragging == false){
                        Inventory.selectedSlotTrashBinCell = true;
                        Inventory.endSlotTrashBinCell = true;
                        Inventory.isRightMouseDragging = true;
                    }
                    else{
                        Inventory.endSlotEquippingCell = -1;
                        Inventory.endSlotTrashBinCell = true;
                    }
                    break;
                default:
                    System.out.println("Error: the type of cell do not exist");
                    break;
            }
        }
    }
}
