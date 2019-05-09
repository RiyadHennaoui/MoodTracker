package com.example.moodtracker;

import org.threeten.bp.LocalDate;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class DailyMood extends RealmObject {

    private String comment;
    private String mood;
    @PrimaryKey
    private long date;


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

//    public long getDate() {
//        return date;
//    }

    public void setDate(LocalDate date) {
        this.date = DateUtils.getTodaysDateAsLong(date);
    }

    public RealmResults<DailyMood> saveAllMood (){

        RealmResults<DailyMood> results = Realm.getDefaultInstance()
                .where(DailyMood.class)
                .equalTo("date", DateUtils.getTodaysDateAsLong(LocalDate.now()))
                .findAll();

        return results;
    }
}


