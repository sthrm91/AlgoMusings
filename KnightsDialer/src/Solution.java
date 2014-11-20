import java.util.ArrayList;
import java.util.List;


public class Solution {
	
	static int[][] knightsPosition = {{4,6}, {6,8}, {7,9}, {4,8}, {0,3,9}, {}, {0,1,7}, {2,6}, {1,3}, {2,4}};
	
	static List<Character> getNextLengthNumbers(List<Character> phoneNumbers)
	{
		ArrayList<Character> nextLengthNumbers = new ArrayList<>();
		for (Character phoneNumber : phoneNumbers)
		{
			int lastDigit = (int)(phoneNumber - '0');
			for (int i=0; i < knightsPosition[lastDigit].length; i++)
			{
				nextLengthNumbers.add((char)('0' + knightsPosition[lastDigit][i]));
			}
			
		}
		return nextLengthNumbers;
		
	}
	
    static long calculateUniqueMovements(int digits) {
    	
    	 long[] nextLastDigitFrequency = new long[10];
    	 long count = 0;
    	 
    	 for(int i = 1; i <= digits; i++)
    	 {
             if(i == 1)
             {
            	 nextLastDigitFrequency[1] = 1;                 
             }
             else
             {
            	 nextLastDigitFrequency = getLastDigitFrequency(nextLastDigitFrequency);
             }
             //System.out.println("level : " + i + " : " + phoneNumbers);
    	 }
    	 
    	 for(int i = 0; i <nextLastDigitFrequency.length; i++)
    	 {
    		 count += nextLastDigitFrequency[i];
    	 }
    	 
         return count;

    }
    
    
    static long[] getLastDigitFrequency(long[] currentFrequency)
    {
    	long[] nextLastDigitFrequency = new long[10];
    	
    	for (int lastDigit = 0; lastDigit < currentFrequency.length; lastDigit++)
		{
			for (int i=0; i < knightsPosition[lastDigit].length; i++)
			{
				nextLastDigitFrequency[knightsPosition[lastDigit][i]] += currentFrequency[lastDigit];
			}
			
		}
    	
    	return nextLastDigitFrequency;    	
    }
    
    public static void main(String[] arg)
    {
    	System.out.println(calculateUniqueMovements(7));
    }


}
