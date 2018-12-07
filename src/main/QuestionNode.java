package main;

import linkedlist.ListaLinearLigada;

public class QuestionNode {
	
    public String name;
    public ListaLinearLigada answers;

    public QuestionNode(String name, ListaLinearLigada answers) {
        this.name = name;
        this.answers = answers;
    }

    public Object getCorrectAnswer() {
        return answers.get(0);
    }
}