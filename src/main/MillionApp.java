package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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

    private Parent createContent() {
        HBox root = new HBox(50);
        root.setPadding(new Insets(50, 50, 50, 50));
        
        ListaLinearLigada answers = new ListaLinearLigada();
        answers.add("4");
        answers.add("5");
        answers.add("6");
        answers.add("-1");
        
        qPane.setQuestion(new Question("Quanto é: 2 + 2?", answers));
        
        root.getChildren().addAll(qPane, sPane);
        
        return root;
    }

    private void nextQuestion() {
    	ListaLinearLigada answers = new ListaLinearLigada();
        answers.add("6");
        answers.add("5");
        answers.add("-3");
        answers.add("10");
        qPane.setQuestion(new Question("Quanto é: 3 + 3?", answers));
        sPane.selectNext();
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
                        nextQuestion();
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
