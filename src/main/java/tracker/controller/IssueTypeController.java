package tracker.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import tracker.entity.IssueType;
import tracker.exception.IssueTypeNotFound;
import tracker.service.IssueTypeService;
import tracker.validation.IssueTypeValidator;

@Controller
@RequestMapping("/issue-type")
public class IssueTypeController {
    private static final String REDIRECT_PROJECT = "redirect:/issue-type";
    private static final String PAGE_LIST = "issue-type/list";
    private static final String PAGE_EDIT = "issue-type/edit";

    @Autowired
    private IssueTypeService service;
    @Autowired
    private IssueTypeValidator validator;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    @GetMapping("")
    public ModelAndView issueTypeListPage() {
        ModelAndView mav = new ModelAndView(PAGE_LIST, "issueType", new IssueType());
        List<IssueType> issueTypeList = service.findAll();
        mav.addObject("issueTypeList", issueTypeList);
        return mav;
    }

    @PostMapping("/create")
    public ModelAndView createNewIssueType(@ModelAttribute @Valid IssueType issueType, BindingResult result,
            final RedirectAttributes redirectAttributes) {

        ModelAndView mav = new ModelAndView(REDIRECT_PROJECT);
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", result.getAllErrors());
            return mav;
        }

        String message = null;
        String error = null;
        try {
            service.create(issueType);
            message = "New IssueType \"" + issueType.getName() + "\" has been created.";
        } catch (Exception e) {
            error = "An error occured, " + e.getMessage();
        }

        redirectAttributes.addFlashAttribute("error", error);
        redirectAttributes.addFlashAttribute("message", message);
        redirectAttributes.addFlashAttribute("name", issueType.getName());

        return mav;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteIssueType(@PathVariable Integer id, final RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView(REDIRECT_PROJECT);
        String error = null;
        String message = null;
        try {
            IssueType issueType = service.delete(id);
            message = "IssueType \"" + issueType.getId() + ": " + issueType.getName() + "\" has been deleted.";
        } catch (IssueTypeNotFound e) {
            error = "An error occured, " + e.getMessage();
        }

        redirectAttributes.addFlashAttribute("error", error);
        redirectAttributes.addFlashAttribute("message", message);
        return mav;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editIssueTypePage(@PathVariable Integer id) {
        try {
            IssueType issueType = service.findById(id);
            ModelAndView mav = new ModelAndView(PAGE_EDIT);
            mav.addObject("issueType", issueType);
            return mav;
        } catch (IssueTypeNotFound e) {
            ModelAndView mav = new ModelAndView(PAGE_LIST);
            mav.addObject("error", e.getMessage());
            return mav;
        }
    }

    @PostMapping("/save/{id}")
    public ModelAndView saveIssueType(@ModelAttribute @Valid IssueType issueType, BindingResult result,
            @PathVariable Integer id, final RedirectAttributes redirectAttributes) {

        ModelAndView mav = new ModelAndView(REDIRECT_PROJECT);
        if (result.hasErrors()) {
            mav.addObject("errors", result.getAllErrors());
            return mav;
        }

        String message = null;
        String error = null;
        try {
            service.update(issueType);
            message = "IssueType \"" + issueType.getId() + ": " + issueType.getName() + "\" has been updated.";
        } catch (Exception e) {
            error = "An error occured, " + e.getMessage();
        }

        redirectAttributes.addFlashAttribute("error", error);
        redirectAttributes.addFlashAttribute("message", message);
        redirectAttributes.addFlashAttribute("name", issueType.getName());

        return mav;
    }
}
