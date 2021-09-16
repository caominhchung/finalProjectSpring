package com.training.service;

import com.training.entities.Issue;

public interface IssueService {

    public boolean createIssue(Issue issue);

    public Issue findById(Integer id);

    public void updateIssue(Issue issue);

    public void deleteIssue(Issue issue);
}
