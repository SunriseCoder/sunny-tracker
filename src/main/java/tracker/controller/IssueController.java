package tracker.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
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
@RequestMapping(value = "/issue")
public class IssueController {
	private static final String REDIRECT_PROJECT = "redirect:/issue";
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

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView issueListPage() {
		ModelAndView mav = new ModelAndView(PAGE_LIST, "issue", new Issue());

		List<Issue> issueList = issueService.findAll();
		mav.addObject("issueList", issueList);

		injectAllData(mav);

		return mav;
	}

	@RequestMapping(value = "/create/{projectId}/{typeId}", method = RequestMethod.GET)
	public ModelAndView createIssuePageByProjectAndType(@PathVariable Integer projectId,
			@PathVariable Integer typeId) {
		ModelAndView mav = new ModelAndView(PAGE_CREATE);

		Issue issue = new Issue();
		try {
			issue.setProject(projectService.findById(projectId));
		} catch (ProjectNotFound e) {
		}
		try {
			issue.setType(issueTypeService.findById(typeId));
		} catch (IssueTypeNotFound e) {
		}
		mav.addObject("issue", issue);

		injectAllData(mav);

		return mav;
	}

	@RequestMapping(value = "/create/{projectId}/{typeId}/{parentId}", method = RequestMethod.GET)
	public ModelAndView createSubIssuePageByProjectAndType(@PathVariable Integer projectId,
			@PathVariable Integer typeId, @PathVariable Integer parentId) {
		ModelAndView mav = new ModelAndView(PAGE_CREATE);

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
		mav.addObject("issue", issue);

		injectAllData(mav);

		return mav;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView createNewIssue(@ModelAttribute @Valid Issue issue,
			BindingResult result, final RedirectAttributes redirectAttributes) {

		ModelAndView mav = new ModelAndView(REDIRECT_PROJECT);
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("errors",
					result.getAllErrors());
			return mav;
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

		return mav;
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public ModelAndView deleteIssue(@PathVariable Integer id,
			final RedirectAttributes redirectAttributes) {
		ModelAndView mav = new ModelAndView(REDIRECT_PROJECT);
		String error = null;
		String message = null;
		try {
			Issue issue = issueService.delete(id);
			message = "Issue \"" + issue.getId() + ": " + issue.getName()
					+ "\" has been deleted.";
		} catch (IssueNotFound e) {
			error = "An error occured, " + e.getMessage();
		}

		redirectAttributes.addFlashAttribute("error", error);
		redirectAttributes.addFlashAttribute("message", message);
		return mav;
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView editIssuePage(@PathVariable Integer id) {
		try {
			ModelAndView mav = new ModelAndView(PAGE_EDIT);

			Issue issue = issueService.findById(id);
			mav.addObject("issue", issue);

			injectAllData(mav);

			return mav;
		} catch (IssueNotFound e) {
			ModelAndView mav = new ModelAndView(PAGE_LIST);
			mav.addObject("error", e.getMessage());
			return mav;
		}
	}

	@RequestMapping(value = "/save/{id}", method = RequestMethod.POST)
	public ModelAndView saveIssue(@ModelAttribute @Valid Issue issue,
			BindingResult result, @PathVariable Integer id,
			final RedirectAttributes redirectAttributes) {

		ModelAndView mav = new ModelAndView(REDIRECT_PROJECT);
		if (result.hasErrors()) {
			mav.addObject("errors", result.getAllErrors());
			return mav;
		}

		String message = null;
		String error = null;
		try {
			// TODO Clarify, maybe there is another way to do it?
			if (rootIssue.getId().equals(issue.getParent().getId())) {
				issue.setParent(null);
			}
			issueService.update(issue);
			message = "Issue \"" + issue.getId() + ": " + issue.getName()
					+ "\" has been updated.";
		} catch (Exception e) {
			error = "An error occured, " + e.getMessage();
		}

		redirectAttributes.addFlashAttribute("error", error);
		redirectAttributes.addFlashAttribute("message", message);
		redirectAttributes.addFlashAttribute("name", issue.getName());

		return mav;
	}

	private void injectAllData(ModelAndView mav) {
		List<Issue> issues = issueService.findAll();
		issues.add(0, rootIssue);
		mav.addObject("issues", issues);

		List<Project> projects = projectService.findAll();
		mav.addObject("projects", projects);

		List<IssueType> issueTypes = issueTypeService.findAll();
		mav.addObject("issueTypes", issueTypes);

		List<IssueStatus> issueStatuses = issueStatusService.findAll();
		mav.addObject("issueStatuses", issueStatuses);

		List<IssuePriority> issuePriorities = issuePriorityService.findAll();
		mav.addObject("issuePriorities", issuePriorities);
	}
}
