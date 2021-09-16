package com.training.controller;

import com.training.dto.CreateIssueDto;
import com.training.dto.CreateSolutionDto;
import com.training.dto.UpdateIssueDto;
import com.training.dto.UpdateSolutionDto;
import com.training.entities.Solution;
import com.training.service.IssueService;
import com.training.service.SolutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/solution")
public class SolutionController {

    @Autowired
    private SolutionService solutionService;

    @Autowired
    private IssueService issueService;

    @PostMapping("/updateSolution")
    public String updateSolution(@ModelAttribute UpdateSolutionDto updateSolutionDto){
        Solution solution = solutionService.getSolutionById(updateSolutionDto.getId());
        solution.setContent(updateSolutionDto.getContent());
        solutionService.updateSolution(solution);
        return "redirect:/admin/class-management/"+updateSolutionDto.getClassId()+
                "?showModal=true&updateSolution=true&idIssue="+solution.getIssue().getId()+
                "&idSolution="+solution.getId();
    }

    @GetMapping("/deleteSolution")
    public String deleteSolution(@RequestParam Integer idDelete, @RequestParam Integer classId, @RequestParam Integer issueId){
        solutionService.deleteSolution(solutionService.getSolutionById(idDelete));
        return "redirect:/admin/class-management/"+classId+
                "?showModal=true&deleteSolution=true&idIssue="+issueId;
    }

    @PostMapping("/createSolution")
    public String createSolution(@ModelAttribute CreateSolutionDto createSolutionDto){
        Solution solution = new Solution();
        solution.setContent(createSolutionDto.getContent());
        solution.setTitle(createSolutionDto.getTitle());
        solution.setIssue(issueService.findById(createSolutionDto.getIssueId()));
        solutionService.createSolution(solution);
        return "redirect:/admin/class-management/"+createSolutionDto.getClassId()+
                "?showModal=true&createSolution=true&idIssue="+solution.getIssue().getId();
    }
}
