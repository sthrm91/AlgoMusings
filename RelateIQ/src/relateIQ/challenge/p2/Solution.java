package relateIQ.challenge.p2;

import java.util.Scanner;

/**
 * 
 * @author Sethu : - sthrm91 AT hotmail DOT com / sthrm91 AT gmail DOT com
 * Solution has a main method which takes in an input string and decodes it based on MonoAlphabeticCipher.
 * The key to this cipher is hard coded and constructed from the solutions of first two problems
 *
 */
public class Solution
{
    private static class MonoAlphabeticCipher 
    {        
        /*
         * Given a word, the following function constructs the key according to the instructions specified
         */
        private String ConstructKey(String word)
        {
            boolean[] charsFound = new boolean[26];
            String key = "";
            for (char each : word.toCharArray())
            {
                if(each == ' ')
                {
                    key += each;
                }
                else
                {
                    if(!charsFound[each - 'A'])
                    {
                        key += each;
                        charsFound[each - 'A'] = true;
                    }                    
                }                
            }
            return key;
        }
        
        private static String zToA()
        {
            String zToA = "";
            for(int i = 25; i >= 0; i--)
            {
                zToA += (char)('A'+ i);
            }
            return zToA;
        }
        
        public String decode(String input)
        {
            String output = "";
            /*
             * finds a letter in the key and maps it back to its initial letter using the index at which it is found
             */
            
            for (char each : input.toCharArray())
            {
                int index = key.indexOf(each);
                output += index == 26 ? ' ' :  (char)('A' + index);
            }
            
            return output;
        }
        
        /*
         * The below key was found by solving challenges P0 & P1
         * P0 yielded a solution 29,  digits add up to 11 --> 2 => 'B'
         * P1 yielded -12 21 E, which according to the instructions maps to "LUE"
         * Hence the key starts with "BLUE "
         * 
         */
        private final String key = ConstructKey("BLUE " + zToA());

    }
    
    public static void main(String[] arg)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println(new MonoAlphabeticCipher().decode(scanner.nextLine()));
        scanner.close();
    }    
}

