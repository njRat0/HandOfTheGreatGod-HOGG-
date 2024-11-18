package game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Dictionary;
import java.util.Hashtable;

public abstract class Character{
    public int locX, locY;
	public BufferedImage sprite;
	public float sizeOfSprite = 1f;

    protected Dictionary<String, Integer> attributes = new Hashtable<String, Integer>();
    protected Dictionary<String, Integer> previosAttributes = new Hashtable<String, Integer>();
    protected Dictionary<String, Float> parameters = new Hashtable<String, Float>();
    protected Dictionary<String, Float> parametersOfEqiupedItems = new Hashtable<String, Float>();
    protected String[] eqiupedItems = new String[6];


    public Character(String[] eqiupedItems){
        this.eqiupedItems = eqiupedItems;
        attributes = BasicPattern.GetBasicAttributes();
        previosAttributes = attributes;
        parameters = BasicPattern.GetBasicParameters();
        parametersOfEqiupedItems = BasicPattern.GetBasicParametersOfEqiupedItems();
    }

    public void ChangeAttribute(String attributeName, int value){
        if(attributes.get(attributeName) != null){
            attributes.put(attributeName, value);
            ConvertAttributesToParameters();
        }
    }

    private void ConvertAttributesToParameters(){
        parameters = BasicPattern.GetBasicParameters();
        int value;
        if(attributes.get("Strength") != previosAttributes.get("Strength")){
            value = attributes.get("Strength");
            if(attributes.get("Strength") < 1){
                parameters.put("MaxHP", parameters.get("MaxHP") + 2.5f);
                parameters.put("RegenHP", parameters.get("RegenHP") + 0.03f);
                parameters.put("PhysicalDamage", parameters.get("PhysicalDamage") + 0.15f);
                parameters.put("ResistanceToEquipmentSpeedCost", parameters.get("ResistanceToEquipmentSpeedCost") + 0.5f);
            }
            else{
                parameters.put("MaxHP", parameters.get("MaxHP") + 2.5f * value);
                parameters.put("RegenHP", parameters.get("RegenHP") + 0.03f * value);
                parameters.put("PhysicalDamage", parameters.get("PhysicalDamage") + 0.15f * value);
                parameters.put("ResistanceToEquipmentSpeedCost", parameters.get("ResistanceToEquipmentSpeedCost") + 0.5f * value);
            }
        }
         
        if(attributes.get("Durability") != previosAttributes.get("Durability")){
            value = attributes.get("Durability");
            if(attributes.get("Durability") < 1){
                parameters.put("MaxHP", parameters.get("MaxHP") + 7.5f);
                parameters.put("RegenHP", parameters.get("RegenHP") + 0.07f);
                parameters.put("PhysicalArmor", parameters.get("PhysicalArmor") + 0.1f);
                parameters.put("MagicArmor", parameters.get("MagicArmor") + 0.1f);
                parameters.put("Resistance", 0f);
            }
            else{
                parameters.put("MaxHP", parameters.get("MaxHP") + 7.5f * value);
                parameters.put("RegenHP", parameters.get("RegenHP") + 0.07f * value);
                parameters.put("PhysicalArmor", parameters.get("PhysicalArmor") + 0.1f * value);
                parameters.put("MagicArmor", parameters.get("MagicArmor") + 0.1f * value);
                parameters.put("Resistance", (float)Math.floor(parameters.get("Resistance") + 0.04f * value));
            }
        }
        if(attributes.get("Dexterity") != previosAttributes.get("Dexterity")){
            value = attributes.get("Dexterity");
            if(attributes.get("Dexterity") < 1){
                parameters.put("CreateChance", parameters.get("CreateChance") + 0.5f);
                parameters.put("CreateDamage", parameters.get("CreateDamage") + 0.02f);
                parameters.put("Speed", parameters.get("Speed") + 0.2f);
            }
            else{
                parameters.put("CreateChance", parameters.get("CreateChance") + 0.5f * value);
                parameters.put("CreateDamage", parameters.get("CreateDamage") + 0.02f * value);
                parameters.put("Speed", parameters.get("Speed") + 0.2f * value);
            }
        }

        if(attributes.get("Intelligence ") != previosAttributes.get("Intelligence ")){
            value = attributes.get("Intelligence");
            if(attributes.get("Intelligence") < 1){
                parameters.put("MaxMP", parameters.get("MaxHP") + 5f);
                parameters.put("RegenHP", parameters.get("RegenHP") + 0.05f);
                parameters.put("MagicDamage", parameters.get("MagicDamage") + 0.15f );
            }
            else{
                parameters.put("MaxMP", parameters.get("MaxHP") + 5f * value);
                parameters.put("RegenHP", parameters.get("RegenHP") + 0.05f * value);
                parameters.put("MagicDamage", parameters.get("MagicDamage") + 0.15f * value);
            }
        }


        previosAttributes = attributes;
    }

    public abstract void ToDraw(Graphics2D g2d);
    public abstract void Update();
}

class BasicPattern{
    static private Dictionary<String, Integer> attributes = new Hashtable<String, Integer>();
    static private Dictionary<String, Float> parameters = new Hashtable<String, Float>();
    static private Dictionary<String, Float> parametersOfEqiupedItems = new Hashtable<String, Float>();

    static private void InitAttributesDictionary(){
        attributes.put("Intelligence", 1);
        attributes.put("Dexterity", 1);
        attributes.put("Strength", 1);
    }

    static private void InitParametersDictionary(){
        parameters.put("CurHP", 0f);
        parameters.put("MaxHP", 0f);
        parameters.put("RegenHP", 0f);
        parameters.put("CurMP", 0f);
        parameters.put("MaxMP", 0f);
        parameters.put("RegenMP", 0f);

        parameters.put("CreateChance", 0f);
        parameters.put("CreateDamage", 0f);
        parameters.put("Resistance", 0f);
        parameters.put("ResistanceToEquipmentSpeedCost", 0f);

        parameters.put("PhysicalArmor", 0f);
        parameters.put("MagicArmor", 0f);
        parameters.put("PhysicalDamage", 0f);
        parameters.put("MagicDamage", 0f);
        parameters.put("Speed", 0f);
    }

    static private void InitParametersOfEqiupedItemsDictionary(){
        parametersOfEqiupedItems.put("Intelligence", 0f);
        parametersOfEqiupedItems.put("Dexterity", 0f);
        parametersOfEqiupedItems.put("Strength", 0f);
        
        parametersOfEqiupedItems.put("MaxHP", 0f);
        parametersOfEqiupedItems.put("RegenHP", 0f);
        parametersOfEqiupedItems.put("MaxMP", 0f);
        parametersOfEqiupedItems.put("RegenMP", 0f);

        parametersOfEqiupedItems.put("CreateChance", 0f);
        parametersOfEqiupedItems.put("CreateDamage", 1.8f);
        parametersOfEqiupedItems.put("Resistance", 0f);
        parametersOfEqiupedItems.put("ResistanceToEquipmentSpeedCost", 0f);

        parametersOfEqiupedItems.put("PhysicalArmor", 0f);
        parametersOfEqiupedItems.put("MagicArmor", 0f);
        parametersOfEqiupedItems.put("PhysicalDamage", 0f);
        parametersOfEqiupedItems.put("MagicDamage", 0f);
        parametersOfEqiupedItems.put("Speed", 0f);
    }
    
    public static void Init(){
        InitAttributesDictionary();
        InitParametersDictionary();
        InitParametersOfEqiupedItemsDictionary();
    }

    static Dictionary<String, Float> GetBasicParameters(){
        return parameters;
    }

    static Dictionary<String, Float> GetBasicParametersOfEqiupedItems(){
        return parametersOfEqiupedItems;
    }

    static Dictionary<String, Integer> GetBasicAttributes(){
        return attributes;
    }
}