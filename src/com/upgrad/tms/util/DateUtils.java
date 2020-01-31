package com.upgrad.tms.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public interface DateFormat {
    public static final String YEAR_MONTH_DAY_HOUR_MIN_HYPHEN_SEPARATED = "yyyy-MM-dd HH:mm";
    public static final String DAY_MONTH_YEAR_HOUR_MIN_SLASH_SEPARATED = "dd/MM/yyyy HH:mm";
    }

    private DateUtils() {
        //Nothing to be done here
    }

    public static Date getFormattedDate(String dateString, String format) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        try {
           return formatter.parse(dateString);
        } catch (ParseException e) {
            throw e;
        }
    }
}
