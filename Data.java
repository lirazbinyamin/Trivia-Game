import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Data {
	
	// ArrayLists to store banks of questions, correct answers and wrong answers
	protected final List<String> questionBank = new ArrayList<>();
	protected final List<String> correctAnswerBank = new ArrayList<>();
	protected final List<String> wrongAnswerBank = new ArrayList<>();
	
	// Constructor
	public Data(String filePath) throws IOException {
	    
		arrangeData(filePath);
	}

	// Method to scan file data and arrange it in the 3 Lists
    private void arrangeData(String filePath) throws IOException {
    	
    	File file = new File(filePath);
    	try (Scanner s= new Scanner(file)){
    	/** Assuming File should be organized by this following line order:
    	 * 1- question
    	 * 2- correct answer
    	 * 3,4,5- wrong answers
    	 * Repeat**/
    	int i=0;
    	while(s.hasNext()) {
    		//Adding all questions to a Bank
    		if (i%5==0) // Questions located every 5th line
    			questionBank.add(s.nextLine()); 
    		//Adding all correct answers to a Bank
    		else if (i%5==1) // Correct answers located every 5th+1 line
    			correctAnswerBank.add(s.nextLine()); 
    		//Adding all wrong answers to a Bank
    		else // Wrong answers located every 5th+2,5th+3,5th+4 line
    			wrongAnswerBank.add(s.nextLine()); 
    		i++;
			}	
    	}catch (IOException e) {
    		System.out.println("ERROR: "+ e.getMessage());
    	}
    	/**Check for all Lists
    	System.out.println("Questions: ");
    	printBank(questionBank);
    	System.out.println("Answers: ");
    	printBank(correctAnswerBank);
    	System.out.println("Wrong Answers: ");
    	printBank(wrongAnswerBank);**/
    }
    
    /** Method to print given bank and indexes of each line
    private void printBank(List<String> bank) {
    	for(int i=0;i<bank.size();i++) {
    		System.out.println(i + "- " +bank.get(i) + " ");
    	}
    }**/
}