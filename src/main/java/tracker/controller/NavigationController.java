package tracker.controller;

import java.util.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import tracker.dto.IssueTypeIssuesDTO;
import tracker.dto.ProjectIssueTypesDTO;
import tracker.entity.Issue;
import tracker.entity.IssueType;
import tracker.entity.Project;
import tracker.service.IssueService;
import tracker.service.IssueTypeService;
import tracker.service.ProjectService;

@Controller
@RequestMapping(value = "/")
public class NavigationController {
    @Autowired
    private IssueService issueService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private IssueTypeService issueTypeService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("main");

        List<ProjectIssueTypesDTO> structures = new ArrayList<ProjectIssueTypesDTO>();
        List<IssueType> issueTypes = issueTypeService.findAll();

        for (Project project : projectService.findAll()) {
            ProjectIssueTypesDTO projectStructure = new ProjectIssueTypesDTO();
            projectStructure.setProject(project);
            List<IssueTypeIssuesDTO> issueTypeStructures = new ArrayList<IssueTypeIssuesDTO>();

            for (IssueType issueType : issueTypes) {
                IssueTypeIssuesDTO issueTypeStructure = new IssueTypeIssuesDTO();
                issueTypeStructure.setIssueType(issueType);
                List<Issue> rootIssues = issueService.findRootIssues(project.getId(), issueType.getId());
                sortChildIssuesRecursively(rootIssues);
                Collections.sort(rootIssues, (a, b) -> a.getName().compareTo(b.getName()));
                issueTypeStructure.setIssues(rootIssues);
                issueTypeStructures.add(issueTypeStructure);
            }

            projectStructure.setIssueTypeStructures(issueTypeStructures);
            structures.add(projectStructure);
        }

        mav.addObject("structures", structures);
        mav.addObject("issueTypes", issueTypes);

        return mav;
    }

    private void sortChildIssuesRecursively(List<Issue> issues) {
        if (issues.isEmpty()) {
            return;
        }

        issues.sort((a, b) -> {
            int as = a.getStatus().getIssuePosition();
            int bs = b.getStatus().getIssuePosition();
            if (as != bs) {
                return as - bs;
            }

            int ap = a.getPriority().getIssuePosition();
            int bp = b.getPriority().getIssuePosition();
            if (ap != bp) {
                return ap - bp;
            }

            Date ac = a.getChanged();
            Date bc = b.getChanged();
            long acl = ac == null ? Long.MIN_VALUE : ac.getTime();
            long bcl = bc == null ? Long.MIN_VALUE : bc.getTime();
            return acl < bcl ? 1 : acl == bcl ? 0 : -1;
        });

        for (Issue issue : issues) {
            List<Issue> childs = issue.getChilds();
            sortChildIssuesRecursively(childs);
        }
    }
}
