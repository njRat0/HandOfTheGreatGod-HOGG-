package game;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class DataService {

    private static File DataFile_ItemsInventory;
    private static File DataFile_CardsInventory;
    private static File DataFile_PlayerParameters;
    private static File DataFile_EquippedItems;

    public DataService(){
        DataFile_ItemsInventory = new File("data\\Player\\ItemsInventory.txt");
        if(DataFile_ItemsInventory.exists() && !DataFile_ItemsInventory.isDirectory()) { 
            System.out.println("file exist");
        }
        else{
            try{
                DataFile_ItemsInventory.createNewFile();
            }
            catch (IOException e){
                System.out.println("The settings data file are already created");
            }
        }

        DataFile_CardsInventory =  new File("data\\Player\\CardsInventory.txt");
        if(DataFile_CardsInventory.exists() && !DataFile_CardsInventory.isDirectory()) { 
            System.out.println("file exist");
        }
        else{
            try{
                DataFile_CardsInventory.createNewFile();
            }
            catch (IOException e){
                System.out.println("The settings data file are already created");
            }
        }

        DataFile_PlayerParameters = new File("data\\Player\\PlayerParameters.txt");
        if(DataFile_PlayerParameters.exists() && !DataFile_PlayerParameters.isDirectory()) { 
            System.out.println("file exist");
        }
        else{
            try{
                DataFile_PlayerParameters.createNewFile();
            }
            catch (IOException e){
                System.out.println("The settings data file are already created");
            }
        }

        DataFile_EquippedItems = new File("data\\Player\\EquippedItems.txt");
        if(DataFile_EquippedItems.exists() && !DataFile_EquippedItems.isDirectory()) { 
            System.out.println("file exist");
        }
        else{
            try{
                DataFile_EquippedItems.createNewFile();
            }
            catch (IOException e){
                System.out.println("The settings data file are already created");
            }
        }
    }

    public static void Player_SetDefaultData_ItemsInventory(){

    }

    public static void Player_SetDefaultData_CardsInventory(){

    }

    public static void Player_SetDefaultData_PlayerParameters(){

    }

    public static void Player_SetDefaultData_EquippedItems(){

    }

    public static void Player_SaveData_ItemsInventory(){

    }

    public static ArrayList<String> Player_GetData_ItemsInventory(ArrayList<String> itemsInventory){
        return new ArrayList<String>();
    }

    public static void Player_SaveData_CardsInventory(){

    }

    public static ArrayList<String> Player_GetData_CardsInventory(ArrayList<String> cardsInventory){
        return new ArrayList<String>();
    }

    public static void Player_SaveData_PlayersParameters(){

    }

    public static void Player_GetData_PlayersParameters(Player player){
        
    }
}
