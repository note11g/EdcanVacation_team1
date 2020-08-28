package com.edcan.shareformproject.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeUtil {

    public static String getTimeByFormat(String format) {
        return new SimpleDateFormat(format, Locale.ENGLISH).format(new Date());
    }

    public static String changeFormat(String time, String f1, String f2) {
        SimpleDateFormat format = new SimpleDateFormat(f1, Locale.ENGLISH);
        try {
            return new SimpleDateFormat(f2, Locale.ENGLISH).format(format.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
