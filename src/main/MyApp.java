package main;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

import LinkedList.ListaLinearLigada;
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

public class MyApp extends Application {

	private static final Font FONT = Font.font(18);

    private QuestionPane qPane = new QuestionPane();
    private QuestionStackPane sPane = new QuestionStackPane();
    private int count = 0;
    private LevelPane lPane = new LevelPane();
    Timer timer = new Timer();
    int time = 5;
    Text timeText;
    private int levelIndex = 1;
    private int questionCounter = 0;

    private Parent createContent() {
    	
        HBox root = new HBox(50);
        root.setPadding(new Insets(50, 50, 50, 50));
        
        timeText = new Text();
        timeText.setFont(FONT);
        
        Text levelText = new Text("Nível: 1");
        lPane.getChildren().add(levelText);
        
        nextQuestion(true);
        
        root.getChildren().addAll(timeText, levelText, qPane, sPane);
        
        return root;
    }

    private void nextQuestion(boolean first) {
    	timer.schedule(new RandomQuestion(), 1000, 1000);
    	
    	if (time <= 0) {
    		timer.schedule(new RandomQuestion(), 1000, 1000);
    		time = 5;
    	}
    }

    private class QuestionPane extends VBox {
    	
        private Text text = new Text();
        private ListaLinearLigada buttons = new ListaLinearLigada();
        private Question current;

        public QuestionPane() {
            super(20);

            text.setFont(FONT);

            HBox hbox = new HBox();
            for (int i = 0; i < 4; i++) {
                Button btn = new Button();
                btn.setFont(FONT);
                btn.setPrefWidth(120);
                btn.setOnAction(event -> {
                    if (btn.getText().equals(current.getCorrectAnswer())) {
                        nextQuestion(false);
                    }
                    else {
                        System.out.println("Resposta incorreta");
                    }
                });

                buttons.add(btn);
                hbox.getChildren().add(btn);
            }

            setAlignment(Pos.CENTER);
            getChildren().addAll(text, hbox);
        }

        public void setQuestion(Question question) {
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
    
    public void randomQuestion(int decimal1, int decimal2, int count) {
    	
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
        
        if (count > 2) {
        	answers.add(result2String);
            answers.add("7");
            answers.add("-2");
            answers.add("12");
        	
        	qPane.setQuestion(new Question("Quanto e: " + num1 + " - " + num2 + " + " + num3 + " ?" , answers));
            sPane.selectNext();
            
        } else if (count > 3) {
        	answers.add(result3String);
            answers.add("2");
            answers.add("-4");
            answers.add("13");
        	
        	qPane.setQuestion(new Question("Quanto e: " + num1 + " + " + num2 + " * " + num3 + " ?", answers));
            sPane.selectNext();
            
        } else {
        	answers.add(result1String);
            answers.add("5");
            answers.add("-3");
            answers.add("10");
            
        	qPane.setQuestion(new Question("Quanto e: " + num1 + " + " + num2 + " ?", answers));
            sPane.selectNext();
        }
        
        questionCounter++;
        
        if (questionCounter > 15) {
        	lPane.getChildren().clear();
        	Text levelText = new Text("Nível: "+(++levelIndex));
            lPane.getChildren().add(levelText);
            questionCounter = 1;
        }
    }
    
    private class RandomQuestion extends TimerTask {
        public void run() {
        	time--;
        	timeText.setText("Tempo: " + time);
        	System.out.println(time);
        	Platform.runLater(new Runnable() {
				@Override
				public void run() {
					randomQuestion(1, 9, count);
		    		count++;
		    		
		    		if (time <= 0) {
		        		timer.cancel();
		        	}
				}
        	});
        }
    }
}
