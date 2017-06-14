package tracker.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import tracker.entity.Issue;
import tracker.entity.IssueStatistic;
import tracker.helper.StatisticHistogramGenerator;
import tracker.service.IssueService;
import tracker.service.IssueStatisticService;

@Controller
@RequestMapping("/monitoring")
public class MonitoringController {
    private static final Logger logger = LogManager.getLogger(MonitoringController.class.getName());
    private static final String PAGE_LIST = "monitoring/list";

    @Autowired
    private IssueService issueService;
    @Autowired
    private IssueStatisticService issueStatisticService;

    @GetMapping("")
    public String monitoringListPage(Model model) {
        List<Issue> issueList = issueService.findMonitored();
        model.addAttribute("issueList", issueList);
        return PAGE_LIST;
    }

    @GetMapping(value = "/histogram/{issueId}", produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public byte[] createHistogram(@PathVariable Integer issueId, HttpServletResponse response,
                    @RequestParam(name = "h", required = false) Integer h, @RequestParam(name = "w", required = false) Integer w) {

        List<IssueStatistic> statistics = issueStatisticService.findByIssueId(issueId);
        BufferedImage image = generateHistogram(h, w, statistics);
        return getData(image);
    }

    private BufferedImage generateHistogram(Integer h, Integer w, List<IssueStatistic> statistics) {
        StatisticHistogramGenerator generator = new StatisticHistogramGenerator();
        generator.setBinPartHeight(h);
        generator.setBinWidth(w);
        generator.setData(statistics);
        return generator.generate();
    }

    private byte[] getData(BufferedImage image) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();) {
            ImageIO.write(image, "PNG", baos);
            baos.flush();
            return baos.toByteArray();
        } catch (IOException e) {
            logger.error("IOException during generating image", e);
        }
        return null;
    }
}
