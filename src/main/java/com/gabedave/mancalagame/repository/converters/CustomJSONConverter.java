package com.gabedave.mancalagame.repository.converters;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.persistence.AttributeConverter;

public class CustomJSONConverter<T> implements AttributeConverter<T, String> {

    private static final Logger logger = LoggerFactory.getLogger(CustomJSONConverter.class);

    private static final ObjectMapper objectMapper = new ObjectMapper();

    {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.setDateFormat(new StdDateFormat().withColonInTimeZone(true));
    }

    @Override
    public String convertToDatabaseColumn(T customerInfo) {

        String jsonString = null;
        try {
            jsonString = objectMapper.writeValueAsString(customerInfo);
        } catch (final JsonProcessingException e) {
            logger.error("JSON writing error", e);
        }

        return jsonString;
    }

    @Override
    public T convertToEntityAttribute(String jsonString) {

        T entityAttribute = null;

        try {
            String temp = objectMapper.readValue(jsonString,
                    new TypeReference<String>() {
                    });

            entityAttribute = objectMapper.readValue(temp,
                    new TypeReference<T>() {
                    });
        } catch (final IOException e) {
            logger.error("JSON reading error", e);
        }
        return entityAttribute;
    }
}