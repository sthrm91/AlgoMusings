import java.util.ArrayList;
import java.util.List;


public class FSMImplementation 
{
    enum State {printing, beforeComment, afterComment, comment};
    /*
     * Given a list of Strings, prints the uncommented part 
     */
    public static void getUncommentedPart(List<String> lines)
    {
        State currentState = State.printing, previousState = State.printing;
        
        for(String each : lines)
        {
            for(int i = 0; i < each.length(); i++)
            {
                if(currentState == State.printing)
                {
                    if(each.charAt(i) == '/')
                    {
                        currentState = State.beforeComment;    
                    }
                    else
                    {
                        System.out.print(each.charAt(i));
                    }
                                    
                }
                else if(currentState == State.beforeComment) 
                {
                    if(each.charAt(i) == '*')
                    {
                        currentState = State.comment;
                        previousState = State.comment;
                    }
                    else
                    {
                        System.out.print("/" + each.charAt(i));
                    }
                }
                else if(currentState == State.comment)
                {
                    if(each.charAt(i) == '*')
                    {
                        currentState = State.afterComment;
                    }
                }
                else 
                {
                    if(each.charAt(i) == '/')
                    {
                        currentState = State.printing;
                        previousState = State.printing;
                    }
                    else 
                    {
                        currentState = State.comment;
                    }
                }
            }
            currentState = previousState;
        }

    }
    
    public static void main(String[] args) 
    {
        List<String> inputString = new ArrayList<String>();
        inputString.add("hello "); 
        inputString.add("How are you");
        inputString.add("Commented section /* doesnt print me ");
        inputString.add("i am still not being printed */ ends the commented part ");
        inputString.add("/*/");
        inputString.add("hello */ new chapter");
        inputString.add("/* now this is not affected*");
        inputString.add("/Still in comments*/");
        inputString.add("Finally I will be printed");
        getUncommentedPart(inputString);
    }

}
