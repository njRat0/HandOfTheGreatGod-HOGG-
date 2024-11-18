package game;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

import javax.swing.Icon;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

enum TYPES_OF_ITEMS{
    OneHandedWeapon,
    TwoHandedWeapon,
    Armor,
    Consumable,
    Scroll
}


public class ItemsService {
    private static File itemsListData = new File("data\\ItemsList.txt");
    private static Dictionary<String, List<String>> dictionaryOfItemsByName = new Hashtable<String, List<String>>();
    private static Dictionary<Integer, String> dictionaryOfItemsNameByWorth = new Hashtable<Integer,String>();
    private static Dictionary<String, Item> dictionaryOfItemsInGameByName = new Hashtable<String, Item>();
    

    public static void Init(){
        try {
            List<String> stringListOfItemsData = Files.readAllLines(itemsListData.toPath());
            boolean isNextName = true;
            String nameOfItem = null;
            List<String> itemParameters = new ArrayList<String>();
            for(String line : stringListOfItemsData){
                line = line.replaceAll(" ", "");
                String[] parametersNameAndValue = line.split(":");
                if(isNextName){
                    isNextName = false;
                    nameOfItem = line.substring(0, line.length()-1);
                    continue;
                }
                
                if(parametersNameAndValue[0].equals("worth")){
                    dictionaryOfItemsNameByWorth.put(Integer.valueOf(parametersNameAndValue[1]), nameOfItem);
                }

                if(line.equals("}")){
                    dictionaryOfItemsByName.put(nameOfItem, itemParameters);
                    itemParameters = new ArrayList<String>();
                    isNextName = true;
                    nameOfItem = null;
                    continue;
                }
                
                itemParameters.add(line);
            }
            System.out.println(String.join(", ", dictionaryOfItemsByName.get("SmallRedSoul")));
        } catch (IOException e) {
            System.out.println("Critical ERROR: items data file wasnt found");
        }
    }

    public static Item GetItemClass(String name){ 
        int iStringOfPhysicalUpdgrate = name.indexOf("+");
        int iStringOfMagicUpdgrate = name.indexOf("*");
        
        if( iStringOfPhysicalUpdgrate != -1){
          
        }
        else if(iStringOfMagicUpdgrate != -1){
            
        }

        Item item = dictionaryOfItemsInGameByName.get(name);
        if (item != null){
            return item;
        }
        else{
            LoadItemIntoGameSession(name);
            item = dictionaryOfItemsInGameByName.get(name);
                
            if(item == null){
                System.out.println("Error: Item doesnt exists in game");
            }
            else{
                return item;
            }
        }
        return null;
    }

    public static void EquippingItem(boolean wasAlreadyMoved){

    }

    public static void AddItemToInventoryOfPlayer(String itemName, int amount){
        boolean wasItemAdd = false;
        Item classOfItem = GetItemClass(itemName);
        System.out.println("add");
        boolean isEnd = false;
        
        if(classOfItem.maxAmountInStack > 1){
            for(int y = 2; y < Player.sizeOfItemsInventory[1]; y++){
                for(int x = 0; x < Player.sizeOfItemsInventory[0]; x++){
                    if(Player.itemsInventory[y][x] != null && Player.itemsInventory[y][x].equals(itemName)){
                        if(Player.itemsInventoryAmount[y][x] < classOfItem.maxAmountInStack){
                            if(Player.itemsInventoryAmount[y][x] + amount > classOfItem.maxAmountInStack){
                                System.out.println("add3");
                                int addValue = classOfItem.maxAmountInStack - Player.itemsInventoryAmount[y][x];
                                Player.itemsInventoryAmount[y][x] += addValue;
                                amount -= addValue;
                            }
                            else{
                                wasItemAdd = true;
                                Player.itemsInventoryAmount[y][x] += amount;
                                isEnd = true;
                                break;
                            }
                        }
                    }
                }
                if(isEnd == true){
                    break;
                }
            }
        }

        if (wasItemAdd == false){
            isEnd = false;
            for(int y = 2; y < Player.itemsInventory.length; y++){
                for(int x = 0; x < Player.itemsInventory[0].length; x++){
                    if(Player.itemsInventory[y][x] == null){
                        if(amount > classOfItem.maxAmountInStack){
                            amount -= classOfItem.maxAmountInStack;
                            Player.itemsInventory[y][x] = itemName;
                            Player.itemsInventoryAmount[y][x] += classOfItem.maxAmountInStack;
                        }
                        else{
                            wasItemAdd = true;
                            Player.itemsInventory[y][x] = itemName;
                            Player.itemsInventoryAmount[y][x] += amount;
                            isEnd = true;
                            break;
                        }
                    }
                }
                if(isEnd == true){
                    break;
                }
            }
        }
        
        if(wasItemAdd == false){
            System.out.println("Inventory of player is Full");
        }
    }

    private static Item CreateItemClass(String itemName,List<String> itemParameters){
        String typeOfItem = null;
        Item item;
        for(String line : itemParameters){
            String[] parametersNameAndValue = line.split(":");
            System.out.println(parametersNameAndValue[0]);
            if(parametersNameAndValue[0].equals("type")){
                typeOfItem = parametersNameAndValue[1];
                break;
            }
        }

        switch (typeOfItem) {
            case "OneHandedWeapon":
                item = new OneHandedWeapon(itemParameters, itemName);
                return item;
            case "TwoHandedWeapon":
                item = new TwoHandedWeapon(itemParameters, itemName);
                return item;
            case "Armor":
                item = new Armor(itemParameters, itemName);
                return item;
            case "Consumable":
                item = new Consumable(itemParameters, itemName);
                return item;
            case "Scroll":
                item = new Scroll(itemParameters, itemName);
                return item;
            default:
                System.out.println("Error: game doesnt contains class of item: " + typeOfItem);
                break;
        }
        return null;
    }

    public static void LoadItemIntoGameSession(String itemName){
        Item item = dictionaryOfItemsInGameByName.get(itemName);
        if (item == null){
            List<String> itemParameters = dictionaryOfItemsByName.get(itemName);
            dictionaryOfItemsInGameByName.put(itemName, CreateItemClass(itemName, itemParameters));
        }
        else{
            System.out.println("Error: item already exists in game");
        }
    }

    public static void ActivateOptionFromRightClickMenuInInventory(String nameOfOption){
        switch (nameOfOption) {
            case "Equipe":
                System.out.println("Equipe");
                break;
            case "Unequipe":
                
                break;
            case "Use":
                System.out.println("Use");
                break;
            case "Sell":
                System.out.println("Sell");
                break;
            case "Divide":
                System.out.println("Divide");
                break;
        
            default:
            System.out.println("Error: list doesnt consist this option");
                break;
        }
    }
}

abstract class Item {
    String id;
    String name;
    int worth;
    float rarity = 1;
    BufferedImage icon;
    int maxAmountInStack = 1;
    public TypeOfInventoryCell equippingPart;

    ArrayList<String> optionsForRightClickMenuInInventory = new ArrayList<String>(){};

    Item(){
        optionsForRightClickMenuInInventory.add("Throw away");
    }

}

class OneHandedWeapon extends Item{
    float addPhysicalDamage = 0;
    float addMagicDamage = 0;
    float addSpeed = 0;
    float addPhysicalArmor = 0;
    float addMagicArmor = 0;
    String[] cards;

    public OneHandedWeapon(List<String> ListOfParameters, String itemName){
        name = itemName;
        try {
			icon = ImageIO.read(new File("res\\Items\\OneHandedWeapon\\" + name + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
        for(String line : ListOfParameters){
            String[] parameterNameAndValue = line.split(":");
            switch (parameterNameAndValue[0]) {
                case "id":
                    id = parameterNameAndValue[1];
                    break;
                case "worth":
                    worth = Integer.valueOf(parameterNameAndValue[1]);
                    break;
                case "equippingPart":
                    equippingPart = TypeOfInventoryCell.valueOf(parameterNameAndValue[1]);
                    break;
                case "addPhysicalDamage":
                    addPhysicalDamage = Float.valueOf(parameterNameAndValue[1]);
                    break;
                case "addMagicDamage":
                    addMagicDamage = Float.valueOf(parameterNameAndValue[1]);
                    break;
                case "addPhysicalArmor":
                    addPhysicalArmor = Float.valueOf(parameterNameAndValue[1]);
                    break;
                case "addMagicArmor":
                    addMagicArmor = Float.valueOf(parameterNameAndValue[1]);
                    break;
                case "rarity":
                    rarity = Float.valueOf(parameterNameAndValue[1]);
                    break;
                case "addSpeed":
                    addSpeed = Float.valueOf(parameterNameAndValue[1]);
                    break;
                case "cards":
                    cards = parameterNameAndValue[1].split(", ");
                    break;
                default:
                    break;
            }
        }

        optionsForRightClickMenuInInventory.add("Equipe");
        optionsForRightClickMenuInInventory.add("Sell");
    }
}
class TwoHandedWeapon extends Item{
    float addPhysicalDamage = 0;
    float addMagicDamage = 0;
    float addSpeed = 0;
    float addPhysicalArmor = 0;
    float addMagicArmor = 0;
    String[] cards;

    public TwoHandedWeapon(List<String> ListOfParameters, String itemName){
        name = itemName;
        try {
			icon = ImageIO.read(new File("res\\Items\\TwoHandedWeapon\\" + name + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
        for(String line : ListOfParameters){
            String[] parameterNameAndValue = line.split(":");
            switch (parameterNameAndValue[0]) {
                case "id":
                    id = parameterNameAndValue[1];
                    break;
                case "worth":
                    worth = Integer.valueOf(parameterNameAndValue[1]);
                    break;
                case "equippingPart":
                    equippingPart = TypeOfInventoryCell.valueOf(parameterNameAndValue[1]);
                    break;
                case "addPhysicalDamage":
                    addPhysicalDamage = Float.valueOf(parameterNameAndValue[1]);
                    break;
                case "addMagicDamage":
                    addMagicDamage = Float.valueOf(parameterNameAndValue[1]);
                    break;
                case "addPhysicalArmor":
                    addPhysicalArmor = Float.valueOf(parameterNameAndValue[1]);
                    break;
                case "addMagicArmor":
                    addMagicArmor = Float.valueOf(parameterNameAndValue[1]);
                    break;
                case "rarity":
                    rarity = Float.valueOf(parameterNameAndValue[1]);
                    break;
                case "addSpeed":
                    addSpeed = Float.valueOf(parameterNameAndValue[1]);
                    break;
                case "cards":
                    cards = parameterNameAndValue[1].split(", ");
                    break;
                default:
                    break;
            }
        }

        optionsForRightClickMenuInInventory.add("Equipe");
        optionsForRightClickMenuInInventory.add("Sell");
    }
}
class Armor extends Item{
    float addPhysicalDamage = 0;
    float addMagicDamage = 0;
    float addSpeed = 0;
    float addPhysicalArmor = 0;
    float addMagicArmor = 0;
    String[] cards;
    
    public Armor(List<String> ListOfParameters, String itemName){
        name = itemName;
        try {
			icon = ImageIO.read(new File("res\\Items\\Armor\\" + name + ".png"));
            for(String line : ListOfParameters){
                String[] parameterNameAndValue = line.split(":");
                switch (parameterNameAndValue[0]) {
                    case "id":
                        id = parameterNameAndValue[1];
                        break;
                    case "worth":
                        worth = Integer.valueOf(parameterNameAndValue[1]);
                        break;
                    case "equippingPart":
                        equippingPart = TypeOfInventoryCell.valueOf(parameterNameAndValue[1]);
                        break;
                    case "addPhysicalDamage":
                        addPhysicalDamage = Float.valueOf(parameterNameAndValue[1]);
                        break;
                    case "addMagicDamage":
                        addMagicDamage = Float.valueOf(parameterNameAndValue[1]);
                        break;
                    case "addPhysicalArmor":
                        addPhysicalArmor = Float.valueOf(parameterNameAndValue[1]);
                        break;
                    case "addMagicArmor":
                        addMagicArmor = Float.valueOf(parameterNameAndValue[1]);
                        break;
                    case "rarity":
                        rarity = Float.valueOf(parameterNameAndValue[1]);
                        break;
                    case "addSpeed":
                        addSpeed = Float.valueOf(parameterNameAndValue[1]);
                        break;
                    case "cards":
                        cards = parameterNameAndValue[1].split(", ");
                        break;
                    default:
                        break;
                }
            }
		} catch (IOException e) {
			e.printStackTrace();
		}

        optionsForRightClickMenuInInventory.add("Equipe");
        optionsForRightClickMenuInInventory.add("Sell");
    }
}
class Consumable extends Item{
    int addCurHP = 0;
    int addMaxHP = 0;
    int addCurMP = 0;
    int addMaxMP = 0;
    int addSpeed = 0;
    int duration = 0;
    boolean isGradual=false;
    float speedCost = 0;

    public Consumable(List<String> ListOfParameters, String itemName){
        name = itemName;
        maxAmountInStack = 16;
        try {
			icon = ImageIO.read(new File("res\\Items\\Consumable\\" + name + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
        for(String line : ListOfParameters){
            String[] parameterNameAndValue = line.split(":");
            switch (parameterNameAndValue[0]) {
                case "id":
                    id = parameterNameAndValue[1];
                    break;
                case "worth":
                    worth = Integer.valueOf(parameterNameAndValue[1]);
                    break;
                case "addCurHP":
                    addCurHP = Integer.valueOf(parameterNameAndValue[1]);
                    break;
                case "addMaxHP":
                    addMaxHP = Integer.valueOf(parameterNameAndValue[1]);
                    break;
                case "addCurMP":
                    addCurMP = Integer.valueOf(parameterNameAndValue[1]);
                    break;
                case "addMaxMP":
                    addMaxMP = Integer.valueOf(parameterNameAndValue[1]);
                    break;
                case "addSpeed":
                    addSpeed = Integer.valueOf(parameterNameAndValue[1]);
                    break;
                case "duration":
                    duration = Integer.valueOf(parameterNameAndValue[1]);
                    break;
                case "isGradual":
                    isGradual = Boolean.valueOf(parameterNameAndValue[1]);
                    break;
                case "speedCost":
                    speedCost = Float.valueOf(parameterNameAndValue[1]);
                    break;
                default:
                    break;
            }
        }

        optionsForRightClickMenuInInventory.add("Use");
        optionsForRightClickMenuInInventory.add("Sell");
        optionsForRightClickMenuInInventory.add("Divide");
    }
}
class Scroll extends Item{

    public Scroll(List<String> ListOfParameters, String itemName){
        name = itemName;
        maxAmountInStack = 5;
        try {
			icon = ImageIO.read(new File("res\\Items\\Scroll\\" + name + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

        optionsForRightClickMenuInInventory.add("Use");
        optionsForRightClickMenuInInventory.add("Sell");
        optionsForRightClickMenuInInventory.add("Divide");
    }
}