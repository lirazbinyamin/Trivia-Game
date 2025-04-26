import java.io.IOException;
import java.util.Random;

public class Question extends Data {
	
	// Constructor
	public Question(String filePath) throws IOException {
		
		super(filePath); 
	}
	
	// Method to return random question index from question bank
	public int getRandomQuestionIndex() {
			
		Random rand = new Random();
		return rand.nextInt(questionBank.size());
	}
	
	// Method to return question based on given index
	public String getQuestion(int questionIndex) {
		
		return questionBank.get(questionIndex);
	}
	
	// Method to return correct answer for a question, based on given question index
	public String getCorrectAnswer(int questionIndex) {
	    return correctAnswerBank.get(questionIndex);
	}
	
	// Method to return first wrong answer for a question, based on given question index
	public String getFirstWrongAnswer(int questionIndex) {
		return wrongAnswerBank.get(questionIndex*3);
	}
	
	// Method to return second wrong answer for a question, based on given question index
	public String getSecondWrongAnswer(int questionIndex) {
		return wrongAnswerBank.get(questionIndex*3+1);
	}
	
	// Method to return third wrong answer for a question, based on given question index
	public String getThirdWrongAnswer(int questionIndex) {
		return wrongAnswerBank.get(questionIndex*3+2);
	}
	
}
