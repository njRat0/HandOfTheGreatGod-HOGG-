package game;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Dictionary;
import java.util.List;

enum TYPES_OF_ITEMS{
    OneHandedWeapon,
    TwoHandedWeapon,
    Armor,
    Consumable,
    Scroll
}

public class ItemsService {
    private static File itemsListData = new File("data\\ItemsList.txt");
    private static Dictionary<String, List<String>> dictionaryOfItemsByID;
    private static Dictionary<String, List<String>> dictionaryOfItemsByName;

    public static void Init(){
        try {
            List<String> stringListOfItemsData = Files.readAllLines(itemsListData.toPath());
            
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
}

abstract class Item {
    int id;
    String name;
    int worth;
}

class OneHandedWeapon extends Item{
    public OneHandedWeapon(List<String> ListOfParameters){

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
    public Consumable(List<String> ListOfParameters){

    }
}
class Scroll extends Item{
    public Scroll(List<String> ListOfParameters){

    }
}

class Item_BasicSword extends Item{
    public Item_BasicSword(){
        id = 15;
        System.out.println(id);
    }
}


