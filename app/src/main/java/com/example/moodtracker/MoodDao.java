package com.example.moodtracker;

import org.threeten.bp.LocalDate;

import java.util.List;

import javax.annotation.Nullable;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class MoodDao {

//  Management of Database
    @Nullable
    DailyMood getTodaysMood() {
        RealmResults<DailyMood> realmResults = Realm.getDefaultInstance()
                .where(DailyMood.class)
                .equalTo("date", DateUtils.getTodaysDateAsLong(LocalDate.now()))
                .findAll();
        if (realmResults.isEmpty()) {

            return null;
        } else {

            return realmResults.get(0);
        }
    }

    public List<DailyMood> getLastSevenDailyMoods() {

        return Realm.getDefaultInstance()
                .where(DailyMood.class)
                .sort("date", Sort.DESCENDING)
                .limit(7)
                .lessThan("date", DateUtils.getTodaysDateAsLong(LocalDate.now()))
                .findAll();
    }

    public void persistMood(Mood mood) {

        Realm.getDefaultInstance().beginTransaction();

        DailyMood dailyMood = getTodaysMood();
        if (dailyMood == null){
            dailyMood = new DailyMood();
            dailyMood.setDate(LocalDate.now());
        }
        dailyMood.saveMood(mood);

        Realm.getDefaultInstance().copyToRealmOrUpdate(dailyMood);

        Realm.getDefaultInstance().commitTransaction();
    }

    public void persistComment(String comment) {

        Realm.getDefaultInstance().beginTransaction();

        DailyMood dailyMood = getTodaysMood();
        if (dailyMood == null){
            dailyMood = new DailyMood();
            dailyMood.setDate(LocalDate.now());
        }
        dailyMood.setComment(comment);

        Realm.getDefaultInstance().copyToRealmOrUpdate(dailyMood);

        Realm.getDefaultInstance().commitTransaction();

    }
}
