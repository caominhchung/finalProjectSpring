package com.training.service.impl;

import com.training.entities.Issue;
import com.training.repository.IssueRepository;
import com.training.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class IssueServiceIplm implements IssueService {

    @Autowired
    private IssueRepository issueRepository;

    @Transactional
    @Modifying
    @Override
    public boolean createIssue(Issue issue){
         Issue issue1 = issueRepository.save(issue);
         if (null==issue1){
             return false;
         }else{
             return true;
         }
    }

    @Override
    public Issue findById(Integer id){
        return issueRepository.findById(id).get();
    }

    @Override
    public void updateIssue(Issue issue){
        issueRepository.save(issue);
    }

    @Override
    public void deleteIssue(Issue issue){
        issueRepository.delete(issue);
    }
}
