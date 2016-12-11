package com.example.afinochka.weatherapp.Parsers;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateParser {

    enum DAY_OF_WEEK{
        su, mo, tu, we, th, fr, sa;
    }

    private static Calendar mCurrentDate = Calendar.getInstance();

    public static String weekDayByDate(String date) {

        String[] buffer = date.split("\\D");
        Calendar calendar = new GregorianCalendar();

        int year = Integer.parseInt(buffer[0]);
        int month = Integer.parseInt(buffer[1]) - 1;
        int day = Integer.parseInt(buffer[2]);

        calendar.set(year, month, day);

        int weekDay = calendar.get(Calendar.DAY_OF_WEEK);

        return DAY_OF_WEEK.values()[weekDay - 1].toString();
    }

    public static String dayOrNight(String date){
        String[] buffer = date.split("\\D");

        String hour = buffer[3];
        String empty = "";
        String night = "Night";

        switch (hour){
            case "00":
                return night;
            case "03":
                return night;
            case "06":
                return night;
            case "12":
                return empty;
            case "15":
                return empty;
            case "18":
                return empty;
            case "21":
                return night;
            default:
                return empty;
        }
    }

    public static String parseDateFromEnglishDate(String date){

        String[] buffer = date.split("\\D");

        int month = Integer.parseInt(buffer[1]);
        int day = Integer.parseInt(buffer[2]);

        return day + "." + month;
    }

    public static String getCurrentDateByEnglishFormat() {
        int mMonth = mCurrentDate.get(Calendar.MONTH) + 1;
        int mDay = mCurrentDate.get(Calendar.DAY_OF_MONTH);
        return mMonth + "-" + mDay;
    }

}
