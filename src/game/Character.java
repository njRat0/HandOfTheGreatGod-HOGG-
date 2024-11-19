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


    public Character(String[] eqiupedItems, int _str, int _int, int _dex, int _dur){
        this.eqiupedItems = eqiupedItems;
        BasicPattern.InitAttributesDictionary(attributes);
        attributes.put("Strength", _str);
        attributes.put("Durability", _dur);
        attributes.put("Dexterity", _dex);
        attributes.put("Intelligence", _int);
        BasicPattern.InitAttributesDictionary(previosAttributes);
        BasicPattern.InitParametersDictionary(parameters);
        BasicPattern.InitParametersOfEqiupedItemsDictionary(parameters);
        ConvertAttributesToParameters();
    }

    public void ChangeAttribute(String attributeName, int value){
        if(attributes.get(attributeName) != null){
            attributes.put(attributeName, attributes.get(attributeName) + value);
            ConvertAttributesToParameters();
        }
    }

    private void ConvertAttributesToParameters(){
        BasicPattern.InitParametersDictionary(parameters);
        int value;
        if(attributes.get("Strength") != previosAttributes.get("Strength")){
            previosAttributes.put("Strength", attributes.get("Strength"));
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
            previosAttributes.put("Durability", attributes.get("Durability"));
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
            previosAttributes.put("Dexterity", attributes.get("Dexterity"));
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

        if(attributes.get("Intelligence") != previosAttributes.get("Intelligence")){
            value = attributes.get("Intelligence");
            previosAttributes.put("Intelligence", attributes.get("Intelligence"));
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
        System.out.println(parameters.get("MaxHP"));
    }

    public abstract void ToDraw(Graphics2D g2d);
    public abstract void Update();
}

class BasicPattern{
    static public void InitAttributesDictionary(Dictionary<String, Integer> curDictionary){
        curDictionary.put("Intelligence", 1);
        curDictionary.put("Durability", 1);
        curDictionary.put("Dexterity", 1);
        curDictionary.put("Strength", 1);
    }

    static public void InitParametersDictionary(Dictionary<String, Float> curDictionary){
        curDictionary.put("CurHP", 0f);
        curDictionary.put("MaxHP", 0f);
        curDictionary.put("RegenHP", 0f);
        curDictionary.put("CurMP", 0f);
        curDictionary.put("MaxMP", 0f);
        curDictionary.put("RegenMP", 0f);

        curDictionary.put("CreateChance", 0f);
        curDictionary.put("CreateDamage", 0f);
        curDictionary.put("Resistance", 0f);
        curDictionary.put("ResistanceToEquipmentSpeedCost", 0f);

        curDictionary.put("PhysicalArmor", 0f);
        curDictionary.put("MagicArmor", 0f);
        curDictionary.put("PhysicalDamage", 0f);
        curDictionary.put("MagicDamage", 0f);
        curDictionary.put("Speed", 0f);
    }

    static public void InitParametersOfEqiupedItemsDictionary(Dictionary<String, Float> curDictionary){
        curDictionary.put("Intelligence", 0f);
        curDictionary.put("Dexterity", 0f);
        curDictionary.put("Strength", 0f);
        
        curDictionary.put("MaxHP", 0f);
        curDictionary.put("RegenHP", 0f);
        curDictionary.put("MaxMP", 0f);
        curDictionary.put("RegenMP", 0f);

        curDictionary.put("CreateChance", 0f);
        curDictionary.put("CreateDamage", 1.8f);
        curDictionary.put("Resistance", 0f);
        curDictionary.put("ResistanceToEquipmentSpeedCost", 0f);

        curDictionary.put("PhysicalArmor", 0f);
        curDictionary.put("MagicArmor", 0f);
        curDictionary.put("PhysicalDamage", 0f);
        curDictionary.put("MagicDamage", 0f);
        curDictionary.put("Speed", 0f);
    }
}