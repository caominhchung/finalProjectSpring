package com.training.entities.enumeration.converter;

import com.training.entities.enumeration.GroupOfQuestion;
import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;

/**
 * @author ChungCM
 */
public class GroupOfQuestionConverter implements AttributeConverter<GroupOfQuestion, String> {
    @Override
    public String convertToDatabaseColumn(GroupOfQuestion groupOfQuestion) {
        if (groupOfQuestion != null) {
            return groupOfQuestion.getValue();
        }
        return null;
    }

    @Override
    public GroupOfQuestion convertToEntityAttribute(String s) {
        if (!StringUtils.isEmpty(s)) {
            return GroupOfQuestion.getGroupOfQuestionByValue(s);
        }
        return null;
    }
}
