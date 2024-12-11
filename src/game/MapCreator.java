package game;

import java.util.Random;
import java.util.ArrayList;

public class MapCreator {
    public static void CreateNewMap(float roughnessFactor, int size){
        int minHight = 0;
        int maxHight = 7;
        int sizeOfSide = (int)(Math.pow(2, size) + 1);
        int[][] map = new int[sizeOfSide][sizeOfSide];
        
        map = CreateHightMap(map, minHight, maxHight, sizeOfSide, roughnessFactor);
    }

    private static int[][] CreateHightMap(int[][] map, int minHight, int maxHight, int sizeOfSide, float roughness){
        map[0][0] = (int)(Math.random() * maxHight);
        map[0][sizeOfSide] = (int)(Math.random() * maxHight);
        map[sizeOfSide][0] = (int)(Math.random() * maxHight);
        map[sizeOfSide][sizeOfSide] = (int)(Math.random() * maxHight);
        
        //diamond step
        int lengthToNextPoint = sizeOfSide/2;
        ArrayList<int[]> nextPoints = new ArrayList<int[]>();
        nextPoints.add(new int[]{lengthToNextPoint, lengthToNextPoint});
        while(lengthToNextPoint != 0){

        }

        return map;
    }

    public static void CreateNewOpenWorld(){
        
    }

    public static void CreateNewUpperWorld(){
        
    }

    public static void CreateNewDownWorld(){
        
    }

    public static void CreateMapOfDungeons(){
        
    }

    private static void CreateMapOfRisks(int intensityOfRisks, int intensityOfDangerous){

    }
}
