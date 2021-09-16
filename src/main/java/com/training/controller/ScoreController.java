package com.training.controller;

import com.training.dto.CreateCommentDto;
import com.training.dto.CreateIssueDto;
import com.training.dto.CreateScoreDto;
import com.training.dto.UpdateCommentDto;
import com.training.entities.*;
import com.training.entities.enumeration.ScoreType;
import com.training.repository.CertificateRepository;
import com.training.service.CertificateService;
import com.training.service.CourseTraineeService;
import com.training.service.ScoreService;
import com.training.service.TraineeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * author HuyLQ21
 */
@RequestMapping(value = "/trainer/score")
@Controller
@PreAuthorize("hasRole('TRAINER')")
public class ScoreController {

    @Autowired
    private TraineeService traineeService;

    @Autowired
    private CourseTraineeService courseTraineeService;

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private CertificateService certificateService;

    /**
     * author HuyLQ21
     *
     * @tode: use to get score detail screen
     */
    @GetMapping
    public String getScoreDetail(@RequestParam Integer traineeId, ModelMap modelMap) {
        Trainee trainee = traineeService.findById(traineeId);
        Double totalScore = 0.0;
        modelMap.put("trainee", trainee);
        modelMap.put("createCommentDto", new CreateCommentDto());
        modelMap.put("updateCommentDto", new UpdateCommentDto());
        Collections.sort(trainee.getCourseTrainees(), new Comparator<CourseTrainee>() {
            @Override
            public int compare(CourseTrainee o1, CourseTrainee o2) {
                if (o1.getId() > o2.getId()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
        modelMap.put("listTraineeCourse", trainee.getCourseTrainees());
        modelMap.put("createScoreDto", new CreateScoreDto());

        Integer courseHaveScore = 0;
        List<CourseTrainee> courseTrainees = trainee.getCourseTrainees();
        for (CourseTrainee courseTrainee : courseTrainees) {
            List<Score> scores = courseTrainee.getScores();
            if (scores.size() != 0) {
                courseHaveScore += 1;
                for (Score score : scores) {
                    if (score.getType().equals(ScoreType.Summary)) {
                        totalScore += score.getValue();
                    }
                }
            }
        }

        Double gpa = totalScore / courseHaveScore;
        if(trainee.getClassOfTrainee().getClassCourses().size()!=0
            && trainee.getClassOfTrainee().getClassCourses().size()== courseHaveScore ){
            Certificate certificate = new Certificate();
            certificate.setTrainee(trainee);
            certificate.setCompletionLevel(scoreService.getRank(gpa).getValue());
            certificate.setRate(scoreService.getRank(gpa));
            certificateService.createCertificate(certificate);

            modelMap.put("isGraduated", "graduated");
            modelMap.put("titleRank", "Certificate: ");
            modelMap.put("titleGPA", "Final GPA: ");
        }else {
            modelMap.put("Graduated", "");
            modelMap.put("titleRank", "Rank: ");
            modelMap.put("titleGPA", "GPA: ");
            modelMap.put("updateScoreDto", new UpdateScoreDto());
        }
        modelMap.put("rankNow", scoreService.getRank(gpa) == null ? "None" : scoreService.getRank(gpa));
        modelMap.put("gpaNow", (double) Math.round(gpa * 100) / 100);
        return "score-management";
    }

    /**
     * author HuyLQ21
     *
     * @tode: use to create new score
     */
    @PostMapping("/createScore")
    public String createScore(@ModelAttribute CreateScoreDto createScoreDto) {
        CourseTrainee courseTrainee = courseTraineeService.getCourseTraineeById(createScoreDto.getTraineeCourseId());

        Double valueScoreQuiz = createScoreDto.getQuizScore();
        Double valueScoreAssignment = createScoreDto.getAssignmentScore();
        Double valueScoreFinalExam = createScoreDto.getFinalExamScore();


        Score scoreQuiz = new Score();
        Score scoreAssignment = new Score();
        Score scoreFinalExam = new Score();
        Score scoreSummary = new Score();

        scoreQuiz.setCourseTrainee(courseTrainee);
        scoreQuiz.setType(ScoreType.Quiz);
        scoreQuiz.setValue(createScoreDto.getQuizScore());

        scoreAssignment.setCourseTrainee(courseTrainee);
        scoreAssignment.setType(ScoreType.Assignment);
        scoreAssignment.setValue(createScoreDto.getAssignmentScore());

        scoreFinalExam.setCourseTrainee(courseTrainee);
        scoreFinalExam.setType(ScoreType.FinalExam);
        scoreFinalExam.setValue(createScoreDto.getFinalExamScore());

        scoreSummary.setCourseTrainee(courseTrainee);
        scoreSummary.setType(ScoreType.Summary);
        scoreSummary.setValue((double) Math.round((valueScoreQuiz * 0.15 + valueScoreAssignment * 0.15 + valueScoreFinalExam * 0.7) * 100) / 100);

        scoreService.createScore(scoreQuiz);
        scoreService.createScore(scoreAssignment);
        scoreService.createScore(scoreFinalExam);
        scoreService.createScore(scoreSummary);

        return "redirect:/trainer/score?traineeId=" + courseTrainee.getTrainee().getId() + "&createScore=true&courseScore=" + courseTrainee.getCourse().getName();


    }

    /**
     * author HuyLQ21
     *
     * @tode: use to update score
     */
    @PostMapping("/updateScore")
    public String updateScoreController(@ModelAttribute UpdateScoreDto updateScoreDto) {
        Score scoreQuiz = scoreService.findScoreById(updateScoreDto.getQuizScoreId());
        Score scoreAssignment = scoreService.findScoreById(updateScoreDto.getAssignmentScoreId());
        Score scoreFinalExam = scoreService.findScoreById(updateScoreDto.getFinalExamScoreId());
        Score scoreSummary = scoreService.findScoreById(updateScoreDto.getSummaryScoreId());
        scoreQuiz.setValue(updateScoreDto.getQuizScore());
        scoreAssignment.setValue(updateScoreDto.getAssignmentScore());
        scoreFinalExam.setValue(updateScoreDto.getFinalExamScore());
        Double summaryScore = updateScoreDto.getQuizScore() * 0.15 + updateScoreDto.getAssignmentScore() * 0.15 + updateScoreDto.getFinalExamScore() * 0.7;
        scoreSummary.setValue((double) Math.round(summaryScore * 100) / 100);
        scoreService.createScore(scoreAssignment);
        scoreService.createScore(scoreQuiz);
        scoreService.createScore(scoreFinalExam);
        scoreService.createScore(scoreSummary);
        return "redirect:/trainer/score?traineeId=" + scoreQuiz.getCourseTrainee().getTrainee().getId() + "&updateScore=true&courseScore=" + scoreQuiz.getCourseTrainee().getCourse().getName();

    }


}
