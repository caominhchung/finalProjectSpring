package com.training.dto;


import com.training.entities.enumeration.StatusOfClass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DashboardClass {
    private Long count;
    private StatusOfClass type;
}
