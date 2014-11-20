package relateIQ.challenge.p1;

import java.util.Scanner;

/**
 * 
 * @author Sethu : - sthrm91 AT hotmail DOT com / sthrm91 AT gmail DOT com
 * Solution has a main method which takes in an input string representing the sequence of steps taken by a robot
 * And outputs the final position in <x y d> as specified in the instructions where x and y correspond to final positions in x and y axis respectively, d is the direction 
 *
 */
public class Solution
{
    /*
     * Robot is the depiction of the robot mentioned in P1.
     * It is capable of rotating clockwise or anti clockwise, move a step in the direction it faces
     * Initialized with currentX, currentY = 0,0 and direction -> North
     */
    private static class Robot 
    {
        public Robot()
        {
            this.currentX = 0;
            this.currentY = 0;
            this.currentDirection = 0; 
        }
        
        /*
         * Moves the bot one step in the current direction
         */
        public void moveOneStep()
        {
            for (char direction : allDirections[currentDirection].toCharArray())
            {
                switch(direction)
                {
                    case 'N' : currentY ++;
                    break;
                
                    case 'S' : currentY --;
                    break;
                
                    case 'E' : currentX ++;
                    break;
                
                    case 'W' : currentX --;
                    break;            
                }
            }
        }
        
        /*
         * Rotates the bot in clockwise direction if isClockwise is true otherwise rotates it anticlockwise
         */
        public void rotate(boolean isClockwise)
        {
            if(isClockwise)
            {
                currentDirection = currentDirection == allDirections.length - 1 ? 0 : currentDirection + 1;            
            }
            else
            {
                currentDirection = currentDirection == 0 ? allDirections.length - 1 : currentDirection - 1;
            }        
        }
        
        /*
         * Moves the bot in the sequence specified by the step 
         */
        public void moveTheBot(String steps)
        {
            for(char each : steps.toCharArray())
            {
                if(each == 'S')
                {
                    moveOneStep();
                }
                else
                {
                    if(each == 'C') 
                    {
                        rotate(true);
                    }
                    else if(each == 'A') 
                    {
                        rotate(false);
                    }
                    else
                    {
                        throw new IllegalArgumentException();
                    }
                }
                
                    
            }
        }
        
        public String  getPosition()
        {
            return currentX + " " + currentY + " " + allDirections[currentDirection];
        }
            
        private int currentX;
        private int currentY;
        /*
         * 8 - directions flattened out into an array 
         */
        private static final String[] allDirections = {"N", "NE", "E", "SE", "S", "SW", "W", "NW"};
        private int currentDirection;    
    }
    
    public static void main(String[] arg)
    {
        Scanner scanner = new Scanner(System.in);
        Robot robot = new Robot();
        robot.moveTheBot(scanner.next());
        System.out.println(robot.getPosition());        
        scanner.close();
    }

}
