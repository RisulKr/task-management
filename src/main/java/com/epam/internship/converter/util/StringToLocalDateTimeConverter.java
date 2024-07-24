package com.epam.internship.converter.util;

import org.modelmapper.AbstractConverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StringToLocalDateTimeConverter extends AbstractConverter<String, LocalDateTime> {
    public final static DateTimeFormatter CUSTOM_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    @Override
    protected LocalDateTime convert(String source) {
        return LocalDateTime.parse(source, CUSTOM_FORMATTER);
    }
}
