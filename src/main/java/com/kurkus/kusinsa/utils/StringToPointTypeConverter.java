package com.kurkus.kusinsa.utils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import com.kurkus.kusinsa.enums.PointType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class StringToPointTypeConverter implements Converter<String, PointType> {

    @Override
    public PointType convert(String source) {
        log.info("작동될때가 언제인지 알기위한것 : {}", source);
        return PointType.valueOf(source.toUpperCase());
    }
}
