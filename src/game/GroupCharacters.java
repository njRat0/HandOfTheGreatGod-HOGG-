package game;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class GroupCharacters {
    public int global_locX, global_locY;
    public int local_locX, local_locY;

    private ArrayList<GameCharacter> charactersInGroup;
    public BufferedImage[] spritesOfGroup = new BufferedImage[4]; // 0 - right, 1 - up, 2 - left , 3 - down

    public GroupCharacters(int global_locX, int global_locY, ArrayList<GameCharacter> listOfCharacter, BufferedImage[] spritesOfGroup){
        this.charactersInGroup = listOfCharacter;
        this.global_locX = global_locX;
        this.global_locY = global_locY;

        if(spritesOfGroup == null){
            //Basic groups sprites
            try {
                spritesOfGroup[0] = ImageIO.read(new File("res\\MapRes\\Groups\\Player\\Right.png"));
                spritesOfGroup[1] = ImageIO.read(new File("res\\MapRes\\Groups\\Player\\Up.png"));
                spritesOfGroup[2] = ImageIO.read(new File("res\\MapRes\\Groups\\Player\\Left.png"));
                spritesOfGroup[3] = ImageIO.read(new File("res\\MapRes\\Groups\\Player\\Down.png"));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        else{
            this.spritesOfGroup = spritesOfGroup;
        }
    }

    public ArrayList<GameCharacter> GetCharactersInGroup(){
        return GetCharactersInGroup();
    }

    public void SetCharactersList(ArrayList<GameCharacter> newCharactersList){
        charactersInGroup = newCharactersList;
    }

    public void AddCharacterToGroup(GameCharacter newCharacter){
        charactersInGroup.add(newCharacter);
    }

    public void RemoveCharactersInGroup(String nameOrIndex){
        if(nameOrIndex.chars().anyMatch(Character::isDigit)){
            if(charactersInGroup.size()<=Integer.parseInt(nameOrIndex)){
                System.out.println("Error: out of index in group of characters");
            }
        }
        else{
            boolean isFound = false;
            for(int i = 0; i < charactersInGroup.size(); i++){
                if(charactersInGroup.get(i).name == nameOrIndex){
                    isFound = true;
                    charactersInGroup.remove(i);
                    break;
                }
            }

            if(isFound == false){
                System.out.println("Error: group doesnt contains this character");
            }
        }
    }
}
