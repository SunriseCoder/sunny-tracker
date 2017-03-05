package tracker.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import tracker.model.IssueType;
import tracker.model.Project;
import tracker.service.IssueService;
import tracker.service.IssueTypeService;
import tracker.service.ProjectService;
import tracker.structures.IssueTypeIssuesStructure;
import tracker.structures.ProjectIssueTypesStructure;

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

		List<ProjectIssueTypesStructure> structures = new ArrayList<ProjectIssueTypesStructure>();
		List<IssueType> issueTypes = issueTypeService.findAll();

		for (Project project : projectService.findAll()) {
			ProjectIssueTypesStructure projectStructure = new ProjectIssueTypesStructure();
			projectStructure.setProject(project);
			List<IssueTypeIssuesStructure> issueTypeStructures = new ArrayList<IssueTypeIssuesStructure>();

			for (IssueType issueType : issueTypes) {
				IssueTypeIssuesStructure issueTypeStructure = new IssueTypeIssuesStructure();
				issueTypeStructure.setIssueType(issueType);
				issueTypeStructure.setIssues(issueService.findRootIssues(project.getId(), issueType.getId()));
				issueTypeStructures.add(issueTypeStructure);
			}
			
			projectStructure.setIssueTypeStructures(issueTypeStructures);
			structures.add(projectStructure);
		}

		mav.addObject("structures", structures);
		mav.addObject("issueTypes", issueTypes);

		return mav;
	}
}
