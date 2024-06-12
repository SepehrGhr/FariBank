package ir.ac.kntu;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.BasicStroke;
import java.awt.Font;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class ChartHelper {
    public void generateHtmlReport(String htmlFilePath, String chartFilePath, User user) {
        StringBuilder htmlContent = new StringBuilder();
        htmlContent.append("<!DOCTYPE html>")
                .append("<html lang=\"en\">").append("<head>").append("<meta charset=\"UTF-8\">")
                .append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">")
                .append("<title>Account Report</title>").append("<style>")
                .append("body { font-family: Arial, sans-serif; margin: 0; padding: 20px; background-color: #f4f4f9; }")
                .append("h1 { text-align: center; color: #333; }")
                .append(".balance, .transactions { margin: 20px 0; }")
                .append(".balance p { font-size: 1.2em; color: #333; }")
                .append(".transactions table { width: 100%; border-collapse: collapse; margin-top: 10px; }")
                .append(".transactions th, .transactions td { padding: 10px; border: 1px solid #ddd; text-align: left; }")
                .append(".transactions th { background-color: #f0f0f0; color: #333; }")
                .append("img { display: block; margin: 0 auto; max-width: 100%; height: auto; }")
                .append("</style>").append("</head>").append("<body>")
                .append("<h1>Account Report for ").append(user.getName()).append(" ").append(user.getLastName()).append("</h1>")
                .append("<div class=\"balance\">")
                .append("<h2>Current Balance</h2>")
                .append("<p>$").append(user.getAccount().getBalance()).append("</p>").append("</div>")
                .append("<div class=\"transactions\">").append("<h2>Transactions</h2>").append("<table>")
                .append("<tr>").append("<th>Date</th>").append("<th>Amount</th>").append("</tr>");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());
        Main.getUsers().getCurrentUser().addReceiptsToHTML(htmlContent, formatter);
        htmlContent.append("</table>").append("</div>").append("<div class=\"chart\">")
                .append("<h2>Balance Over Time</h2>").append("<img src=\"").append(chartFilePath).append("\" alt=\"Balance Chart\">")
                .append("</div>").append("</body>").append("</html>");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(htmlFilePath))) {
            writer.write(htmlContent.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateBalanceChart(String filePath) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Main.getUsers().getCurrentUser().addReceiptsToChart(dataset, formatter);
        JFreeChart lineChart = ChartFactory.createLineChart(
                "Account Balance Over Time",
                "Date",
                "Balance",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);
        StandardChartTheme chartTheme = new StandardChartTheme("JFree/Shadow", true);
        chartTheme.apply(lineChart);
        CategoryPlot plot = setChartBackground(lineChart);
        LineAndShapeRenderer renderer = setRenderer();
        plot.setRenderer(renderer);
        setFonts(plot, lineChart);
        lineChart.setAntiAlias(true);
        int width = 640;
        int height = 480;
        File lineChartFile = new File(filePath);
        try {
            ChartUtils.saveChartAsJPEG(lineChartFile, lineChart, width, height);
            System.out.println(Color.GREEN + "Your account reports have been successfully generated" + Color.RESET);
        } catch (IOException e) {
            System.out.println(Color.RED + "Creating chart was not possible" + Color.RED);
        }
    }

    private void setFonts(CategoryPlot plot, JFreeChart lineChart) {
        Font titleFont = new Font("SansSerif", Font.BOLD, 18);
        Font labelFont = new Font("SansSerif", Font.PLAIN, 12);
        lineChart.getTitle().setFont(titleFont);
        plot.getDomainAxis().setLabelFont(labelFont);
        plot.getRangeAxis().setLabelFont(labelFont);
        plot.getDomainAxis().setTickLabelFont(labelFont);
        plot.getRangeAxis().setTickLabelFont(labelFont);
    }

    private LineAndShapeRenderer setRenderer() {
        LineAndShapeRenderer renderer = new LineAndShapeRenderer();
        renderer.setSeriesPaint(0, new java.awt.Color(0, 122, 204));
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));
        return renderer;
    }

    private CategoryPlot setChartBackground(JFreeChart lineChart) {
        CategoryPlot plot = lineChart.getCategoryPlot();
        plot.setBackgroundPaint(java.awt.Color.WHITE);
        plot.setDomainGridlinePaint(java.awt.Color.GRAY);
        plot.setRangeGridlinePaint(java.awt.Color.GRAY);
        plot.setOutlineVisible(false);
        return plot;
    }

}
