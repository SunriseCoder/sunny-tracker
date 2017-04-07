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

import tracker.entity.IssuePriority;
import tracker.exception.IssuePriorityNotFound;
import tracker.service.IssuePriorityService;
import tracker.validation.IssuePriorityValidator;

@Controller
@RequestMapping(value = "/issue-priority")
public class IssuePriorityController {
	private static final String REDIRECT_PROJECT = "redirect:/issue-priority";
	private static final String PAGE_LIST = "issue-priority/list";
	private static final String PAGE_EDIT = "issue-priority/edit";

	@Autowired
	private IssuePriorityService service;
	@Autowired
	private IssuePriorityValidator validator;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView issuePriorityListPage() {
		ModelAndView mav = new ModelAndView(PAGE_LIST, "issuePriority", new IssuePriority());
		List<IssuePriority> issuePriorityList = service.findAll();
		mav.addObject("issuePriorityList", issuePriorityList);
		return mav;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView createNewIssuePriority(@ModelAttribute @Valid IssuePriority issuePriority,
			BindingResult result, final RedirectAttributes redirectAttributes) {

		ModelAndView mav = new ModelAndView(REDIRECT_PROJECT);
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("errors", result.getAllErrors());
			return mav;
		}

		String message = null;
		String error = null;
		try {
			service.create(issuePriority);
			message = "New IssuePriority \"" + issuePriority.getName() + "\" has been created.";
		} catch (Exception e) {
			error = "An error occured, " + e.getMessage();
		}

		redirectAttributes.addFlashAttribute("error", error);
		redirectAttributes.addFlashAttribute("message", message);
		redirectAttributes.addFlashAttribute("name", issuePriority.getName());

		return mav;
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public ModelAndView deleteIssuePriority(@PathVariable Integer id, final RedirectAttributes redirectAttributes) {
		ModelAndView mav = new ModelAndView(REDIRECT_PROJECT);
		String error = null;
		String message = null;
		try {
			IssuePriority issuePriority = service.delete(id);
			message = "IssuePriority \"" + issuePriority.getId() + ": " + issuePriority.getName() + "\" has been deleted.";
		} catch (IssuePriorityNotFound e) {
			error = "An error occured, " + e.getMessage();
		}

		redirectAttributes.addFlashAttribute("error", error);
		redirectAttributes.addFlashAttribute("message", message);
		return mav;
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView editIssuePriorityPage(@PathVariable Integer id) {
		try {
			IssuePriority issuePriority = service.findById(id);
			ModelAndView mav = new ModelAndView(PAGE_EDIT);
			mav.addObject("issuePriority", issuePriority);
			return mav;
		} catch (IssuePriorityNotFound e) {
			ModelAndView mav = new ModelAndView(PAGE_LIST);
			mav.addObject("error", e.getMessage());
			return mav;
		}
	}

	@RequestMapping(value = "/save/{id}", method = RequestMethod.POST)
	public ModelAndView saveIssuePriority(@ModelAttribute @Valid IssuePriority issuePriority,
			BindingResult result, @PathVariable Integer id, final RedirectAttributes redirectAttributes) {

		ModelAndView mav = new ModelAndView(REDIRECT_PROJECT);
		if (result.hasErrors()) {
			mav.addObject("errors", result.getAllErrors());
			return mav;
		}

		String message = null;
		String error = null;
		try {
			service.update(issuePriority);
			message = "IssuePriority \"" + issuePriority.getId() + ": " + issuePriority.getName() + "\" has been updated.";
		} catch (Exception e) {
			error = "An error occured, " + e.getMessage();
		}

		redirectAttributes.addFlashAttribute("error", error);
		redirectAttributes.addFlashAttribute("message", message);
		redirectAttributes.addFlashAttribute("name", issuePriority.getName());

		return mav;
	}
}
