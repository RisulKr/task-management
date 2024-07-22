package com.epam.internship.utils;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class DateTimeUtils {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public String convertDateToString(LocalDateTime now) {
        return now.format(formatter);

    }

    public LocalDateTime convertStringToDate(String str) {
        return LocalDateTime.parse(str, formatter);

    }
}
