package com.training.controller.API;

import com.google.gson.Gson;
import com.training.entities.Attendance;
import com.training.entities.Class;
import com.training.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AttendanceControllerApi {

    @Autowired
    private AttendanceService attendanceService;

    @GetMapping("api/attendance/{traineeId}")
    public ResponseEntity<List<Attendance>> findAll(@PathVariable Integer traineeId) {
        return ResponseEntity.ok(attendanceService.findAttendancesByUserId(traineeId));
    }
}
