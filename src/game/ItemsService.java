package game;

public class ItemsService {


    public static void GetItems(int id){

    }

    public static void SetItemToInventoryOfPlayer(){

    }
}

abstract class Item {
    int id;

}

class Item_BasicSword extends Item{
    public Item_BasicSword(){
        id = 15;
        System.out.println(id);
    }
}


