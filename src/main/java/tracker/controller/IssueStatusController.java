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

import tracker.exception.IssueStatusNotFound;
import tracker.model.IssueStatus;
import tracker.service.IssueStatusService;
import tracker.validation.IssueStatusValidator;

@Controller
@RequestMapping(value = "/issue-status")
public class IssueStatusController {
	private static final String REDIRECT_PROJECT = "redirect:/issue-status";
	private static final String PAGE_LIST = "issue-status/list";
	private static final String PAGE_EDIT = "issue-status/edit";

	@Autowired
	private IssueStatusService service;
	@Autowired
	private IssueStatusValidator validator;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView issueStatusListPage() {
		ModelAndView mav = new ModelAndView(PAGE_LIST, "issueStatus", new IssueStatus());
		List<IssueStatus> issueStatusList = service.findAll();
		mav.addObject("issueStatusList", issueStatusList);
		return mav;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView createNewIssueStatus(@ModelAttribute @Valid IssueStatus issueStatus,
			BindingResult result, final RedirectAttributes redirectAttributes) {

		ModelAndView mav = new ModelAndView(REDIRECT_PROJECT);
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("errors", result.getAllErrors());
			return mav;
		}

		String message = null;
		String error = null;
		try {
			service.create(issueStatus);
			message = "New IssueStatus \"" + issueStatus.getName() + "\" has been created.";
		} catch (Exception e) {
			error = "An error occured, " + e.getMessage();
		}

		redirectAttributes.addFlashAttribute("error", error);
		redirectAttributes.addFlashAttribute("message", message);
		redirectAttributes.addFlashAttribute("name", issueStatus.getName());

		return mav;
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public ModelAndView deleteIssueStatus(@PathVariable Integer id, final RedirectAttributes redirectAttributes) {
		ModelAndView mav = new ModelAndView(REDIRECT_PROJECT);
		String error = null;
		String message = null;
		try {
			IssueStatus issueStatus = service.delete(id);
			message = "IssueStatus \"" + issueStatus.getId() + ": " + issueStatus.getName() + "\" has been deleted.";
		} catch (IssueStatusNotFound e) {
			error = "An error occured, " + e.getMessage();
		}

		redirectAttributes.addFlashAttribute("error", error);
		redirectAttributes.addFlashAttribute("message", message);
		return mav;
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView editIssueStatusPage(@PathVariable Integer id) {
		try {
			IssueStatus issueStatus = service.findById(id);
			ModelAndView mav = new ModelAndView(PAGE_EDIT);
			mav.addObject("issueStatus", issueStatus);
			return mav;
		} catch (IssueStatusNotFound e) {
			ModelAndView mav = new ModelAndView(PAGE_LIST);
			mav.addObject("error", e.getMessage());
			return mav;
		}
	}

	@RequestMapping(value = "/save/{id}", method = RequestMethod.POST)
	public ModelAndView saveIssueStatus(@ModelAttribute @Valid IssueStatus issueStatus,
			BindingResult result, @PathVariable Integer id, final RedirectAttributes redirectAttributes) {

		ModelAndView mav = new ModelAndView(REDIRECT_PROJECT);
		if (result.hasErrors()) {
			mav.addObject("errors", result.getAllErrors());
			return mav;
		}

		String message = null;
		String error = null;
		try {
			service.update(issueStatus);
			message = "IssueStatus \"" + issueStatus.getId() + ": " + issueStatus.getName() + "\" has been updated.";
		} catch (Exception e) {
			error = "An error occured, " + e.getMessage();
		}

		redirectAttributes.addFlashAttribute("error", error);
		redirectAttributes.addFlashAttribute("message", message);
		redirectAttributes.addFlashAttribute("name", issueStatus.getName());

		return mav;
	}
}
