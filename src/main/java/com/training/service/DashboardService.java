package com.training.service;

import com.training.dto.DashboardClass;
import com.training.entities.enumeration.ClassTypeName;

import java.util.Date;
import java.util.List;

public interface DashboardService {
    List<DashboardClass> countStatusByClass();

    List<DashboardClass> countStatusByClassType(ClassTypeName classTypeName);

    List<DashboardClass> countStatusByClassTypeAndDate(ClassTypeName classTypeName,
                                                       Date startDate,
                                                       Date endDate);
}
