package com.training.service.impl;

import com.training.entities.Attendance;
import com.training.repository.AttendanceRepository;
import com.training.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendanceServiceImpl implements AttendanceService {
    @Autowired
    private AttendanceRepository attendanceRepository;
    @Override
    public void save(Attendance attendance) {
        attendanceRepository.save(attendance);
    }

    @Override
    public Integer countAttendancePerMonth(Integer month) {
        return null;
    }

    @Override
    public List<Attendance> findAttendancesByUserId(Integer userId) {
        return attendanceRepository.findAttendancesByUserId(userId);
    }
}
