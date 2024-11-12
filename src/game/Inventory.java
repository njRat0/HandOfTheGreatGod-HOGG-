package game;
import java.util.List;
import java.awt.Color;
import java.util.ArrayList;

enum TypeOfInventory{
    CardsInventory,
    ItemsInventory
}

enum TypeOfInventoryCell{
    Empty,
    TrashBin,
    Helm,
    ChestArmor,
    Leggings,
    Boots,
    Weapon,
    Ring,
    Armlet,
    Necklace
}

public class Inventory {
    public static List<InventoryCell> cellsOfItemsInventory = new ArrayList<InventoryCell>();
    public static int[] selectedSlotCordinate = new int[]{0,0};
    public static InventoryCell selectedInventoryCell;
    public static InventoryCell endInventoryCell; 
    public static int[] endSlotCordinate= new int[]{0,0};
    public static boolean isLeftMouseDragging = false;
    public static boolean isRightMouseDragging = false;
    public static boolean isRightClick = false;
    public static String equippingParams_TypeNameOfCarringItem = null;

    public static boolean isRightMenuOpen = false;
    public static boolean isEquipedItemsWindowOpen = false;
    public static MyButton[] rightClickMenuButtons;

    public static void InitNewItemsInventoryGrid(){
        cellsOfItemsInventory.clear();
        for (int y = 2; y < Player.sizeOfItemsInventory[1]; y++){
            for (int x = 0; x < Player.sizeOfItemsInventory[0]; x++){
                cellsOfItemsInventory.add(new InventoryCell(x, y, 660 + x * 74, 60 + (y-2)*74 , 64, 64, TypeOfInventoryCell.Empty));   
            }
        }

        cellsOfItemsInventory.add(new InventoryCell(7,1,570 , 640, 64, 64, TypeOfInventoryCell.TrashBin));
        cellsOfItemsInventory.add(new InventoryCell(0,0,200 , 100, 64,64, TypeOfInventoryCell.Helm));
        cellsOfItemsInventory.add(new InventoryCell(1,0,200 , 170, 64,64, TypeOfInventoryCell.ChestArmor));
        cellsOfItemsInventory.add(new InventoryCell(2,0,200 , 240, 64,64, TypeOfInventoryCell.Leggings));
        cellsOfItemsInventory.add(new InventoryCell(3,0,200 , 310, 64,64, TypeOfInventoryCell.Boots));
        cellsOfItemsInventory.add(new InventoryCell(4,0,130 , 170, 64,64, TypeOfInventoryCell.Weapon));
        cellsOfItemsInventory.add(new InventoryCell(5,0,270 , 170, 64,64, TypeOfInventoryCell.Weapon));
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
            if(endInventoryCell.type == TypeOfInventoryCell.Empty){
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
            else if(endInventoryCell.type == TypeOfInventoryCell.TrashBin){
                if(!(selectedSlotCordinate[0] == endSlotCordinate[0] && selectedSlotCordinate[1] == endSlotCordinate[1])){
                    Player.itemsInventory[endSlotCordinate[1]][endSlotCordinate[0]] =  Player.itemsInventory[selectedSlotCordinate[1]][selectedSlotCordinate[0]];
                    Player.itemsInventoryAmount[endSlotCordinate[1]][endSlotCordinate[0]] =  Player.itemsInventoryAmount[selectedSlotCordinate[1]][selectedSlotCordinate[0]];
                    Player.itemsInventory[selectedSlotCordinate[1]][selectedSlotCordinate[0]] = null;
                    Player.itemsInventoryAmount[selectedSlotCordinate[1]][selectedSlotCordinate[0]] = 0;
                }
            }
            else{
                //System.out.println(ItemsService.GetItemClass(Player.itemsInventory[selectedSlotCordinate[1]][selectedSlotCordinate[0]]).equippingPart);
                if( ItemsService.GetItemClass(Player.itemsInventory[selectedSlotCordinate[1]][selectedSlotCordinate[0]]).equippingPart != null && endInventoryCell.type == ItemsService.GetItemClass(Player.itemsInventory[selectedSlotCordinate[1]][selectedSlotCordinate[0]]).equippingPart){
                    String savedItemName = Player.itemsInventory[selectedSlotCordinate[1]][selectedSlotCordinate[0]];
                    int savedItemAmount = Player.itemsInventoryAmount[selectedSlotCordinate[1]][selectedSlotCordinate[0]];
    
                    Player.itemsInventory[selectedSlotCordinate[1]][selectedSlotCordinate[0]] = Player.itemsInventory[endSlotCordinate[1]][endSlotCordinate[0]];
                    Player.itemsInventoryAmount[selectedSlotCordinate[1]][selectedSlotCordinate[0]] = Player.itemsInventoryAmount[endSlotCordinate[1]][endSlotCordinate[0]];
                    Player.itemsInventory[endSlotCordinate[1]][endSlotCordinate[0]] = savedItemName;
                    Player.itemsInventoryAmount[endSlotCordinate[1]][endSlotCordinate[0]] = savedItemAmount;
                }
                else{
                    System.out.println("Cannot equipe since its not correct type");
                }
            }
            

            selectedInventoryCell = null;
            endInventoryCell = null;
        }
    }

    private static void RightMouseDragging(){
        if(UserInputService.rightMousePress == false ){
            isRightMouseDragging = false;
            isRightClick = false;
            if( isRightClick == false && selectedSlotCordinate[0] == endSlotCordinate[0] && selectedSlotCordinate[1] == endSlotCordinate[1]){
                System.out.println("right menu");
                SetUpRightClickMenuOfItem();
                isRightClick = true;
                isRightMouseDragging = false;
                isLeftMouseDragging = false;
            }
            else if(endInventoryCell.type == TypeOfInventoryCell.Empty){
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
            else if(endInventoryCell.type == TypeOfInventoryCell.TrashBin){
                if(!(selectedSlotCordinate[0] == endSlotCordinate[0] && selectedSlotCordinate[1] == endSlotCordinate[1])){
                    int amount = Player.itemsInventoryAmount[selectedSlotCordinate[1]][selectedSlotCordinate[0]] / 2;
                    if(amount == 0){
                        Player.itemsInventory[1][7] =  Player.itemsInventory[selectedSlotCordinate[1]][selectedSlotCordinate[0]];
                        Player.itemsInventoryAmount[1][7] =  Player.itemsInventoryAmount[selectedSlotCordinate[1]][selectedSlotCordinate[0]];
                        Player.itemsInventoryAmount[selectedSlotCordinate[1]][selectedSlotCordinate[0]] = 0;
                        Player.itemsInventory[selectedSlotCordinate[1]][selectedSlotCordinate[0]] = null;
                    }
                    else{
                        Player.itemsInventory[1][7] =  Player.itemsInventory[selectedSlotCordinate[1]][selectedSlotCordinate[0]];
                        Player.itemsInventoryAmount[1][7] =  Player.itemsInventoryAmount[selectedSlotCordinate[1]][selectedSlotCordinate[0]] / 2;
                        Player.itemsInventoryAmount[selectedSlotCordinate[1]][selectedSlotCordinate[0]] = Player.itemsInventoryAmount[selectedSlotCordinate[1]][selectedSlotCordinate[0]]/2;
                    }
                    
                    //<---
                }
            }
            else{
                //System.out.println(ItemsService.GetItemClass(Player.itemsInventory[selectedSlotCordinate[1]][selectedSlotCordinate[0]]).equippingPart);
                if( ItemsService.GetItemClass(Player.itemsInventory[selectedSlotCordinate[1]][selectedSlotCordinate[0]]).equippingPart != null && endInventoryCell.type == ItemsService.GetItemClass(Player.itemsInventory[selectedSlotCordinate[1]][selectedSlotCordinate[0]]).equippingPart){
                    String savedItemName = Player.itemsInventory[selectedSlotCordinate[1]][selectedSlotCordinate[0]];
                    int savedItemAmount = Player.itemsInventoryAmount[selectedSlotCordinate[1]][selectedSlotCordinate[0]];
    
                    Player.itemsInventory[selectedSlotCordinate[1]][selectedSlotCordinate[0]] = Player.itemsInventory[endSlotCordinate[1]][endSlotCordinate[0]];
                    Player.itemsInventoryAmount[selectedSlotCordinate[1]][selectedSlotCordinate[0]] = Player.itemsInventoryAmount[endSlotCordinate[1]][endSlotCordinate[0]];
                    Player.itemsInventory[endSlotCordinate[1]][endSlotCordinate[0]] = savedItemName;
                    Player.itemsInventoryAmount[endSlotCordinate[1]][endSlotCordinate[0]] = savedItemAmount;
                }
                else{
                    System.out.println("Cannot equipe since its not correct type");
                }
            }

            selectedInventoryCell = null;
            endInventoryCell = null;
        }
    }

    private static void CombineTwoStacks(int amount){
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
        ArrayList<String> listOfAdditionalOptionsForRightMenu = new ArrayList<String>();
        int numberOfAdditionalOptionsForRightMenu = 0;
        if(selectedInventoryCell.type != TypeOfInventoryCell.TrashBin && selectedInventoryCell.type != TypeOfInventoryCell.Empty ){
            numberOfAdditionalOptionsForRightMenu += 1;
            listOfAdditionalOptionsForRightMenu.add("Uneqiupe");
        }
        rightClickMenuButtons = new MyButton[selectedItem.optionsForRightClickMenuInInventory.size() + numberOfAdditionalOptionsForRightMenu];
        int globalOffset = -(int)(rightClickMenuButtons.length*25)/2;
        isRightMenuOpen = true;
		for(int i = 0; i < rightClickMenuButtons.length; i++){
            if(i < numberOfAdditionalOptionsForRightMenu){
                rightClickMenuButtons[i] = new MyButton(TypeOfButton.RightClickMenuInInventory, listOfAdditionalOptionsForRightMenu.get(i));
                rightClickMenuButtons[i].name = listOfAdditionalOptionsForRightMenu.get(i);
            }
            else{
                rightClickMenuButtons[i] = new MyButton(TypeOfButton.RightClickMenuInInventory, selectedItem.optionsForRightClickMenuInInventory.get(i - numberOfAdditionalOptionsForRightMenu));
                rightClickMenuButtons[i].name = selectedItem.optionsForRightClickMenuInInventory.get(i - numberOfAdditionalOptionsForRightMenu);
            }
            rightClickMenuButtons[i].id=i;
            rightClickMenuButtons[i].SetBorderSize((int)(1));
            rightClickMenuButtons[i].colorBackground = new Color(125, 125, 125);
            rightClickMenuButtons[i].colorBorders = new Color(0, 0, 0);
            rightClickMenuButtons[i].colorOver = new Color(94, 94, 94);
            rightClickMenuButtons[i].colorClick = new Color(0, 0, 0);
            rightClickMenuButtons[i].SetSize( 50, 25);

            rightClickMenuButtons[i].SetLocation(selectedInventoryCell.locationOnScreenX + 70,selectedInventoryCell.locationOnScreenY + 35 + globalOffset);
			globalOffset += 25;
		}
    }
}

class InventoryCell{
    public int indexX;
    public int indexY;
    public int sizeX;
    public int sizeY;
    public int locationOnScreenX;
    public int locationOnScreenY;

    private boolean isMouseOver =  false;
    private boolean isPressed = false;
    public TypeOfInventoryCell type;
    
    public InventoryCell(int indexX,int indexY, int locationOnScreenX, int locationOnScreenY, int sizeX, int sizeY, TypeOfInventoryCell type){
        this.type = type;
        this.indexX = indexX;
        this.indexY = indexY;
        this.locationOnScreenX = locationOnScreenX;
        this.locationOnScreenY = locationOnScreenY;
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
                Inventory.selectedSlotCordinate = new int[]{indexX,indexY};
                Inventory.selectedInventoryCell = this;
                Inventory.endInventoryCell = this;
                Inventory.endSlotCordinate = new int[]{indexX,indexY};
                Inventory.isLeftMouseDragging = true;
            }
            else{
                Inventory.endSlotCordinate = new int[]{indexX,indexY};
                Inventory.endInventoryCell = this;
            }
        }
        else if(UserInputService.rightMousePress == true && isMouseOver == true){
            isPressed = true;
            if(Inventory.isRightMouseDragging == false){
                Inventory.selectedSlotCordinate = new int[]{indexX,indexY};
                Inventory.endSlotCordinate = new int[]{indexX,indexY};
                Inventory.isRightMouseDragging = true;
                Inventory.selectedInventoryCell = this;
                Inventory.endInventoryCell = this;
            }
            else{
                Inventory.endSlotCordinate = new int[]{indexX,indexY};
                Inventory.endInventoryCell = this;
            }
        }
    }
}
