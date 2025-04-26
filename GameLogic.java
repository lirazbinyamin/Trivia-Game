import java.util.HashSet;
import java.util.Set;

public class GameLogic{
	
	private int points; // Integer to hold game points 
	private Question questionManager; 
	private Set<Integer> usedQuestions = new HashSet<>(); // HashSet to store used questions indexes
	private int currentQuestionIndex; 
	
	// Constructor
	public GameLogic(Question questionManager) {
		this.questionManager = questionManager;
		this.points=0;
	}
	
	// Method to calculate points based on user answer (right/wrong)
	public void calculatePoints(boolean rightAnswer) {
		if (rightAnswer) 
			points += 10;
		else{
			if (points>0) //Assuming points can't go below 0
				points -= 5;
		}			
	}
	
	// Method to get current points of the game
	public int getPoints() {
		return points;
	}
	
	// Method to reset points of the game
	public int resetPoints() {
		return points=0;
	}
	
	// Method to reset used questions list
	public void resetUsedQuestions() {
		usedQuestions.clear();
	}
	
	// Boolean to check if is there any question from the bank which hasn't been used yet, returns true if there is
	public boolean hasMoreQuestions() {
		return usedQuestions.size() < questionManager.questionBank.size();
	}
	
	// Method to load new unused question and mark current question as used, returns new question
	public Question loadNewQuestion() {
	    if (!hasMoreQuestions()) {
	        return null; // No more questions
	    }
	    do {
	        currentQuestionIndex = questionManager.getRandomQuestionIndex(); // Get a random unused question
	    } while (usedQuestions.contains(currentQuestionIndex)); // Make sure it's not used already

	    usedQuestions.add(currentQuestionIndex); // Add current question to used questions set using it's index
	    return questionManager; 
	}
	
	// Method to return currentQuestionIndex
	public int getCurrentQuestionIndex() {
		return currentQuestionIndex;
	}

}
