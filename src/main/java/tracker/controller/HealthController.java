package tracker.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import tracker.entity.Drive;
import tracker.service.DriveService;

@Controller
@RequestMapping("/health")
public class HealthController {
	private static final Logger LOGGER = LogManager.getLogger(HealthController.class.getName());
	private static final String PAGE_LIST = "health/list";

	@Autowired
	private DriveService driveService;

	@GetMapping("")
	public String monitoringListPage(Model model) {
		try {
			List<Drive> drives = driveService.findAll();
			model.addAttribute("drives", drives);
			return PAGE_LIST;
		} catch (Exception e) {
			LOGGER.error("Error due to retrieve drives from DriveService", e);
			throw e;
		}
	}
}
