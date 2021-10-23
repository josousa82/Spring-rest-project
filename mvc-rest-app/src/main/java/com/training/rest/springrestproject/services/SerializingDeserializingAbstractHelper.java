package com.training.rest.springrestproject.services;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SerializingDeserializingAbstractHelper {

    public static String asJsonString(final Object obj){
        try {
            return new ObjectMapper().writeValueAsString(obj);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
