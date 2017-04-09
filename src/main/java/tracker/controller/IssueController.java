package tracker.controller;

import java.util.Collections;
import java.util.Iterator;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    private static final String REDIRECT_PAGE_HOME = "redirect:/";
    private static final String REDIRECT_PAGE_ISSUE = "redirect:/issue";
    private static final String PAGE_LIST = "issue/list";
    private static final String PAGE_CREATE = "issue/create";
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
        model.addAttribute("issue", new Issue());

        List<Issue> issueList = issueService.findAll();
        model.addAttribute("issueList", issueList);

        injectAllData(model);

        return PAGE_LIST;
    }

    @GetMapping("/create/{projectId}/{typeId}")
    public String createIssuePageByProjectAndType(@PathVariable Integer projectId, @PathVariable Integer typeId,
            Model model) {

        Issue issue = new Issue();
        try {
            issue.setProject(projectService.findById(projectId));
        } catch (ProjectNotFound e) {
        }
        try {
            issue.setType(issueTypeService.findById(typeId));
        } catch (IssueTypeNotFound e) {
        }
        model.addAttribute("issue", issue);

        injectAllData(model);
        injectIssueTree(model, issue);

        return PAGE_CREATE;
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
        model.addAttribute("issue", issue);

        injectAllData(model);
        injectIssueTree(model, issue);

        return PAGE_CREATE;
    }

    @PostMapping("/create")
    public String createNewIssue(@ModelAttribute @Valid Issue issue, BindingResult result,
            final RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", result.getAllErrors());
            return REDIRECT_PAGE_ISSUE;
        }

        String message = null;
        String error = null;
        try {
            // TODO Clarify, maybe there is another way to do it?
            if (rootIssue.getId().equals(issue.getParent().getId())) {
                issue.setParent(null);
            }
            issueService.create(issue);
            message = "New Issue \"" + issue.getName() + "\" has been created.";
        } catch (Exception e) {
            error = "An error occured, " + e.getMessage();
        }

        redirectAttributes.addFlashAttribute("error", error);
        redirectAttributes.addFlashAttribute("message", message);
        redirectAttributes.addFlashAttribute("name", issue.getName());

        return REDIRECT_PAGE_ISSUE;
    }

    @GetMapping("/delete/{id}")
    public String deleteIssue(@PathVariable Integer id, final RedirectAttributes redirectAttributes) {
        String error = null;
        String message = null;
        try {
            Issue issue = issueService.delete(id);
            message = "Issue \"" + issue.getId() + ": " + issue.getName() + "\" has been deleted.";
        } catch (IssueNotFound e) {
            error = "An error occured, " + e.getMessage();
        }

        redirectAttributes.addFlashAttribute("error", error);
        redirectAttributes.addFlashAttribute("message", message);
        return REDIRECT_PAGE_ISSUE;
    }

    @GetMapping("/edit/{id}")
    public String editIssuePage(@PathVariable Integer id, Model model) {
        try {
            Issue issue = issueService.findById(id);
            model.addAttribute("issue", issue);

            injectAllData(model);
            injectIssueTree(model, issue);

            return PAGE_EDIT;
        } catch (IssueNotFound e) {
            model.addAttribute("error", e.getMessage());
            return REDIRECT_PAGE_ISSUE;
        }
    }

    @PostMapping("/save/{id}")
    public String saveIssue(@ModelAttribute @Valid Issue issue, BindingResult result, @PathVariable Integer id,
            Model model, final RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("errors", result.getAllErrors());
            return REDIRECT_PAGE_ISSUE;
        }

        String message = null;
        String error = null;
        try {
            // TODO Clarify, maybe there is another way to set parent to null?
            if (rootIssue.getId().equals(issue.getParent().getId())) {
                issue.setParent(null);
            }
            if (issue.getId().equals(issue.getParent().getId())) {
                Issue storedIssue = issueService.findById(id);
                issue.setParent(storedIssue.getParent());
            }
            issueService.update(issue);
            message = "Issue \"" + issue.getId() + ": " + issue.getName() + "\" has been updated.";
        } catch (Exception e) {
            error = "An error occured, " + e.getMessage();
        }

        redirectAttributes.addFlashAttribute("error", error);
        redirectAttributes.addFlashAttribute("message", message);
        redirectAttributes.addFlashAttribute("name", issue.getName());

        return REDIRECT_PAGE_HOME;
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
        removeIssueFromList(issues, issue);
        issues.forEach(i -> processIssueBranchRecursively(i.getChilds(), issue));
        return issues;
    }

    private void removeIssueFromList(List<Issue> issues, Issue issue) {
        Iterator<Issue> iterator = issues.iterator();
        while (iterator.hasNext()) {
            Issue current = iterator.next();
            if (current.getId().equals(issue.getId())) {
                iterator.remove();
            }
        }
    }

    private void injectAllData(Model model) {
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
