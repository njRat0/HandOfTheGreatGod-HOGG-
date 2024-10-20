package game;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.awt.image.BufferedImage;

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
    private static Dictionary<Integer, List<String>> dictionaryOfItemsNameByWorth = new Hashtable<Integer,List<String>>();
    private static Dictionary<String, Item> dictionaryOfItemsInGameByName = new Hashtable<String, Item>();

    public static void Init(){
        try {
            List<String> stringListOfItemsData = Files.readAllLines(itemsListData.toPath());
            boolean isNextName = true;
            String nameOfItem = null;
            List<String> itemParameters = new ArrayList<String>();
            for(String line : stringListOfItemsData){
                if(isNextName){
                    isNextName = false;
                    nameOfItem = line.substring(0, line.length()-1);
                    continue;
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
            CreateDictionaryOfItemsNameByWorth();
            System.out.println(String.join(", ", dictionaryOfItemsByName.get("SmallRedSoul")));
        } catch (IOException e) {
            System.out.println("Critical ERROR: items data file wasnt found");
        }
    }

    public static void GetItemsById(int id){

    }

    public static void GetItemsByName(String name){

    }

    public static void SetItemToInventoryOfPlayer(){

    }

    private static void CreateDictionaryOfItemsNameByWorth(){
        // System.out.println("works123");
        // for(String nameOfItem : ){
        //     System.out.println(nameOfItem);
        // }
    }

    private static Item CreateItemClass(String itemName,List<String> itemParameters){
        String typeOfItem = null;
        Item item;
        for(String line : itemParameters){
            String[] parametersNameAndValue = line.split(":");
            if(parametersNameAndValue.equals("type")){
                typeOfItem = parametersNameAndValue[1];
                break;
            }
        }

        switch (typeOfItem) {
            case "OneHandedWeapon":
                item = new OneHandedWeapon(itemParameters);
                item.name = itemName;
                return item;
            case "TwoHandedWeapon":
                item = new TwoHandedWeapon(itemParameters);
                item.name = itemName;
                return item;
            case "Armor":
                item = new Armor(itemParameters);
                item.name = itemName;
                return item;
            case "Consumable":
                item = new Consumable(itemParameters);
                item.name = itemName;
                return item;
            case "Scroll":
                item = new Scroll(itemParameters);
                item.name = itemName;
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

    public OneHandedWeapon(List<String> ListOfParameters){
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

    public TwoHandedWeapon(List<String> ListOfParameters){

    }
}
class Armor extends Item{
    public Armor(List<String> ListOfParameters){

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

    public Consumable(List<String> ListOfParameters){
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

    public Scroll(List<String> ListOfParameters){

    }
}