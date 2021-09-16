package com.training.controller;

import com.training.entities.enumeration.ClassTypeName;
import com.training.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;



    @GetMapping("/dashboard")
    public String uiDashboard(ModelMap modelMap) {

        modelMap.put("listFresher", dashboardService.countStatusByClassType(ClassTypeName.Fresher));
        modelMap.put("listInternship", dashboardService.countStatusByClassType(ClassTypeName.Internship));
        //modelMap.put("classList", dashboardService.countStatusByClass());
        return "dashboard";
    }
}
