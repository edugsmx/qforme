package main;

import java.util.concurrent.ThreadLocalRandom;

import LinkedList.ListaLinearLigada;
import javafx.application.Application;
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

public class MillionApp extends Application {

	private static final Font FONT = Font.font(18);

    private QuestionPane qPane = new QuestionPane();
    private SidePane sPane = new SidePane();
    private QuestionStackPane qsPane = new QuestionStackPane();
    private LevelPane lPane = new LevelPane();

    private Parent createContent() {
        HBox root = new HBox(50);
        root.setPadding(new Insets(50, 50, 50, 50));
        
        nextQuestion(true);
        
        sPane.getChildren().addAll(lPane, qsPane);
        root.getChildren().addAll(qPane, sPane);
        
        return root;
    }

    private void nextQuestion(boolean first) {
    	ListaLinearLigada answers = new ListaLinearLigada();
    	
    	int num1 = ThreadLocalRandom.current().nextInt(1, 9);
    	int num2 = ThreadLocalRandom.current().nextInt(1, 9);
    	int result = num1 + num2;
    	String resultString = String.valueOf(result);
    	
        answers.add(resultString);
        answers.add("5");
        answers.add("-3");
        answers.add("10");
        
        qPane.setQuestion(new Question("Quanto é: " + num1 + " + " + num2 + " ?", answers));
        if (!first)
        	qsPane.selectNext();
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
                        System.out.println("Incorrect");
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
}
