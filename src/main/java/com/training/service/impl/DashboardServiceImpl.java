package com.training.service.impl;

import com.training.dto.DashboardClass;
import com.training.entities.enumeration.ClassTypeName;
import com.training.repository.ClassRepository;
import com.training.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private ClassRepository classRepository;


    @Override
    @Transactional
    public List<DashboardClass> countStatusByClass() {
        return classRepository.countClassByStatus();
    }

    @Override
    @Transactional
    public List<DashboardClass> countStatusByClassType(ClassTypeName classTypeName) {
        return  classRepository.countClassByStatusAndTypeClass(classTypeName);
    }


    @Override
    @Transactional
    public List<DashboardClass> countStatusByClassTypeAndDate(ClassTypeName classTypeName,
                                                              Date startDate,
                                                              Date endDate) {
        return  classRepository.countClassByStatusAndTypeClassAndDate(classTypeName, startDate, endDate);
    }
}
