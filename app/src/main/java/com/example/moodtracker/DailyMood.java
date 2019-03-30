package com.example.moodtracker;

import io.realm.RealmObject;

public class DailyMood extends RealmObject {

    private String comment;
    private String mood;



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
}
