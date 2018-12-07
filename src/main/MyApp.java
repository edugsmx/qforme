package main;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import linkedlist.ListaLinearLigada;

public class MyApp extends Application {

	private static final Font FONT = Font.font(18);

    private QuestionPane qPane = new QuestionPane();
    private CurrentQuestionPane sPane = new CurrentQuestionPane();
    private HBox root;
    private Timer timer = new Timer();
    private QuestionNode current;
    private Text timeText;
    private Text levelText;
    private Text lifeText;
    private Text youLooseText;
    private int time = 6;
    private int levelIndex = 1;
    private int questionCounter = 0;
    private int lifeCount = 3;

    private Parent createContent() {

        root = new HBox(50);
        root.setPadding(new Insets(50, 50, 50, 50));
        
        timeText = new Text();
        timeText.setFont(FONT);
        timeText.setText("Tempo: " + time);
        
        levelText = new Text();
        levelText.setFont(FONT);
        levelText.setText("Level: " + levelIndex);
        
        lifeText = new Text();
        lifeText.setFont(FONT);
        lifeText.setText("Vidas: " + lifeCount);
        
        nextQuestion(true);
        
        root.getChildren().addAll(timeText, lifeText, qPane, levelText, sPane);
        
        timer.schedule(new RandomQuestion(), 1000, 1000);
        
        return root;
    }

    private void nextQuestion(boolean first) {
    	randomQuestion(1, 9, levelIndex);
    }

    class QuestionPane extends VBox {
    	
        private Text text = new Text();
        private Text answer = new Text();
        private ListaLinearLigada buttons = new ListaLinearLigada();

        public QuestionPane() {
            super(20);

            text.setFont(FONT);
            answer.setFont(FONT);

            HBox hbox = new HBox();
            for (int i = 0; i < 4; i++) {
                Button btn = new Button();
                btn.setFont(FONT);
                btn.setPrefWidth(120);
                btn.setOnAction(event -> {
                    if (btn.getText().equals(current.getCorrectAnswer())) {
                        nextQuestion(false);
                        time = 6;
                    }
                    else {
                        System.out.println("Resposta incorreta");
                        lifeCount--;
                        lifeText.setText("Vidas: " + lifeCount);
                    }
                });

                buttons.add(btn);
                hbox.getChildren().addAll(btn);
            }

            setAlignment(Pos.CENTER);
            getChildren().addAll(text, hbox);
        }

        public void setQuestion(QuestionNode question) {
            current = question;
            text.setText(question.name);
            buttons = buttons.shuffle();

            for(int i=0; i<buttons.size(); i++) {
            	Button btn = (Button) buttons.get(i);
                btn.setText((String) question.answers.get(i));
            }
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    public void randomQuestion(int decimal1, int decimal2, int levelIndex) {
    	
    	ListaLinearLigada answers = new ListaLinearLigada();
    
    	int num1 = ThreadLocalRandom.current().nextInt(decimal1, decimal2);
    	int num2 = ThreadLocalRandom.current().nextInt(decimal1, decimal2);
    	int num3 = ThreadLocalRandom.current().nextInt(decimal1, decimal2);
    	int resultLevel1 = num1 + num2;
    	int resultLevel2 = num1 - num2 + num3;
    	int resultLevel3 = num1 + num2 * num3;
    	
    	String result1String = String.valueOf(resultLevel1);
    	String result2String = String.valueOf(resultLevel2);
    	String result3String = String.valueOf(resultLevel3);
        
    	if (levelIndex > 0) {
    		answers.add(result1String);
            answers.add("5");
            answers.add("-3");
            answers.add("10");
            
            questionCounter++;
        	qPane.setQuestion(new QuestionNode("Quanto e: " + num1 + " + " + num2 + " ? ", answers));
            sPane.selectNext();
            
    	} else if (levelIndex > 1) {
        	answers.add(result2String);
            answers.add("7");
            answers.add("-2");
            answers.add("12");
        	
            questionCounter++;
        	qPane.setQuestion(new QuestionNode("Quanto e: " + num1 + " - " + num2 + " + " + num3 + " ? ", answers));
            sPane.selectNext();
            
        } else if (levelIndex > 2) {
        	answers.add(result3String);
            answers.add("2");
            answers.add("-4");
            answers.add("13");
        	
            questionCounter++;
        	qPane.setQuestion(new QuestionNode("Quanto e: " + num1 + " + " + num2 + " * " + num3 + " ? ", answers));
            sPane.selectNext();
        }
    	
        if (questionCounter > 14) {
        	levelIndex++;
        	questionCounter = 0;
            levelText.setText("Level: " + levelIndex);
        }
    }
    
    public void restart() {
    	
    	time = 6;
    	lifeCount = 3;
    	lifeText.setText("Vidas: " + lifeCount);
    	questionCounter = 0;
    	levelIndex = 0;
    	
    	youLooseText = new Text();
		youLooseText.setFont(FONT);
		youLooseText.setText("Você Perdeu!");
    	
    	Button restart = new Button();
    	restart.setFont(FONT);
    	restart.setPrefWidth(120);
    	restart.setText("Reiniciar");
    	restart.setOnAction(event -> {
    		timer = new Timer();
    		root.getChildren().clear();
    		root.setPadding(new Insets(50, 50, 50, 50));
    		root.getChildren().addAll(timeText, lifeText, qPane, levelText, sPane);
    		timer.schedule(new RandomQuestion(), 1000, 1000);
        });
    	
    	root.setPadding(new Insets(200, 0, 0, 400));
    	root.getChildren().addAll(youLooseText, restart);
    }
    
    class RandomQuestion extends TimerTask {
        public void run() {
        	Platform.runLater(new Runnable() {
				@Override
				public void run() {
					time--;
		        	timeText.setText("Tempo: " + time);
		        	
		        	if (time < 1) {
		        		randomQuestion(1, 9, levelIndex);
		        		lifeCount--;
		        		lifeText.setText("Vidas: " + lifeCount);
		        		time = 6;
		        	}
		        	
		        	if (lifeCount < 1) {
		        		timer.cancel();
		        		root.getChildren().clear();
		        		restart();
		        	}
				}
        	});
        }
    }
}
