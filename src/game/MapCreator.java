package game;

import java.util.Random;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MapCreator {
    public static void CreateNewMap(float roughnessFactor, int size){
        int minHight = 0;
        int maxHight = 7;
        int sizeOfSide = (int)(Math.pow(2, size) + 1);
        int[][] map = new int[sizeOfSide][sizeOfSide];
        
        map = CreateHightMap(map, minHight, maxHight, sizeOfSide, roughnessFactor);
        System.out.println("");
        for(int[] i : map){
            for(int j : i){
                System.out.print(j);
            }
            System.out.println("");
        }
        System.out.println("------------");
    }

    private static int[][] CreateHightMap(int[][] map, int minHight, int maxHight, int sizeOfSide, float roughness){
        map[0][0] = (int)(Math.random() * maxHight);
        map[0][sizeOfSide-1] = (int)(Math.random() * maxHight);
        map[sizeOfSide-1][0] = (int)(Math.random() * maxHight);
        map[sizeOfSide-1][sizeOfSide-1] = (int)(Math.random() * maxHight);

        int lengthToNextPoint = sizeOfSide/2;
        ArrayList<int[]> nextPointsCor = new ArrayList<int[]>();
        ArrayList<int[]> curPointsCor = new ArrayList<int[]>();
        int countOfAffectingCells = 0;
        int totalValue = 0;
        //ArrayList<int[]> nextPointsValue = new ArrayList<int[]>();
        curPointsCor.add(new int[]{lengthToNextPoint, lengthToNextPoint});
        //nextPointsValue.add(new int[]{0, 0});
        while(lengthToNextPoint != 0){
            //diamond step
            for(int[] curCellCor : curPointsCor){
                System.out.println("New point of diamond step: " + curCellCor[0] + ", " + curCellCor[1]);
                totalValue = 0;
                countOfAffectingCells = 0;
                if(curCellCor[1] + lengthToNextPoint < sizeOfSide && curCellCor[0] + lengthToNextPoint < sizeOfSide){
                    totalValue += map[curCellCor[1] + lengthToNextPoint][curCellCor[0] + lengthToNextPoint];
                    countOfAffectingCells++;
                }
                if(curCellCor[1] - lengthToNextPoint >= 0 && curCellCor[0] + lengthToNextPoint < sizeOfSide){
                    totalValue += map[curCellCor[1] + lengthToNextPoint][curCellCor[0] - lengthToNextPoint];
                    countOfAffectingCells++;
                }
                if(curCellCor[1] + lengthToNextPoint < sizeOfSide && curCellCor[0] - lengthToNextPoint >= 0){
                    totalValue += map[curCellCor[1] - lengthToNextPoint][curCellCor[0] + lengthToNextPoint];
                    countOfAffectingCells++;
                }
                if(curCellCor[1] - lengthToNextPoint >= 0 && curCellCor[0] - lengthToNextPoint >= 0){
                    totalValue += map[curCellCor[1] - lengthToNextPoint][curCellCor[0] - lengthToNextPoint];
                    countOfAffectingCells++;
                }
                map[curCellCor[1]][curCellCor[0]] = totalValue/countOfAffectingCells;

                if(!nextPointsCor.contains(new int[]{curCellCor[0]+lengthToNextPoint, curCellCor[1]}))nextPointsCor.add(new int[]{curCellCor[0]+lengthToNextPoint, curCellCor[1]});
                if(!nextPointsCor.contains(new int[]{curCellCor[0]-lengthToNextPoint, curCellCor[1]}))nextPointsCor.add(new int[]{curCellCor[0]-lengthToNextPoint, curCellCor[1]});
                if(!nextPointsCor.contains(new int[]{curCellCor[0], curCellCor[1]+lengthToNextPoint}))nextPointsCor.add(new int[]{curCellCor[0], curCellCor[1]+lengthToNextPoint});
                if(!nextPointsCor.contains(new int[]{curCellCor[0], curCellCor[1]-lengthToNextPoint}))nextPointsCor.add(new int[]{curCellCor[0], curCellCor[1]-lengthToNextPoint});
            }
            curPointsCor = new ArrayList<int[]>(List.copyOf(nextPointsCor));
            nextPointsCor.clear();
            for(int[] cor : nextPointsCor){
                System.out.print(cor[0]+"x" + cor[1]+"y, ");
            }
            System.out.println();
            //squer step
            int lengthToNextPoint_SquerStep = lengthToNextPoint -1;
            for(int[] curCellCor : curPointsCor){
                System.out.println("New point of squer step: " + curCellCor[0] + ", " + curCellCor[1]);
                totalValue = 0;
                countOfAffectingCells = 0;
                if(curCellCor[0] + lengthToNextPoint < sizeOfSide){
                    totalValue += map[curCellCor[1]][curCellCor[0] + lengthToNextPoint];
                    countOfAffectingCells++;
                }
                if(curCellCor[0] - lengthToNextPoint >= 0){
                    totalValue += map[curCellCor[1]][curCellCor[0] - lengthToNextPoint];
                    countOfAffectingCells++;
                }
                if(curCellCor[1] + lengthToNextPoint < sizeOfSide){
                    totalValue += map[curCellCor[1] + lengthToNextPoint][curCellCor[0]];
                    countOfAffectingCells++;
                }
                if(curCellCor[1] - lengthToNextPoint >= 0){
                    totalValue += map[curCellCor[1] - lengthToNextPoint][curCellCor[0]];
                    countOfAffectingCells++;
                }
                map[curCellCor[1]][curCellCor[0]] = totalValue/countOfAffectingCells;
                
                //stop here<<<<<<<<<
                if(lengthToNextPoint_SquerStep > 0){
                    if(curCellCor[0] + lengthToNextPoint_SquerStep < sizeOfSide && curCellCor[1] + lengthToNextPoint_SquerStep < sizeOfSide && !ReallyContains_IntMassive(nextPointsCor, curCellCor)){                        
                        nextPointsCor.add(new int[]{curCellCor[0]+lengthToNextPoint_SquerStep,curCellCor[1]+lengthToNextPoint_SquerStep});
                    }
                    if(curCellCor[0] - lengthToNextPoint_SquerStep >= 0 && curCellCor[1] + lengthToNextPoint_SquerStep < sizeOfSide && !ReallyContains_IntMassive(nextPointsCor, curCellCor)){                        
                        nextPointsCor.add(new int[]{curCellCor[0]+lengthToNextPoint_SquerStep,curCellCor[1]+lengthToNextPoint_SquerStep});
                    }
                    if(curCellCor[0] + lengthToNextPoint_SquerStep < sizeOfSide && curCellCor[1] - lengthToNextPoint_SquerStep >= 0 && !ReallyContains_IntMassive(nextPointsCor, curCellCor)){
                        nextPointsCor.add(new int[]{curCellCor[0]+lengthToNextPoint_SquerStep,curCellCor[1]+lengthToNextPoint_SquerStep});
                    }
                    if(curCellCor[0] - lengthToNextPoint_SquerStep >= 0 && curCellCor[1] - lengthToNextPoint_SquerStep >= 0 && !ReallyContains_IntMassive(nextPointsCor, curCellCor)){
                        nextPointsCor.add(new int[]{curCellCor[0]+lengthToNextPoint_SquerStep,curCellCor[1]+lengthToNextPoint_SquerStep});
                    }
                }
            }
            curPointsCor.clear();
            curPointsCor = new ArrayList<int[]>(List.copyOf(nextPointsCor));
            nextPointsCor.clear();
            lengthToNextPoint -=1;

            for(int[] cor : curPointsCor){
                System.out.print(cor[0]+"x" + cor[1]+"y, ");
            }
        }
        return map;
    }

    private static boolean ReallyContains_IntMassive(ArrayList<int[]> list, int[] findingValue){
        boolean result = false;
        for(int[] e : list){
            if(e[0] == findingValue[0] && e[1] == findingValue[1]){
                result = true;
                break;
            }
        }
        return result;
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
