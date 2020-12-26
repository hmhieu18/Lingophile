package com.example.lingophile.Models;

public class Lesson {

    /*
        Store data and getting setting methods
         */
    private String _userID;
    private String _lessionID;
    private boolean _privacy;
    private String _title;
    private Float _rate;
    private Float _percentage;
    private String _topic;

    public Lesson(){
        _userID = "";
        _lessionID = "";
        _privacy = true;
        _title = "";
        _rate = Float.valueOf(0);
        _percentage = Float.valueOf(0);
        _topic = "";
    }



    public String getUserID() {
        return _userID;
    }

    public void setUserID(String userID) {
        this._userID = userID;
    }

    public String getLessionID() {
        return _lessionID;
    }

    public void setLessionID(String lessionID) {
        this._lessionID = lessionID;
    }

    public boolean isPublic() {
        return _privacy;
    }

    public void setPrivacy(boolean privacy) {
        this._privacy = privacy;
    }

    public String getTitle() {
        return _title;
    }

    public void setTitle(String title) {
        this._title = title;
    }

    public Float getRate() {
        return _rate;
    }

    public void setRate(Float rate) {
        this._rate = rate;
    }

    public Float getPercentage() {
        return _percentage;
    }

    public void setPercentage(Float percentage) {
        this._percentage = percentage;
    }

    public String getTopic() {
        return _topic;
    }

    public void setTopic(String topic) {
        this._topic = topic;
    }

}
