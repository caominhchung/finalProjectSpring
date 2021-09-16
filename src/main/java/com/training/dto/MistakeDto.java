package com.training.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


/**
 * @author tungns14
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class MistakeDto {
    private Integer id;
    @NotBlank
    private String name;
    @NotBlank
    private String content;
    private String note;
}
