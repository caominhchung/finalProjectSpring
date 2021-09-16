package com.training.entities.enumeration.converter;

import com.training.entities.enumeration.StatusOfClass;
import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;

public class StatusOfClassConverter implements AttributeConverter<StatusOfClass,String> {
    @Override
    public String convertToDatabaseColumn(StatusOfClass statusOfClass) {

        if(statusOfClass!=null){
            return statusOfClass.getValue();
        }
        return null;
    }

    @Override
    public StatusOfClass convertToEntityAttribute(String s) {
        if(!StringUtils.isEmpty(s)){
            return StatusOfClass.getStatusByValue(s);
        }
        return null;
    }
}
