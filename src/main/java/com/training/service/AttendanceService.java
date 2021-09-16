package com.training.service;

import com.training.entities.Attendance;

import java.util.List;

public interface AttendanceService {
    void save(Attendance attendance);
    Integer countAttendancePerMonth(Integer month);
    List<Attendance> findAttendancesByUserId(Integer userId);
}
