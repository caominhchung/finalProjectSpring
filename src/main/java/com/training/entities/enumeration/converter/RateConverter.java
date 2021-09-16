package com.training.entities.enumeration.converter;

import com.training.entities.enumeration.Rate;
import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;

public class RateConverter implements AttributeConverter<Rate, String> {
    @Override
    public String convertToDatabaseColumn(Rate rate) {
        if(rate!=null){
            return rate.getValue();
        }
        return null;
    }

    @Override
    public Rate convertToEntityAttribute(String s) {
        if(!StringUtils.isEmpty(s)){
            return Rate.getRateByValue(s);
        }
        return null;
    }
}
