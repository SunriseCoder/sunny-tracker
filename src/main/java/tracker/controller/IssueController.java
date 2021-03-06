package tracker.controller;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import tracker.entity.Issue;
import tracker.entity.IssuePriority;
import tracker.entity.IssueStatus;
import tracker.entity.IssueType;
import tracker.entity.Project;
import tracker.exception.IssueNotFound;
import tracker.exception.IssueTypeNotFound;
import tracker.exception.ProjectNotFound;
import tracker.service.IssuePriorityService;
import tracker.service.IssueService;
import tracker.service.IssueStatusService;
import tracker.service.IssueTypeService;
import tracker.service.ProjectService;
import tracker.validation.IssueValidator;

@Controller
@RequestMapping("/issue")
public class IssueController {
    private static final String PAGE_LIST = "issue/list";
    private static final String PAGE_EDIT = "issue/edit";

    @Autowired
    private IssueService issueService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private IssueTypeService issueTypeService;
    @Autowired
    private IssueStatusService issueStatusService;
    @Autowired
    private IssuePriorityService issuePriorityService;
    @Autowired
    private IssueValidator validator;

    private Issue rootIssue;

    public IssueController() {
        rootIssue = new Issue();
        rootIssue.setId(0);
        rootIssue.setName("<root>");
    }

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    @GetMapping("")
    public String issueListPage(Model model) {
        try {
            injectAllData(model, new Issue());
        } catch (Exception e) {
        }

        List<Issue> issueList = issueService.findAll();
        model.addAttribute("issueList", issueList);

        return PAGE_LIST;
    }

    @GetMapping("/create/{projectId}/{typeId}")
    public String createRootIssuePage(@PathVariable Integer projectId, @PathVariable Integer typeId, Model model) {
        Issue issue = new Issue();

        try {
            issue.setProject(projectService.findById(projectId));
        } catch (ProjectNotFound e) {
        }

        try {
            issue.setType(issueTypeService.findById(typeId));
            injectAllData(model, issue);
            injectIssueTree(model, issue);
        } catch (Exception e) {
        }

        return PAGE_EDIT;
    }

    @GetMapping("/create/{projectId}/{typeId}/{parentId}")
    public String createSubIssuePageByProjectAndType(@PathVariable Integer projectId, @PathVariable Integer typeId,
            @PathVariable Integer parentId, Model model) {

        Issue issue = new Issue();

        try {
            issue.setProject(projectService.findById(projectId));
        } catch (ProjectNotFound e) {
        }

        try {
            issue.setType(issueTypeService.findById(typeId));
        } catch (IssueTypeNotFound e) {
        }

        try {
            issue.setParent(issueService.findById(parentId));
        } catch (IssueNotFound e) {
        }

        try {
            injectAllData(model, issue);
        } catch (Exception e) {
        }

        injectIssueTree(model, issue);

        return PAGE_EDIT;
    }

    @PostMapping("/save")
    public String saveIssue(@ModelAttribute @Valid Issue issue, BindingResult result, Model model) {
        String message = null;
        String error = null;
        try {
            if (!result.hasErrors()) {
                issue = issueService.update(issue);
                message = MessageFormat.format("Issue [{0}: {1}] has been saved.", issue.getId(), issue.getName());
            }
        } catch (IssueNotFound e) {
            error = e.getMessage();
        } catch (Exception e) {
            error = "An error occured: " + e.getMessage();
        }

        try {
            injectAllData(model, issue);
        } catch (Exception e) {
            error += e.getMessage();
        }

        injectIssueTree(model, issue);

        if (result.hasErrors()) {
            model.addAttribute("errors", result.getAllErrors());
        }
        model.addAttribute("error", error);
        model.addAttribute("message", message);

        return PAGE_EDIT;
    }

    @GetMapping("/delete/{id}")
    public String deleteIssue(@PathVariable Integer id, Model model) {
        String error = null;
        String message = null;
        try {
            Issue issue = issueService.delete(id);
            message = "Issue \"" + issue.getId() + ": " + issue.getName() + "\" has been deleted.";
        } catch (IssueNotFound e) {
            error = "An error occured, " + e.getMessage();
        }

        model.addAttribute("error", error);
        model.addAttribute("message", message);
        return PAGE_LIST;
    }

    @GetMapping("/edit/{id}")
    public String editIssuePage(@PathVariable Integer id, Model model) {
        try {
            Issue issue = issueService.findById(id);

            injectAllData(model, issue);
            injectIssueTree(model, issue);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }

        return PAGE_EDIT;
    }

    private void injectIssueTree(Model model, Issue issue) {
        Integer projectId = issue.getProject().getId();
        Integer typeId = issue.getType().getId();

        List<Issue> issues = issueService.findRootIssues(projectId, typeId);
        issues = processIssueBranchRecursively(issues, issue);
        rootIssue.setChilds(issues);
        model.addAttribute("rootIssues", Collections.singletonList(rootIssue));
    }

    private List<Issue> processIssueBranchRecursively(List<Issue> issues, Issue issue) {
        Collections.sort(issues, (a, b) -> a.getName().compareToIgnoreCase(b.getName()));
        issues.forEach(i -> {
            if (i.getChilds() != null) {
                processIssueBranchRecursively(i.getChilds(), issue);
            }
        });
        return issues;
    }

    private void injectAllData(Model model, Issue issue) throws Exception {
        if (issue.getStatus() == null) {
            IssueStatus status = issueStatusService.findSelected();
            issue.setStatus(status);
        }

        if (issue.getPriority() == null) {
            IssuePriority priority = issuePriorityService.findSelected();
            issue.setPriority(priority);
        }

        model.addAttribute("issue", issue);

        List<Project> projects = projectService.findAll();
        model.addAttribute("projects", projects);

        List<IssueType> issueTypes = issueTypeService.findAll();
        model.addAttribute("issueTypes", issueTypes);

        List<IssueStatus> issueStatuses = issueStatusService.findAll();
        model.addAttribute("issueStatuses", issueStatuses);

        List<IssuePriority> issuePriorities = issuePriorityService.findAll();
        model.addAttribute("issuePriorities", issuePriorities);
    }
}
