package com.secure.secureservice.domain.converters;

import com.secure.secureservice.domain.enums.Role;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.Objects.isNull;

@Converter
public class RoleConverter implements AttributeConverter<List<Role>, String> {
    private static final String DELIMITER = ",";

    @Override
    public String convertToDatabaseColumn(List<Role> attribute) {
        if (isNull(attribute) || attribute.isEmpty()) {
            return null;
        } else {
            final StringBuilder sb = new StringBuilder();
            attribute.forEach(sb::append);
            sb.deleteCharAt(sb.length() - 1);
            return sb.toString();
        }
    }

    @Override
    public List<Role> convertToEntityAttribute(String dbData) {
        if (isNull(dbData) || dbData.isEmpty()) {
            return Collections.emptyList();
        } else {
            return Arrays.stream(dbData.split(DELIMITER)).map(Role::valueOf).toList();
        }
    }
}
