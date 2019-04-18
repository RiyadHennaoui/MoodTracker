package com.example.moodtracker;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class DailyMood extends RealmObject {

    private String comment;
    private String mood;
    @PrimaryKey
    @Required
    private String date;




    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Mood getMood() {
        return mood == null ? null : Mood.valueOf(mood);
    }

    public void saveMood(Mood mood) {
        this.mood = mood.name();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
