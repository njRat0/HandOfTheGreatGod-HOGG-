package game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Dictionary;
import java.util.Hashtable;

public abstract class Character{
    public int locX, locY;
	public BufferedImage sprite;
	public float sizeOfSprite = 1f;

    private Dictionary<String, Integer> attributes = new Hashtable<String, Integer>();
    private Dictionary<String, Integer> previosAttributes = new Hashtable<String, Integer>();
    private Dictionary<String, Float> parameters = new Hashtable<String, Float>();
    private Dictionary<String, Float> parametersOfEqiupedItems = new Hashtable<String, Float>();


    public Character(){
        attributes = BasicPattern.GetBasicAttributes();
        parameters = BasicPattern.GetBasicParameters();
        parametersOfEqiupedItems = BasicPattern.GetBasicParametersOfEqiupedItems();
    }

    private void ConvertAttributesToParameters(){
        if(attributes.get("Intelligence") != previosAttributes.get("Intelligence")){
            
        }

        if(attributes.get("Dexterity") != previosAttributes.get("Dexterity")){
            
        }

        if(attributes.get("Strength") != previosAttributes.get("Strength")){
            
        }
    }

    public abstract void toDraw(Graphics2D g2d);
    public abstract void update();
}

class BasicPattern{
    static private Dictionary<String, Integer> attributes = new Hashtable<String, Integer>();
    static private Dictionary<String, Float> parameters = new Hashtable<String, Float>();
    static private Dictionary<String, Float> parametersOfEqiupedItems = new Hashtable<String, Float>();

    static private void InitParametersDictionary(){
        attributes.put("Intelligence", 1);
        attributes.put("Dexterity", 1);
        attributes.put("Strength", 1);
    }

    static private void InitAttributesDictionary(){
        parameters.put("CurHP", 10f);
        parameters.put("MaxHP", 10f);
        parameters.put("RegenHP", 10f);
        parameters.put("CurMP", 10f);
        parameters.put("MaxMP", 10f);
        parameters.put("RegenMP", 10f);

        parameters.put("PhysicalArmor", 10f);
        parameters.put("MagicArmor", 10f);
        parameters.put("Speed", 10f);
    }

    static private void InitParametersOfEqiupedItemsDictionary(){
        parametersOfEqiupedItems.put("MaxHP", 0f);
        parametersOfEqiupedItems.put("RegenHP", 0f);
        parametersOfEqiupedItems.put("MaxMP", 0f);
        parametersOfEqiupedItems.put("RegenMP", 0f);

        parametersOfEqiupedItems.put("Intelligence", 0f);
        parametersOfEqiupedItems.put("Dexterity", 0f);
        parametersOfEqiupedItems.put("Strength", 0f);

        parametersOfEqiupedItems.put("PhysicalArmor", 0f);
        parametersOfEqiupedItems.put("MagicArmor", 0f);
        parametersOfEqiupedItems.put("Speed", 0f);
    }
    
    public BasicPattern(){
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