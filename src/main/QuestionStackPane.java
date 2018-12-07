package main;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import stack.PilhaVetor;

public class QuestionStackPane extends VBox {
	
    private int current = 1;
    PilhaVetor pilha = new PilhaVetor(15);
    private int levelIndex = 0;
    
    public QuestionStackPane() {
        super(8);
        
        for (int i = 15; i > 0; i--) {
        	pilha.push(i);
            Text text = new Text(vetorToString(pilha.toArray()));
            text.setFill(i == current ? Color.BLACK : Color.GRAY);

            getChildren().add(text);
            pilha.pop();
        }     
                
    }

    public void selectNext() {
        if (current == 15) {
        	Text text = (Text)getChildren().get(15 - current);
            text.setFill(Color.GRAY);
            current = 1;
            text = (Text)getChildren().get(15 - current);
            text.setFill(Color.BLACK);
            return;
        }
        
        Text text = (Text)getChildren().get(15 - current);
        text.setFill(Color.GRAY);
        current++;
        text = (Text)getChildren().get(15 - current);
        text.setFill(Color.BLACK);
    }
    
    public static String vetorToString(Object[] e) {
		String txt = "";
		for (int i=0 ; i < e.length ; i++)
			txt += "Questão: " + e[i];
		
		return txt;
	}
}
