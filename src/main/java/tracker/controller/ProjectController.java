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

import tracker.entity.Project;
import tracker.exception.ProjectNotFound;
import tracker.service.ProjectService;
import tracker.validation.ProjectValidator;

@Controller
@RequestMapping(value = "/project")
public class ProjectController {
    private static final String REDIRECT_PROJECT = "redirect:/project";
    private static final String PAGE_LIST = "project/list";
    private static final String PAGE_EDIT = "project/edit";

    @Autowired
    private ProjectService service;
    @Autowired
    private ProjectValidator validator;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    @GetMapping("")
    public ModelAndView projectListPage() {
        ModelAndView mav = new ModelAndView(PAGE_LIST, "project", new Project());
        List<Project> projectList = service.findAll();
        mav.addObject("projectList", projectList);
        return mav;
    }

    @PostMapping("/create")
    public ModelAndView createNewProject(@ModelAttribute @Valid Project project, BindingResult result,
            final RedirectAttributes redirectAttributes) {

        ModelAndView mav = new ModelAndView(REDIRECT_PROJECT);
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", result.getAllErrors());
            return mav;
        }

        String message = null;
        String error = null;
        try {
            service.create(project);
            message = "New Project \"" + project.getName() + "\" has been created.";
        } catch (Exception e) {
            error = "An error occured, " + e.getMessage();
        }

        redirectAttributes.addFlashAttribute("error", error);
        redirectAttributes.addFlashAttribute("message", message);
        redirectAttributes.addFlashAttribute("name", project.getName());

        return mav;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteProject(@PathVariable Integer id, final RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView(REDIRECT_PROJECT);
        String error = null;
        String message = null;
        try {
            Project project = service.delete(id);
            message = "Project \"" + project.getId() + ": " + project.getName() + "\" has been deleted.";
        } catch (ProjectNotFound e) {
            error = "An error occured, " + e.getMessage();
        }

        redirectAttributes.addFlashAttribute("error", error);
        redirectAttributes.addFlashAttribute("message", message);
        return mav;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editProjectPage(@PathVariable Integer id) {
        try {
            Project project = service.findById(id);
            ModelAndView mav = new ModelAndView(PAGE_EDIT);
            mav.addObject("project", project);
            return mav;
        } catch (ProjectNotFound e) {
            ModelAndView mav = new ModelAndView(PAGE_LIST);
            mav.addObject("error", e.getMessage());
            return mav;
        }
    }

    @PostMapping("/save/{id}")
    public ModelAndView saveProject(@ModelAttribute @Valid Project project, BindingResult result,
            @PathVariable Integer id, final RedirectAttributes redirectAttributes) {

        ModelAndView mav = new ModelAndView(REDIRECT_PROJECT);
        if (result.hasErrors()) {
            mav.addObject("errors", result.getAllErrors());
            return mav;
        }

        String message = null;
        String error = null;
        try {
            service.update(project);
            message = "Project \"" + project.getId() + ": " + project.getName() + "\" has been updated.";
        } catch (Exception e) {
            error = "An error occured, " + e.getMessage();
        }

        redirectAttributes.addFlashAttribute("error", error);
        redirectAttributes.addFlashAttribute("message", message);
        redirectAttributes.addFlashAttribute("name", project.getName());

        return mav;
    }
}
