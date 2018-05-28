package com.alpha.web.formatter;

import org.springframework.format.Formatter;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class DateFormatter implements Formatter<Date> {
    private SimpleDateFormat dateFormat;

    public DateFormatter(String datePattern) {
        dateFormat = new SimpleDateFormat(datePattern);
        dateFormat.setLenient(false);
    }

    @Override
    public Date parse(String s, Locale locale) throws ParseException {
        System.out.println("Date formatter is working: parse.........");
        return new Date(dateFormat.parse(s).getTime());
    }

    @Override
    public String print(Date date, Locale locale) {
        System.out.println("Date formatter is working: print.........");
        return dateFormat.format(date);
    }
}
