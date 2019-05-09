package com.example.moodtracker;

import org.threeten.bp.LocalDate;

public class DateUtils {

    public static long getTodaysDateAsLong(LocalDate date){


        return date.getYear() * 1_000 + date.getMonthValue() * 100 + date.getDayOfMonth();

    }

}
