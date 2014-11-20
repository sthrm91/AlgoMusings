package relateIQ.challenge.p0;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * 
 * @author Sethu : - sthrm91 AT hotmail DOT com / sthrm91 AT gmail DOT com
 * Solution has a main method which takes in the dimension of a square matrix, followed by strings representing each row of the matrix
 * and outputs the average size of the land in the map
 *
 */
public class Solution
{
    private static class IslandMap 
    {
        /*
         * Constructs the Map using horizontal strip by strip
         */
        public IslandMap(int dimension, List<String> rows)
        {
            this.dimension = dimension;
            map = new int[dimension][dimension];
            for(int i = 0; i < dimension; i++)
            {
                String each = rows.get(i);
                for(int j = 0; j < dimension; j++)
                {
                    if(each.charAt(j) == '1')
                    {
                        map[i][j]=1;
                    }
                        
                }
            }
            
        }
        
        /*
         * Performs a breadth first traversal starting from i,j
         * The land size would be nothing but number of nodes visited from i,j
         */
        private int getLandSizefor(int i, int j, boolean[][] marked)
        {
            LinkedList<Integer> queue = new LinkedList<>();
            
            if(map[i][j] == 0)
            {
                return 0;
            }
            
            queue.add(i*dimension + j);
            int size = 0;
            marked[i][j] = true;
            
            while(!queue.isEmpty())
            {
               int current = queue.remove(0);
               int currX = current/dimension, currY = current % dimension;            
               
               /*
                * if any of the neighbors is land and not marked, then add them to the queue 
                */
               if(currX > 0 && map[currX - 1][currY]==1 && !marked[currX - 1][currY])
               {
                   queue.add((currX - 1)*dimension + currY);
                   marked[currX - 1][currY] = true;
               }
               if(currX < dimension -1 && map[currX + 1][currY]==1 && !marked[currX + 1][currY])
               {
                   queue.add((currX+1)*dimension + currY);
                   marked[currX + 1][currY] = true;
               }
               if(currY > 0 && map[currX][currY-1]==1 && !marked[currX][currY -1])
               {
                   queue.add(currX*dimension + currY -1);
                   marked[currX][currY-1] = true;
                   
               }
               if(currY < dimension -1 && map[currX][currY+1]==1 && !marked[currX][currY+1])
               {
                   queue.add(currX*dimension + currY + 1);
                   marked[currX][currY+1] = true;
               }               
               size++;                
            }
            
            return size;            
        }
        
        public int getAverageLandSize()
        {
            boolean[][] marked = new boolean[dimension][dimension];
            int totalArea = 0; int numOflandMass = 0; 
            
            for(int i=0; i<dimension; i++)
            {
                for(int j = 0; j< dimension; j++)
                {
                    if(map[i][j] == 1 && !marked[i][j])
                    {
                        totalArea += getLandSizefor(i, j, marked); 
                        numOflandMass++;                
                    }
                }
            }
            return numOflandMass == 0 ? 0 : totalArea/numOflandMass;
        }
        
        private final int dimension;
        private int[][] map;
    }
    
    public static void main(String[] arg)
    {
        Scanner scanner = new Scanner(System.in);
        int dimension = scanner.nextInt();
        ArrayList<String> rows = new ArrayList<>();
        
        for(int i = 0; i < dimension; i++)
        {
            rows.add(scanner.next());
        }            
        
        IslandMap islandMap = new IslandMap(dimension, rows);
        System.out.println(islandMap.getAverageLandSize());
        scanner.close();        
    }

}