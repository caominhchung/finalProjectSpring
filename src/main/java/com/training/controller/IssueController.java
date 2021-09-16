package com.training.controller;

import com.training.dto.CreateIssueDto;
import com.training.dto.UpdateIssueDto;
import com.training.entities.Issue;
import com.training.service.ClassService;
import com.training.service.IssueService;
import com.training.service.impl.IssueServiceIplm;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * author HuyLQ21
 */
@Controller
@RequestMapping("/admin")
public class IssueController {

    @Autowired
    private IssueService issueService;

    @Autowired
    private ClassService classService;

    private ModelMapper modelMapper = new ModelMapper();

    /**
     * author HuyLQ21
     * @tode: use to create a issue
     */
    @PostMapping("/issue/createIssue")
    public String createIssue(@ModelAttribute @Valid CreateIssueDto dto, BindingResult bindingResult) throws Exception {

        if(null == dto.getClassId()){
            throw new Exception("Id of class is null");
        }

        Issue issue = modelMapper.map(dto, Issue.class);
        issue.setId(null);
        issue.setClasss(classService.findById(dto.getClassId()));

        issueService.createIssue(issue);
        return "redirect:/admin/class-management/"+dto.getClassId()+"?showModal=true&createIssue=true";
    }

    /**
     * author HuyLQ21
     * @tode: use to update a issue
     */
    @PostMapping("/issue/updateIssue")
    public String updateDto(@ModelAttribute UpdateIssueDto dto){
        Issue issue = issueService.findById(dto.getId());
        issue.setContent(dto.getContent());
        issueService.updateIssue(issue);
        return "redirect:/admin/class-management/"+dto.getClassId()+"?showModal=true&updateIssue=true&idIssue="+dto.getId();
    }

    /**
     * author HuyLQ21
     * @tode: use to delete a issue
     */
    @GetMapping("/issue/deleteIssue")
    public String deleteIssue(@RequestParam(name = "idDelete") Integer idDetete, @RequestParam(name = "classId") Integer classId){
        Issue issue = issueService.findById(idDetete);
        issueService.deleteIssue(issue);
        return "redirect:/admin/class-management/"+classId+"?showModal=true&deleteIssue=true";
    }
}
