package com.example.demo.utils;

import org.apache.commons.lang3.StringUtils;

public interface IDTOToJsonConverter {
    default <T> String convert (T clazz)
    {
        return StringUtils.EMPTY;
    };
}
