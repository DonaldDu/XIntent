package com.dhy.xintent;

import java.util.Calendar;

public class CalendarX {

    public static CalendarX getInstance() {
        return new CalendarX();
    }

    private Calendar calendar = Calendar.getInstance();

    /**
     * Calendar.MONTH->[1,12]
     * <br><br>Calendar.DAY_OF_WEEK->[Calendar#MONDAY, Calendar#SUNDAY] [1,7]
     */
    public int get(int field) {
        int value = calendar.get(field);
        if (field == Calendar.MONTH) {
            value++;
        } else if (field == Calendar.DAY_OF_WEEK) {
            if (value == Calendar.SUNDAY) {
                value = 7;
            } else value--;
        }
        return value;
    }

    /**
     * Calendar.MONTH->[1,12]
     * <br>Calendar.DAY_OF_WEEK->[Calendar#MONDAY, Calendar#SUNDAY] [1,7]
     */
    public void set(int field, int value) {
        if (field == Calendar.MONTH) {
            value--;
        } else if (field == Calendar.DAY_OF_WEEK) {
            if (value == 7) {
                value = Calendar.SUNDAY;
            } else value++;
        }
        calendar.set(field, value);
    }

    public int getActualMaximum(int field) {
        return calendar.getActualMaximum(field);
    }

    public void setTimeInMillis(long millis) {
        calendar.setTimeInMillis(millis);
    }

    public long getTimeInMillis() {
        return calendar.getTimeInMillis();
    }
}
