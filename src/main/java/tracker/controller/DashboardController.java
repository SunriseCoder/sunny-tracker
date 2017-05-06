package tracker.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import tracker.comparator.IssueComparator;
import tracker.dto.IssueTypeIssuesDTO;
import tracker.dto.ProjectIssueTypesDTO;
import tracker.entity.Issue;
import tracker.entity.IssueType;
import tracker.entity.Project;
import tracker.service.IssueService;
import tracker.service.IssueTypeService;
import tracker.service.ProjectService;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {
    @Autowired
    private IssueService issueService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private IssueTypeService issueTypeService;

    @GetMapping("")
    public String index(Model model) {
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
                issueTypeStructure.setIssues(rootIssues);
                issueTypeStructures.add(issueTypeStructure);
            }

            projectStructure.setIssueTypeStructures(issueTypeStructures);
            structures.add(projectStructure);
        }

        model.addAttribute("structures", structures);
        model.addAttribute("issueTypes", issueTypes);

        return "dashboard";
    }

    private void sortChildIssuesRecursively(List<Issue> issues) {
        if (issues.isEmpty()) {
            return;
        }

        issues.sort(new IssueComparator());

        for (Issue issue : issues) {
            List<Issue> childs = issue.getChilds();
            sortChildIssuesRecursively(childs);
        }
    }
}
