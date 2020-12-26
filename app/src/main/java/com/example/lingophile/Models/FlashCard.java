package com.example.lingophile.Models;

import com.google.firebase.database.DataSnapshot;

public class FlashCard {

    /*
        Store data and getting setting methods
         */
    private String _word;
    private String _type;
    private String _meaning;
    private String _example;

    public FlashCard(){
        _word = "";
        _type = "";
        _meaning = "";
        _example = "";
    }
    public FlashCard(DataSnapshot snapshot){
        _word = snapshot.child("word").getValue().toString();
        _type = snapshot.child("type").getValue().toString();
        _meaning = snapshot.child("meaning").getValue().toString();
        _example = snapshot.child("example").getValue().toString();
    }

    public String getWord() {
        return _word;
    }


    public void setWord(String word) {
        this._word = word;
    }

    public String getType() {
        return _type;
    }

    public void setType(String type) {
        this._type = type;
    }

    public String getMeaning() {
        return _meaning;
    }

    public void setMeaning(String meaning) {
        this._meaning = meaning;
    }

    public String getExample() {
        return _example;
    }

    public void setExample(String example) {
        this._example = example;
    }


}
