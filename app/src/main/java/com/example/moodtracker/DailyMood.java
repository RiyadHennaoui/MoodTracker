package com.example.moodtracker;

import org.threeten.bp.LocalDate;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;


public class DailyMood extends RealmObject {

    private String comment;
    private String mood;
    @PrimaryKey
    private long date;


    String getComment() {
        return comment;
    }

    void setComment(String comment) {
        this.comment = comment;
    }

    Mood getMood() {
        return mood == null ? null : Mood.valueOf(mood);
    }

    void saveMood(Mood mood) {
        this.mood = mood.name();
    }

//    public long getDate() {
//        return date;
//    }

    void setDate(LocalDate date) {
        this.date = DateUtils.getTodaysDateAsLong(date);
    }

    @Override
    public String toString() {
        return "DailyMood{" +
                "comment='" + comment + '\'' +
                ", mood='" + mood + '\'' +
                ", date=" + date +
                '}';
    }
}


