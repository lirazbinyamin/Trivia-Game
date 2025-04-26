import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

public class TriviaController {
	
	private GameLogic game; // GameLogic to be used for all game steps
	
	//Strings to hold all question data: question, correct answer and wrong answers 
	private String questionText;
    private String correctAnswer;
    private String wrongAnswer1;
    private String wrongAnswer2;
    private String wrongAnswer3;
    
	@FXML
    private Label questionLabel;
	
	@FXML
    private GridPane answerGrid;
	
	@FXML
	private Button finishGameBtn;

	@FXML
	private Button newGameBtn;

	@FXML
	void finishGameBtnPressed(ActionEvent event) {
		finishGame();
	}

	@FXML
	void newGameBtnPressed(ActionEvent event) {
		startNewGame();
	}	
    
	// Method to initialize game
    public void initialize() {
    	
    	try {
    		Question questionManager = new Question("QuestionBank.txt"); // Get questions from file
    		game = new GameLogic(questionManager); // Initialize game
    		
    	} catch (IOException e) {
    		System.out.println("ERROR: " + e.getMessage());
    	}
    	
    	loadQuestion();
    	
    }
    
    // Method to load question to the game 
    private void loadQuestion() {
    	if (!game.hasMoreQuestions()) {
    		finishGame();
    		return;
    	}
    	Question currentQuestion = game.loadNewQuestion(); // Load the next question
        int questionIndex = game.getCurrentQuestionIndex(); // Get loaded question index
        questionToString(currentQuestion, questionIndex); // Update display with the question based on it's index
    	
    }
    
    // Method to start new game
    private void startNewGame() {
    	
    	updateButtons(true, "", null); // Re-enable buttons and reset their styles
    	questionLabel.setStyle("-fx-font-size: 18px;"); // Reset question label size
    	game.resetPoints();
    	game.resetUsedQuestions();
    	loadQuestion();
    }
    
    // Method to finish current game
    private void finishGame() {
    	
    	updateButtons(false, null, null); // Disable buttons without changing their text or style
    	questionLabel.setText("Game Over! Total Points: " + game.getPoints());
    	questionLabel.setStyle("-fx-font-size: 40px;"); //Get label size bigger for game finished
    	
    }
    
    // Method to create strings to question and it's answers, using methods based on question's index in the Question lists
  	private void questionToString(Question questionManager, int questionIndex) {
  		
  		// Get question and answers into Strings
  	    questionText = questionManager.getQuestion(questionIndex);
  	    correctAnswer = questionManager.getCorrectAnswer(questionIndex);
  	    wrongAnswer1 = questionManager.getFirstWrongAnswer(questionIndex);
  	    wrongAnswer2 = questionManager.getSecondWrongAnswer(questionIndex);
  	    wrongAnswer3 = questionManager.getThirdWrongAnswer(questionIndex);
  	        
  	    questionLabel.setText(questionText); // Update question label to current question
  	    
  	    // Add question's answers to a List and shuffle them (so wont be any specific button for correct answer and wrong answers)
  	    List<String> answers = new ArrayList<>();
  		answers.add(correctAnswer);
        answers.add(wrongAnswer1);
        answers.add(wrongAnswer2);	
  		answers.add(wrongAnswer3);
  		Collections.shuffle(answers);
  		
  		addToGridPane(answers);//Add answers to answers GridPane
  		
  		}
  	
  	// Method to add answers to answers GridPane
  	private void addToGridPane(List<String> answers) {
  		
  		// Add buttons to grid pane based on current question answers
  		updateButtons(true, "", answers); 
  		
  	}
  	
  	// Method to update buttons to be enabled/disabled on GridPane, and update their content based on answers for question
	private void updateButtons(boolean enable, String style, List<String> answers) {
	    
		for (int i = 0; i < answerGrid.getChildren().size(); i++) {
	        if (answerGrid.getChildren().get(i) instanceof Button) {
	            Button btn = (Button) answerGrid.getChildren().get(i);
	            btn.setDisable(!enable); // Enable or disable buttons
	            
	            // Apply given style (needs to be reset after color was red/green)
	            if (style != null) {
	                btn.setStyle(style); 
	            }
	            // Update button text with current question's answers
	            if (answers != null && i < answers.size()) {
	                btn.setText(answers.get(i)); 
	
	                // Pressed button event
	                btn.setOnAction(btnPressed -> handleButton(btnPressed)); 
	            }
	        }
	    }
	}
    
  	/** Method to handle pressed button on the answer GridPane, check if it's the correct answer and change its color according to that
  	 * if its correct- green, wrong answer - red**/
  	private void handleButton(ActionEvent event) {
  		
  		// Check equality of chosen answer to the correct answer of the question
  		Button clickedBtn = (Button) event.getSource();
		boolean isCorrect = clickedBtn.getText().equals(correctAnswer);
		
		// Clicked button is correct answer
		if (isCorrect)
			clickedBtn.setStyle("-fx-background-color: green;"); // Change button color to green
		// Clicked button is wrong answer
		else 
			clickedBtn.setStyle("-fx-background-color: red;"); // Change button color to red
			
		// Add 10 points for correct answer, remove 5 for wrong answer
		game.calculatePoints(isCorrect);
		//System.out.println("points: " + game.getPoints()); // Check to monitor current points status 
		
		// Wait for a short time, then load the next question
		PauseTransition pause = new PauseTransition(Duration.seconds(0.5));  // half-second pause
	    pause.setOnFinished(gamePaused -> loadQuestion());  // Load the next question after game pause
	    pause.play();
	    
	}
  	
  	

}
