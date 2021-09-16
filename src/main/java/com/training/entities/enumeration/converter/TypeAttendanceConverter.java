package com.training.entities.enumeration.converter;

import com.training.entities.enumeration.TypeAttendance;
import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;

public class TypeAttendanceConverter implements AttributeConverter<TypeAttendance, String> {
    @Override
    public String convertToDatabaseColumn(TypeAttendance typeAttendance) {
        if(typeAttendance!=null){
            return typeAttendance.getValue();
        }
        return null;
    }

    @Override
    public TypeAttendance convertToEntityAttribute(String s) {
        if(!StringUtils.isEmpty(s)){
            return TypeAttendance.getTypeAttendanceByValue(s);
        }
        return null;
    }
}
