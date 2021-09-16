package com.training.entities.enumeration.converter;

import com.training.entities.enumeration.Gender;
import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;

public class GenderConverter implements AttributeConverter<Gender, String> {
    @Override
    public String convertToDatabaseColumn(Gender gender) {
        if(gender != null){
            return gender.getValue();
        }
        return null;
    }

    @Override
    public Gender convertToEntityAttribute(String s) {
        if(!StringUtils.isEmpty(s)){
            return Gender.getGenderByValue(s);
        }
        return null;
    }
}
