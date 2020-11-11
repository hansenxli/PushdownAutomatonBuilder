// Coded by Hansen Li, Sthitadhi Sengupta, and Adrika Khan
// November 05, 2020

import java.io.File;
import java.util.*;

class States{
    ArrayList<ArrayList<Integer>> states;
    HashMap<String, Character> transitions;
    HashMap<String, Character> popFromStack;
    HashMap<String, Character> pushToStack;
    ArrayList<Integer> acceptStates;
    int numberofStates;
    Stack<Character> pdaStack;
    
    States(int numberofStates, ArrayList<Integer> acceptStates){
    	
        this.numberofStates = numberofStates;
        this.acceptStates = acceptStates;
        states = new ArrayList<ArrayList<Integer>>();
        for(int i=0; i < numberofStates; i++)
        {
            states.add(new ArrayList<Integer>());
        }
        transitions = new HashMap<>();
        popFromStack = new HashMap<>();
        pushToStack = new HashMap<>();
        pdaStack = new Stack<>();
    }
    
    void addState(int state1, int state2, char transitionChar, char popChar, char pushChar)
    {
        states.get(state1).add(state2);

        String key = "";
        key = Integer.toString(state1)+Integer.toString(state2);
        transitions.put(key, transitionChar);

        //System.out.println("States - Pushing: " + pushChar);
    	//System.out.println("States - Popping: " + popChar);
        popFromStack.put(key, popChar);
        pushToStack.put(key, pushChar);
    }
    
    void printStates(){
        for(int i=0;i<numberofStates;i++)
        {
            
            for(int state: states.get(i)){
                System.out.print("State" + i);

                System.out.print("->" + state);
                String key="";
                String iString = Integer.toString(i);
                String stateString = Integer.toString(state);
                key = iString+stateString;
                char transitionAlphabet = transitions.get(key);
                char popAlphabet = popFromStack.get(key);
                char pushAlphabet = pushToStack.get(key);
                System.out.print(" with transition alphabet as: " + transitionAlphabet);
                System.out.print(" pop: " + popAlphabet);
                System.out.print(" push: " + pushAlphabet);
                System.out.println();
            }
        }
    }
    
    boolean isStringInLanguage(String inputString)
    {
        boolean reachedFinalState = false;
        int currentState;
        int nextState = 0;
        boolean epsilonTransition = false;
        int extraChar = 0;
        
        int i = 0;
        
        
        
        while (i <= inputString.length() || epsilonTransition)
        {
        	//
//			System.out.println(pdaStack);
			
				if (!epsilonTransition && i > inputString.length()) 
					break;
			
	        	for(int j = 0; j < acceptStates.size(); j++)
	            {
	        		//
//	        		System.out.println("here loop");
	        		
	        		if (nextState == acceptStates.get(j)) {
//	        			System.out.println("at accept state");
	        			reachedFinalState = true;
	        		}

//	        		System.out.println("rfs: " + reachedFinalState + ", index: " + i + ", pdaStackempty: " + pdaStack.isEmpty());
	                //if (reachedFinalState && i >= inputString.length() && pdaStack.isEmpty())
	        		if (reachedFinalState && i >= inputString.length())
	                {
	                    return reachedFinalState;
	                }
	                	                
	            }
	        	//
//	        	System.out.println("outside loop");
        	        	
        	if(epsilonTransition && i < inputString.length()){
        			i-=1;
        			epsilonTransition = false;
        	}
//			System.out.println("i is: " + i);

                        
        	currentState = nextState; 
        	
            char currentChar = 'e';
            
            if (i < inputString.length() ) {
            	currentChar = inputString.charAt(i);
            	//System.out.println("currentChar updated to: " + currentChar);
            }
            
            String currentStateString = Integer.toString(currentState);
            
            //
//            System.out.println("CurrState = " + currentStateString);
            
            int size;
            int counter = 0;
            
            //
//            System.out.println("currstates.get: " + states.get(currentState) + ", current state: " + currentState);
//            System.out.println("nextstates.get: " + states.get(nextState) + ", next state: " + nextState);
            
            
            if (states.get(currentState).isEmpty() && i > inputString.length() && !reachedFinalState) { 
            	
            	//
//            	System.out.println("false here");
            	return false;
            }
            

            
            for (int state : states.get(currentState))
            {
            	//
//            	System.out.println("Entered for state loop");
            	size = states.get(currentState).size();
            	
                String key1="";         
                String stateString = Integer.toString(state);
                key1 = currentStateString + stateString;
                
                //System.out.println("String character: " + inputString.charAt(i));
//                System.out.println("currChar: " + currentChar);
//                System.out.println("Key 1: " + key1);

                char transitionAlphabet = transitions.get(key1);
                                

                //System.out.println("transAlph = " + transitionAlphabet + ", currChar = " + currentChar);
                //if (!pdaStack.isEmpty()) System.out.println("topofstack = " + pdaStack.peek() + ", char to pop: " + popFromStack.get(key1));
                
                
                if (transitionAlphabet == currentChar && !pdaStack.isEmpty() && (pdaStack.peek() == popFromStack.get(key1) || popFromStack.get(key1) == 'e'))
                {
                	//System.out.println("Entered pop");
                    nextState = state;
                    counter++;
                    break; 
                }
                
                else if (transitionAlphabet == currentChar && pdaStack.isEmpty() && popFromStack.get(key1) == 'e') {
                	nextState = state;
                	counter++;
                	break;
                	
                }
                
                else if(transitionAlphabet == 'e')
                {
                	//System.out.println("Entered epsilon");
                	epsilonTransition = true;
                    nextState = state;
                }
                                
                else if(counter==size-1) {
                	//System.out.println("Entered no next");
                	nextState = -1;
                	reachedFinalState = false;
                	break;
                }
//                else System.out.println("none chosen");
                
                counter++;
                
            }// for loop ends here

            //System.out.println("currStates get: " + states.get(currentState).isEmpty() + ", current state");
            // current state is same as next state, string is not done, and not on final state
            if (states.get(currentState).isEmpty() && i < inputString.length()) {
//            	System.out.println("this false here");
            	return false;
            }
            
                        
            if(nextState==-1) {
            	break;
            }
                        
            StringBuilder sb = new StringBuilder();
            sb.append(currentStateString);
            sb.append(Integer.toString(nextState));
            String key2 = "";
            key2 = sb.toString();

//            System.out.println("Key 2: " + key2);
//            System.out.println("String location: " + i);
          
//            System.out.println(pdaStack);
//            System.out.println(inputString.charAt(i));
//            System.out.println(currentState);
//            System.out.println("Pop: " + popFromStack.get(key2));
            
            // if no next states available + not done with string + no epsilon transition
//            System.out.println("Next available: " + nextState);
            if (states.isEmpty() && i < inputString.length() && !epsilonTransition)
            	return false;
           
            //System.out.println("popfromstack key2: " + popFromStack.get(key2));
            if (popFromStack.get(key2) != null) {
                char transitionAlphabet = transitions.get(key2);

	            char popAlphabet = popFromStack.get(key2);
	            char pushAlphabet = pushToStack.get(key2);
	            
            
	            if(popAlphabet=='e' && pushAlphabet!='e')
	            {
//	            	System.out.println("First If - Pushing: " + pushAlphabet);
//	            	System.out.println("First If - popping: " + popAlphabet);

	            	
	            	pdaStack.push(pushAlphabet);
	            }
	            
	            else if((popAlphabet!='e' && pushAlphabet=='e' && !pdaStack.isEmpty() && pdaStack.peek() != '$') || 
	            		(pdaStack.size() == 1 && popAlphabet == '$' && currentChar == transitionAlphabet))
	            {
	            	
//	            	System.out.println("peek " + pdaStack.peek());
//	            	System.out.println("pop " + popAlphabet);
	            	if (pdaStack.peek() != popAlphabet)
	            		return false;
	            	
	            	if (!pdaStack.isEmpty()) {
//	            		System.out.println("Second If - Pushing: " + pushAlphabet);
//		            	System.out.println("Second If - popping: " + popAlphabet);
	            		pdaStack.pop();
	            	}
	            	
	            	else {
	            		extraChar++;
	            		
	            	}

	            }
	            
	            else if(popAlphabet!='e' && pushAlphabet !='e')
	            {
//	            	System.out.println("Third If - Pushing: " + pushAlphabet);
//	            	System.out.println("Third If - poppings: " + popAlphabet);
	            	pdaStack.pop();
	            	pdaStack.push(pushAlphabet);
	            }
	            
	            else if (popAlphabet!='e' && pushAlphabet=='e') {
	            	
	            	if (pdaStack.isEmpty())
	            		return false;
	            	
	            	if (!pdaStack.isEmpty()) {
//	            		System.out.println("fourth If - Pushing: " + pushAlphabet);
//		            	System.out.println("fourth If - popping: " + popAlphabet);
	            		pdaStack.pop();
	            	}
	            	
	            	else {
	            		extraChar++;
	            		
	            	}
	            	
	            }
	            
	            else {
	            	epsilonTransition = true;
	            	
	            }
	           

            }
            
            if (i >= inputString.length() -1 ) {
            	epsilonTransition = true;
            	if (i > inputString.length() + 1) {
            		epsilonTransition = false;           		
            	}
            	
            }
            //System.out.println("End of While loop");
            i++;
            
        }// end of while loop
        
        if(nextState != -1) {
        	
        	for(int j=0; j < acceptStates.size(); j++)
            {
                if (nextState == acceptStates.get(j))
                {
                    reachedFinalState = true; 
                }
            }
        }
        
        else {
        	reachedFinalState = false;
        }
 
//        System.out.println("is empty: " + pdaStack.isEmpty() + ", reachedfinalstate: " + reachedFinalState + ", extraChar: " + extraChar);
//        System.out.println(pdaStack);
        if(pdaStack.isEmpty() && reachedFinalState && extraChar==0)
        {
//        	System.out.println("final true");
            return true;
        }
        return false;
    }
}

public class PushdownAutomata {
    
    public static void main(String args[]) throws Exception
    {
		 ArrayList<Integer> acceptstates = new ArrayList<>();
		
		 System.out.println("Please input name for pda description file: ");
		 Scanner input1 = new Scanner(System.in); 
		 String fileName = input1.next();
		 
		 System.out.println("Please input name for test file: ");
		 Scanner testInput = new Scanner(System.in);
		 
		 File file = new File(fileName); 
		 Scanner input = new Scanner(file);
		 List<Integer> pdaDescription = new ArrayList<Integer>();
		 
		 // first line into array list 
		 for (int i = 0; i < 3; i++)
			 pdaDescription.add(input.nextInt());
		 
		 int numberOfStates = pdaDescription.get(0); 
		 int numberOfTransitions = pdaDescription.get(1); 
		 int startState = pdaDescription.get(2);
		 		 
		 String finalStates = input.nextLine(); 
		 String[] finalStatesArray = new String[finalStates.length()];
		 StringTokenizer st1 = new StringTokenizer(finalStates, ",");
		 
		 while (st1.hasMoreTokens()) {
			 acceptstates.add(Integer.parseInt(st1.nextToken().replaceAll("\\s", "")));
		 }	 
		 
		 States s = new States(numberOfStates, acceptstates);
		
		 while (input.hasNextLine()) {
			 String currLine = input.nextLine();
			 
			 StringTokenizer st2 = new StringTokenizer(currLine, " ");


			 int firstState = Integer.parseInt(st2.nextToken());

			 int secondState = Integer.parseInt(st2.nextToken());

			 char firstChar = st2.nextToken().toCharArray()[0];

			 char secondChar = st2.nextToken().toCharArray()[0];

			 char thirdChar = st2.nextToken().toCharArray()[0];

			 s.addState(firstState, secondState, firstChar, secondChar, thirdChar);
		 }
		 
		 input.close();
		 
		 String fileName2 = testInput.next();
		 
		 File file2 = new File(fileName2); 
		 Scanner input2 = new Scanner(file2);
		 
		 int numberToTest = input2.nextInt();
		 //System.out.println(numberToTest);
		 String rand = input2.nextLine();
		 
		 for (int i = 0; i < numberToTest; i++) {
			 
			 String currLine = input2.nextLine();
			 //System.out.println("CurrLine: " + currLine);
			 boolean answer = s.isStringInLanguage(currLine);
		     System.out.println(answer);
		     //System.out.println("------------------------------");
		     s.pdaStack.clear();

		 }
 

		 input2.close();
    
    }
}
