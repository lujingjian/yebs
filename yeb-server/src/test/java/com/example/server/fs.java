package com.example.server;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

/**
 * <  >
 */
public class fs {

    public static String hfhf(Date date) {

        SimpleDateFormat simple = new SimpleDateFormat("yyyy_mm_dd");
        Calendar instance = Calendar.getInstance();
        System.out.println(instance.getTime());

        instance.setTime(date);

        return SimpleDateFormat.getTimeInstance().format(date);
    }

    public static void main(String[] args) {

        //今天
        LocalDate now = LocalDate.now();
        //上一周
        LocalDate sd = now.minusWeeks(2);
        System.out.println(sd);

    }

}
