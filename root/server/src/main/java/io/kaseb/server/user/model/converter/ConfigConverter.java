package io.kaseb.server.user.model.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.kaseb.server.user.model.Config;
import lombok.RequiredArgsConstructor;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
@RequiredArgsConstructor
public class ConfigConverter implements AttributeConverter<Config, String> {
    private final ObjectMapper objectMapper;

    @Override
    public String convertToDatabaseColumn(Config config) {
        try {
            return objectMapper.writeValueAsString(config);
        } catch (JsonProcessingException e) {
            return "{}";
        }
    }

    @Override
    public Config convertToEntityAttribute(String s) {
        try {
            return objectMapper.readValue(s, Config.class);
        } catch (JsonProcessingException e) {
            return new Config();
        }
    }
}
