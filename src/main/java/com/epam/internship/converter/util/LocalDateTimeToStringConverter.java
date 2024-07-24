package com.epam.internship.converter.util;

import org.modelmapper.AbstractConverter;

import java.time.LocalDateTime;

import static com.epam.internship.converter.util.StringToLocalDateTimeConverter.CUSTOM_FORMATTER;
import static java.util.Objects.isNull;

public class LocalDateTimeToStringConverter extends AbstractConverter<LocalDateTime, String> {
    @Override
    protected String convert(LocalDateTime source) {
        if (isNull(source)){
            return null;
        }
        return source.format(CUSTOM_FORMATTER);
    }
}
