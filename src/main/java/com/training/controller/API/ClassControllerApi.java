package com.training.controller.API;

import com.training.dto.DashboardClass;
import com.training.entities.Class;
import com.training.entities.enumeration.ClassTypeName;
import com.training.service.ClassService;
import com.training.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/class")
public class ClassControllerApi {
    @Autowired
    private ClassService classService;
    @Autowired
    private DashboardService dashboardService;

    @GetMapping
    public ResponseEntity<List<Class>> findAll() {
        return ResponseEntity.ok(classService.findAll());
    }

    @GetMapping("/count-status")
    public ResponseEntity<List<DashboardClass>> countAll() {
        return ResponseEntity.ok(dashboardService.countStatusByClass());
    }

    @GetMapping("/count-type/{type}")
    public ResponseEntity<List<DashboardClass>> countAllByClassType(@PathVariable("type") String type) {
        return ResponseEntity.ok(dashboardService.countStatusByClassType(ClassTypeName.valueOf(type)));
    }

    @PostMapping("/dashboard")
    public Map<Integer, List<DashboardClass>> countAllByClassTypeAndDate(
           @RequestParam("startDate") String startDate,
           @RequestParam("endDate") String endDate
    ) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Map<Integer, List<DashboardClass>> classes = new HashMap<>();
        classes.put(1, dashboardService.countStatusByClassTypeAndDate(ClassTypeName.Fresher,
                sdf.parse(startDate), sdf.parse(endDate)));
        classes.put(2,dashboardService.countStatusByClassTypeAndDate(ClassTypeName.Internship,
                sdf.parse(startDate), sdf.parse(endDate)));
        return classes;
    }
}
