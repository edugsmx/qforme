package main;

import LinkedList.ListaLinearLigada;

public class Question {
	
    public String name;
    public ListaLinearLigada answers;

    public Question(String name, ListaLinearLigada answers) {
        this.name = name;
        this.answers = answers;
    }

    public Object getCorrectAnswer() {
        return answers.get(0);
    }
}