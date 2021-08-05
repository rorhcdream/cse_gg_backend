package gg.cse.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;
import java.io.IOException;

@Slf4j
public class JpaConverterJson<T> implements AttributeConverter<T, String> {
    private final static ObjectMapper objectMapper = new ObjectMapper();

    private final Class<T> classType;

    public JpaConverterJson(Class<T> classType) {
        this.classType = classType;
    }

    @Override
    public String convertToDatabaseColumn(T meta) {
        try {
            return objectMapper.writeValueAsString(meta);
        } catch (JsonProcessingException ex) {
            log.error("JsonProcessingException in JpaConverterJson");
            log.error(ex.toString());
            return null;
        }
    }

    @Override
    public T convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, classType);
        } catch (IOException ex) {
            log.error("IOException in JpaConverterJson");
            log.error(ex.toString());
            return null;
        }
    }
}