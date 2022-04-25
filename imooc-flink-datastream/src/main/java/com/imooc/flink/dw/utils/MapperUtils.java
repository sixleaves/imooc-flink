package com.imooc.flink.dw.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MapperUtils {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static ObjectMapper getInstance() {
        return objectMapper;
    }
}
