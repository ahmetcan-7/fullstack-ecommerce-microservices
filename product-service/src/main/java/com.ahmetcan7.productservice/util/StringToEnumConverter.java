package com.ahmetcan7.productservice.util;

import com.ahmetcan7.productservice.enumeration.Sort;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;


@Component
public class StringToEnumConverter implements Converter<String, Sort> {
    @Override
    public Sort convert(String source) throws ConversionFailedException {
        String underscore = Arrays.stream(source.split("(?=[A-Z])")).map(String::toUpperCase)
                .collect(Collectors.joining("_"));
            return Sort.valueOf(underscore);
    }
}