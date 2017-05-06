package tracker.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tracker.exception.IssueNotFound;
import tracker.service.IssueService;

@RestController
@RequestMapping("/rest/issue")
public class IssueRestController {
    @Autowired
    private IssueService issueService;

    @PostMapping("move")
    public void moveIssue(@RequestParam("issueId") Integer issueId, @RequestParam("direction") String direction)
            throws IssueNotFound {
        issueService.move(issueId, direction);
    }

    @GetMapping("sort")
    public void sortIssues() {
        issueService.sortAllIssues();
    }
}
