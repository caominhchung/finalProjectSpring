package com.training.entities.enumeration.converter;

import com.training.entities.enumeration.Role;
import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;

public class RoleConverter implements AttributeConverter<Role,String> {
    @Override
    public String convertToDatabaseColumn(Role role) {
        if(role!=null){
            return role.getValue();
        }
        return null;
    }

    @Override
    public Role convertToEntityAttribute(String s) {
        if(!StringUtils.isEmpty(s)){
            return Role.getRoleByValue(s);
        }
        return null;
    }
}
