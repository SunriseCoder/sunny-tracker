package tracker.helper;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.stream.Collectors;

import tracker.entity.IssueStatistic;

public class StatisticAbsoluteHistogramHelper {
    private static final int BIN_PART_HEIGHT = 5;
    private static final int BIN_WIDTH = 5;

    public static BufferedImage generateAbsoluteHistogram(List<IssueStatistic> statistics) {
        int maxTotal = getMaxTotal(statistics);
        BufferedImage image = generateImage(statistics, maxTotal);
        drawBins(image, statistics, maxTotal);
        return image;
    }

    private static int getMaxTotal(List<IssueStatistic> statistics) {
        return statistics.stream()
                .map(s -> s.getTotal())
                .collect(Collectors.summarizingInt(Integer::intValue))
                .getMax();
    }

    private static BufferedImage generateImage(List<IssueStatistic> statistics, int maxTotal) {
        int length = statistics.size();
        int width = length * BIN_WIDTH + 1;
        int height = maxTotal * BIN_PART_HEIGHT;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);
        graphics.dispose();
        return image;
    }

    private static void drawBins(BufferedImage image, List<IssueStatistic> statistics, int maxTotal) {
        Graphics graphics = image.getGraphics();
        for (int i = 0; i < statistics.size(); i++) {
            IssueStatistic statistic = statistics.get(i);
            drawBin(graphics, i, statistic, maxTotal);
        }
    }

    private static void drawBin(Graphics graphics, int i, IssueStatistic statistic, int maxTotal) {
        int x = i * BIN_WIDTH;
        drawBin(graphics, x, 0, statistic.getTotal(), maxTotal, Color.RED, true);
        drawBin(graphics, x, 0, statistic.getCompleted(), maxTotal, Color.GREEN, true);
        for (int j = 0; j < statistic.getTotal(); j++) {
            drawBin(graphics, x, j, 1, maxTotal, Color.WHITE, false);
        }
    }

    private static void drawBin(Graphics graphics, int x, int y, int amount, int max, Color color, boolean fill) {
        graphics.setColor(color);
        y = (max - amount - y) * BIN_PART_HEIGHT;
        int width = BIN_WIDTH;
        int height = amount * BIN_PART_HEIGHT;
        if (fill) {
            graphics.fillRect(x, y, width, height);
        } else {
            graphics.drawRect(x, y, width, height);
        }
    }
}
