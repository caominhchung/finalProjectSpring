package com.training.entities.enumeration;

/**
 * @author ChungCM
 */
public enum GroupOfQuestion {
    QUESTION_1("What did you like most about this topic/course?"),
    QUESTION_2("What aspects of the topic/course could be improved?"),
    QUESTION_3("I am satisfied with the topic/course's content?"),
    QUESTION_4("The level of the course is appropriate for the trainees?"),
    QUESTION_5("The course is useful for my work?"),
    QUESTION_6("The materials distributed were pertinent and helpful?"),
    QUESTION_7("The training content is fully transferred following the courseware?"),
    QUESTION_8("The trainer is knowledgeable in the topic/course training subject area?"),
    QUESTION_9("The trainer's instructions were clear and understandable?"),
    QUESTION_10("My questions were encouraged and answered satisfactorily?"),
    QUESTION_11("How satisfied are you with the lecturer?"),
    QUESTION_12("Would you like to suggest more wishes about teaching methods to your lecturers?"),
    QUESTION_13("The Class Admin was supportive and helpful?"),
    QUESTION_14("How satisfied are you with class management?"),
    QUESTION_15("Would you like to suggest more wishes about the admin's management method?");


    private String value;

    GroupOfQuestion(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static GroupOfQuestion getGroupOfQuestionByValue(String value) {
        for (GroupOfQuestion groupOfQuestion : GroupOfQuestion.values()) {
            if (groupOfQuestion.value.equals(value)) {
                return groupOfQuestion;
            }
        }
        return null;
    }
}
