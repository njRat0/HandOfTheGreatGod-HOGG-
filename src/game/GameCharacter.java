package game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.Dictionary;
import java.util.Hashtable;

public abstract class GameCharacter{
    public BufferedImage spriteInBattle;
    public int sizeOfSpriteX = 128;
    public int sizeOfSpriteY = 256;
	public float sizeOfSprite = 1f;
    public int lvl = 1;
    public String name = "None";
    public boolean showParameters = false;

    protected DecimalFormat dF = new DecimalFormat("#.#");
    protected Dictionary<String, Integer> attributes = new Hashtable<String, Integer>();
    protected Dictionary<String, Integer> previosAttributes = new Hashtable<String, Integer>();
    protected Dictionary<String, Float> parameters = new Hashtable<String, Float>();
    protected Dictionary<String, Float> additionalParameters = new Hashtable<String, Float>();
    protected Dictionary<String, Float> parametersOfEqiupedItems = new Hashtable<String, Float>();
    protected String[] eqiupedItems = new String[6];


    public GameCharacter(String[] eqiupedItems, int _str, int _int, int _dex, int _dur){
        this.eqiupedItems = eqiupedItems;
        BasicPattern.InitAttributesDictionary(attributes);
        attributes.put("Strength", _str);
        attributes.put("Durability", _dur);
        attributes.put("Dexterity", _dex);
        attributes.put("Intelligence", _int);
        BasicPattern.InitAttributesDictionary(previosAttributes);
        BasicPattern.InitParametersDictionary(parameters);
        BasicPattern.InitAdditionalParametersDictionary(additionalParameters);
        BasicPattern.InitParametersOfEqiupedItemsDictionary(parameters);
        ConvertAttributesToParameters();
        parameters.put("CurHP", parameters.get("MaxHP"));
        System.out.println(parameters.get("CurHP"));
    }

    public void ChangeAttribute(String attributeName, int value){
        if(attributes.get(attributeName) != null){
            attributes.put(attributeName, attributes.get(attributeName) + value);
            ConvertAttributesToParameters();
        }
    }

    protected void NewTurn(){
        parameters.put("CurHP", Float.valueOf(dF.format(parameters.get("CurHP") + parameters.get("RegenHP"))));
        parameters.put("CurMP", Float.valueOf(dF.format(parameters.get("CurMP") + parameters.get("RegenMP"))));
        parameters.put("CurSpeed", Float.valueOf(dF.format(parameters.get("MaxSpeed"))));
    }

    abstract void LogicOfTurn();

    public void TakeDamage(float magicalDamage, float physicalDamage){
        magicalDamage -= parameters.get("MagicArmor");
        physicalDamage -= parameters.get("PhysicalArmor");
        if(magicalDamage < 0) magicalDamage = 0;
        if(physicalDamage < 0) physicalDamage = 0;

        parameters.put("CurHP", parameters.get("CurHP") - (magicalDamage + physicalDamage));
    }

    public void TakeHealth(float value){
        parameters.put("CurHP", parameters.get("CurHP") + value);
    }

    private void ConvertAttributesToParameters(){
        float saveCurHP = parameters.get("CurHP");
        float saveCurMP = parameters.get("CurMP");
        float saveCurSpeed = parameters.get("CurSpeed");
        BasicPattern.InitParametersDictionary(parameters);
        int value;

        value = attributes.get("Strength");
        saveCurHP += 2.5f * (value-previosAttributes.get("Strength"));
        previosAttributes.put("Strength", attributes.get("Strength"));
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
        
        value = attributes.get("Dexterity");
        saveCurSpeed += 0.2f * (value-previosAttributes.get("Dexterity"));
        previosAttributes.put("Dexterity", attributes.get("Dexterity"));
        if(attributes.get("Dexterity") < 1){
            parameters.put("CreateChance", parameters.get("CreateChance") + 0.5f);
            parameters.put("CreateDamage", parameters.get("CreateDamage") + 0.02f);
            parameters.put("MaxSpeed", parameters.get("MaxSpeed") + 0.2f);
        }
        else{
            parameters.put("CreateChance", parameters.get("CreateChance") + 0.5f * value);
            parameters.put("CreateDamage", parameters.get("CreateDamage") + 0.02f * value);
            parameters.put("MaxSpeed", parameters.get("MaxSpeed") + 0.2f * value);
        }

        value = attributes.get("Intelligence");
        saveCurMP += 5f * (value-previosAttributes.get("Intelligence"));
        previosAttributes.put("Intelligence", attributes.get("Intelligence"));
        if(attributes.get("Intelligence") < 1){
            parameters.put("MaxMP", parameters.get("MaxMP") + 5f);
            parameters.put("RegenMP", parameters.get("RegenMP") + 0.05f);
            parameters.put("MagicDamage", parameters.get("MagicDamage") + 0.15f );
        }
        else{
            parameters.put("MaxMP", parameters.get("MaxMP") + 5f * value);
            parameters.put("RegenMP", parameters.get("RegenMP") + 0.05f * value);
            parameters.put("MagicDamage", parameters.get("MagicDamage") + 0.15f * value);
        }

        parameters.put("CurHP", Float.valueOf(dF.format(saveCurHP)));
        parameters.put("CurSpeed", Float.valueOf(dF.format(saveCurSpeed)));
        parameters.put("CurMP", Float.valueOf(dF.format(saveCurMP)));
        System.out.println(parameters.get("CurSpeed"));
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
        curDictionary.put("MaxSpeed", 0f);
        curDictionary.put("CurSpeed", 0f);
    }

    static public void InitAdditionalParametersDictionary(Dictionary<String, Float> curDictionary){
        curDictionary.put("MaxHP", 0f);
        curDictionary.put("RegenHP", 0f);
        curDictionary.put("MaxMP", 0f);
        curDictionary.put("RegenMP", 0f);

        curDictionary.put("CreateChance", 0f);
        curDictionary.put("CreateDamage", 0f);
        curDictionary.put("Resistance", 0f);

        curDictionary.put("PhysicalArmor", 0f);
        curDictionary.put("MagicArmor", 0f);
        curDictionary.put("PhysicalDamage", 0f);
        curDictionary.put("MagicDamage", 0f);
        curDictionary.put("MaxSpeed", 0f);
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
        curDictionary.put("MaxSpeed", 0f);
    }
}