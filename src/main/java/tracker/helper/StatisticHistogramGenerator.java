package tracker.helper;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import tracker.entity.IssueStatistic;

public class StatisticHistogramGenerator {
    private static final int REC_HISTOGRAM_HEIGHT = 200;
    private static final int REC_HISTOGRAM_WIDTH = 1800;

    private static final int MIN_BIN_PART_HEIGHT = 3;
    private static final int MAX_BIN_PART_HEIGHT = 50;

    private static final int MIN_BIN_WIDTH = 5;
    private static final int MAX_BIN_WIDTH = 50;

    private int binPartHeight;
    private int binWidth;
    private List<IssueStatistic> data;

    private int maxTotal;
    private BufferedImage image;

    public void setBinPartHeight(Integer binPartHeight) {
        this.binPartHeight = binPartHeight == null ? 0 : binPartHeight.intValue();
    }

    public void setBinWidth(Integer binWidth) {
        this.binWidth = binWidth == null ? 0 : binWidth.intValue();
    }

    public void setData(List<IssueStatistic> data) {
        this.data = data;
    }

    public BufferedImage generate() {
        checkData();

        if (data.isEmpty()) {
            return generateNoDataImage();
        }

        image = generateImage();
        drawBins();
        return image;
    }

    private void checkData() {
        if (data == null) {
            data = Collections.emptyList();
        }

        calculateMaxBinAmount();

        if (binPartHeight == 0 && !data.isEmpty()) {
            binPartHeight = Math.max(MIN_BIN_PART_HEIGHT, REC_HISTOGRAM_HEIGHT / maxTotal);
            binPartHeight = Math.min(binPartHeight, MAX_BIN_PART_HEIGHT);
        }

        if (binWidth == 0 && !data.isEmpty()) {
            binWidth = Math.min(MAX_BIN_WIDTH, REC_HISTOGRAM_WIDTH / data.size());
            binWidth = Math.max(MIN_BIN_WIDTH, binWidth);
        }
    }

    private  BufferedImage generateNoDataImage() {
        BufferedImage image = new BufferedImage(70, 30, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, 70, 30);

        graphics.setColor(Color.RED);
        graphics.drawRect(0, 0, 69, 29);

        graphics.setColor(Color.RED);
        graphics.drawString("No data", 15, 20);

        return image;
    }

    private void calculateMaxBinAmount() {
        this.maxTotal = data.stream()
                .map(s -> Math.max(s.getCompleted(), s.getTotal()))
                .collect(Collectors.summarizingInt(Integer::intValue))
                .getMax();
    }

    private  BufferedImage generateImage() {
        int length = data.size();
        int width = length * binWidth + 1;
        int height = maxTotal * binPartHeight;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);
        graphics.dispose();
        return image;
    }

    private  void drawBins() {
        Graphics graphics = image.getGraphics();
        for (int i = 0; i < data.size(); i++) {
            IssueStatistic statistic = data.get(i);
            drawBin(graphics, i, statistic, maxTotal, binPartHeight, binWidth);
        }
    }

    private  void drawBin(Graphics graphics, int i, IssueStatistic statistic, int maxTotal, int binPartHeight, int binWidth) {
        int x = i * binWidth;
        drawBin(graphics, x, 0, statistic.getTotal(), maxTotal, Color.RED, true);
        drawBin(graphics, x, 0, statistic.getCompleted(), maxTotal, Color.GREEN, true);
        for (int j = 0; j < statistic.getTotal(); j++) {
            drawBin(graphics, x, j, 1, maxTotal, Color.WHITE, false);
        }
    }

    private  void drawBin(Graphics graphics, int x, int y, int amount, int max, Color color, boolean fill) {
        graphics.setColor(color);
        y = (max - amount - y) * binPartHeight;
        int width = binWidth;
        int height = amount * binPartHeight;
        if (fill) {
            graphics.fillRect(x, y, width, height);
        } else {
            graphics.drawRect(x, y, width, height);
        }
    }
}
