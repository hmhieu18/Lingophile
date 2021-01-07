package com.example.lingophile.Models;

import java.util.ArrayList;

public class QuizCard {
    private FlashCard flashCard;
    private ArrayList<String> options;

    public QuizCard(FlashCard flashCard, ArrayList<String> options) {
        this.flashCard = flashCard;
        this.options = options;
    }

    public FlashCard getFlashCard() {
        return flashCard;
    }

    public void setFlashCard(FlashCard flashCard) {
        this.flashCard = flashCard;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }
}
