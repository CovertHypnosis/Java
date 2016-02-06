/** Program reads a file hangman.txt and if it doesn't exists it will display a game hangman with random words*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javafx.animation.PathTransition;
import javafx.animation.Transition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Hangman extends Application {
	@Override
	public void start(Stage primaryStage) {
		Hangman pane = new Hangman();
		
		pane.setOnKeyPressed(e -> pane.sendKeyCode(e.getCode()));
		Scene scene = new Scene(pane, 400, 400);
		primaryStage.setTitle("Hangman");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
		
		pane.requestFocus();
	}
	
	private class Hangman extends Pane {
		File source = new File("Hangman.txt");
		ArrayList<String> wordsInList = new ArrayList<>();
		ArrayList<Character> guessedList = new ArrayList<>(); // list of guessed characters
		ArrayList<Character> missedList = new ArrayList<>();  // list of missed characters
		
		// Default width and Height of Pane
		private double width = 400;
		private double height = 400;
		
		// Hanger part
		Arc pedestal;
		Line line1;
		Line line2;
		Line line3;
		
		// Man part
		Circle head;
		Line hand1; 		// Left hand
		Line hand2;  		// Right hand
		Line body;
		Line leg1;			// Left leg
		Line leg2;			// Right leg
		
		// Path transition for moving hanged man
		PathTransition ptMove;
		
		// Messages
		Label lblGuessWord = new Label();
		Label lblMissedLetters = new Label();
		
		boolean isPlaying = true;
		String currentWord;
		
		public Hangman() {
			startGame();
		}

		private void startGame() {
			// clear
			getChildren().clear();
			guessedList.clear();
			missedList.clear();
			
			
			// Check File for words
			if (source.exists()) {
				try (Scanner reader = new Scanner(source)){
					
					while (reader.hasNext()) {
						String temp = reader.next();
						wordsInList.add(temp);
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			} else {
				String[] randWords = { "Elepthant", "Ant", "Carpet", "Car",  "Love"};
				wordsInList.addAll(Arrays.asList(randWords));
			}
			
			// get random word
			currentWord = wordsInList.get((int)(Math.random() * wordsInList.size()));
			
			lblGuessWord.setText(getGuessWord());
			lblMissedLetters.setText("");
			
			lblGuessWord.setLayoutX(width * 0.4);
			lblGuessWord.setLayoutY(height * 0.8);
			
			lblMissedLetters.setLayoutX(width * 0.4);
			lblMissedLetters.setLayoutY(height * 0.8 * 1.05);
			
			getChildren().addAll(lblGuessWord, lblMissedLetters);
			draw();
			
		}

		private void draw() {			
			pedestal = new Arc(width * 0.2, height * 0.9, width * 0.15, height * 0.15, 0, 180);
			pedestal.setFill(Color.TRANSPARENT);
			pedestal.setStroke(Color.BLACK);
			
			line1 = new Line(pedestal.getCenterX(), pedestal.getCenterY() - pedestal.getRadiusY(),
					pedestal.getCenterX(), height * 0.05);
			line2 = new Line(line1.getEndX(), line1.getEndY(), width * 0.6, line1.getEndY());
			line3 = new Line(line2.getEndX(), line2.getEndY(), line2.getEndX(), line2.getEndY() + height * 0.1);
			
			getChildren().addAll(pedestal, line1, line2, line3);
			

		}
		
		public void drawMan(int guess) {
			switch (guess) {
				case 1: drawHead();
					break;
				case 2: drawBody();
					break;
				case 3: drawLeftArm();
					break;
				case 4: drawRightArm();
					break;
				case 5: drawLeftLeg();
					break;
				case 6: drawRightLeg();
					break;
				case 7: animateHang();
					break;
			}
		}
		
		private void animateHang() {
        		 head.translateXProperty().addListener((observable, oldValue, newValue) -> {
                	 	body.setTranslateX(newValue.doubleValue());
        			hand1.setTranslateX(newValue.doubleValue());
                		hand2.setTranslateX(newValue.doubleValue());
                		leg1.setTranslateX(newValue.doubleValue());
        	    		leg2.setTranslateX(newValue.doubleValue());
            	});

        		 head.translateYProperty().addListener((observable, oldValue, newValue) -> {
        			 body.setTranslateY(newValue.doubleValue());
        			 hand1.setTranslateY(newValue.doubleValue());
        			 hand2.setTranslateY(newValue.doubleValue());
        			 leg1.setTranslateY(newValue.doubleValue());
                		 leg2.setTranslateY(newValue.doubleValue());
        	});
			
		Arc arc = new Arc(line3.getEndX(), line3.getEndY() + head.getRadius() - 10, 20, 10, 220, 85);
            	arc.setFill(Color.TRANSPARENT);
        	arc.setStroke(Color.BLACK);
        	ptMove= new PathTransition(Duration.seconds(2), arc, head);
        	ptMove.setCycleCount(Transition.INDEFINITE);
            	ptMove.setAutoReverse(true);
            	ptMove.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
            	ptMove.play();
            	
		}
		
		private void drawHead() {
			double radius = width * 0.1;
			head = new Circle(line3.getEndX(), line3.getEndY() + radius, radius, Color.TRANSPARENT);
			head.setStroke(Color.BLACK);
			getChildren().add(head);
		}

		private void drawBody() {
			double startY = head.getCenterY() + head.getRadius();
			double x = head.getCenterX();
			body = new Line(x, startY, x, startY + height * 0.25);
			this.getChildren().add(body);
		}

		private void drawLeftArm() {
			hand1 = createArm(-1, 225);
			this.getChildren().add(hand1);
		}

		private void drawRightArm() {
			hand2 = createArm(1, 315);
			this.getChildren().add(hand2);
		}

		private void drawLeftLeg() {
			leg1 = createLeg(-1);
			this.getChildren().add(leg1);
		}

		private void drawRightLeg() {
			leg2 = createLeg(1);
			this.getChildren().add(leg2);
		}

		private Line createLeg(int dX) {
			double x = body.getEndX();
			double y = body.getEndY();
			return new Line(x, y, x + head.getRadius() * dX, y + width * 0.2);
		}

		private Line createArm(int dX, double angle) {
			double radius = head.getRadius();
			double x = head.getCenterX() + radius * Math.cos(Math.toRadians(angle));
			double y = head.getCenterY() - radius * Math.sin(Math.toRadians(angle));
			return new Line(x, y, x + radius * dX, y + radius * 1.5);
		}

		private void guess(char c) {
			if (!isPlaying)
				return;
			
			if (isRepeated(c))
				return;
			
			guessedList.add(c);
			
			String word = getGuessWord();
			
			if (word.equalsIgnoreCase(lblGuessWord.getText())) {
				missedList.add(c);
				
				if (missedList.size() == 7) {
					lblGuessWord.setText("The word is: " + currentWord);
					lblMissedLetters.setText("You lost! To continue the game, press ENTER");
					isPlaying = false;
				} else {
					lblMissedLetters.setText(lblMissedLetters.getText() + Character.toLowerCase(c));
				}
				
				drawMan(missedList.size());
			} else {
				lblGuessWord.setText(word);
				
				if (word.equalsIgnoreCase("Guess a word: " + currentWord)) {
					lblGuessWord.setText("The word is: " + currentWord);
					lblMissedLetters.setText("You won! To continue the game, press ENTER");
					isPlaying = false;
				}
			}
			
			
		}
		
		private boolean isRepeated(char c) {
			c = Character.toUpperCase(c);
			for (char ch : guessedList) {
				if (ch == c)
					return true;
			}
			
			return false;
		}
		
		
		private String getGuessWord() {
			StringBuilder temp = new StringBuilder("Guess a word: ");
			for (int i = 0; i < currentWord.length(); i++) {
				boolean isMatched = false;
				for (char c : guessedList) {
					if (Character.toLowerCase(c) == Character.toLowerCase(currentWord.charAt(i))) {
						temp.append(currentWord.charAt(i));
						isMatched = true;
						break;
					}
				}
				
				if (!isMatched) {
					temp.append("*");
				}
			}
			
			return temp.toString();
		}
		
		public void sendKeyCode(KeyCode key) {
			if (key == KeyCode.ENTER && !isPlaying) {
				isPlaying = true;
				startGame();
				ptMove.stop();
			} else if (key.isLetterKey()) {
				guess(key.getName().charAt(0));
			}
		}
		
	}
	
	// Main function
	public static void main(String[] args) {
		launch(args);
	}
}
