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
        if (dictionaryOfItemsInGameByName.get(name) != null){
            return dictionaryOfItemsInGameByName.get(name);
        }
        else if(dictionaryOfItemsByName.get(name) != null){
            LoadItemIntoGameSession(name);
            if (dictionaryOfItemsInGameByName.get(name) != null){
                return dictionaryOfItemsInGameByName.get(name);
            }
            else{
                System.out.println("Error: Item doesnt exists in game");
            }
        }
        return null;
    }

    public static void SetItemToInventoryOfPlayer(String itemName, int amount){
        for(int i = 0; i < amount; i++){
            Player.itemsInventory.add(itemName);
        }
        //Player.itemsInventory.put(itemName, amount);
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
        if (dictionaryOfItemsInGameByName.get(itemName) == null){
            List<String> itemParameters = dictionaryOfItemsByName.get(itemName);
            dictionaryOfItemsInGameByName.put(itemName, CreateItemClass(itemName, itemParameters));
        }
        else{
            System.out.println("Error: item already exists in game");
        }

    }
}

abstract class Item {
    String id;
    String name;
    int worth;
    BufferedImage icon;
}

class OneHandedWeapon extends Item{
    float addDamage = 0;
    float addSpeed = 0;
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
                case "addDamage":
                    addDamage = Float.valueOf(parameterNameAndValue[1]);
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
    }
}
class TwoHandedWeapon extends Item{

    public TwoHandedWeapon(List<String> ListOfParameters, String itemName){
        name = itemName;
        try {
			icon = ImageIO.read(new File("res\\Items\\TwoHandedWeapon\\" + name + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
class Armor extends Item{
    public Armor(List<String> ListOfParameters, String itemName){
        name = itemName;
        try {
			icon = ImageIO.read(new File("res\\Items\\Armor\\" + name + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
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
    }
}
class Scroll extends Item{

    public Scroll(List<String> ListOfParameters, String itemName){
        name = itemName;
        try {
			icon = ImageIO.read(new File("res\\Items\\Scroll\\" + name + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}