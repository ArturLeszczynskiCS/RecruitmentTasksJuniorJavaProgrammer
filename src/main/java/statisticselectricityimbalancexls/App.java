package statisticselectricityimbalancexls;

import org.apache.poi.ss.usermodel.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class App {

    public static void main(String[] args) {
        FileDownloader fileDownloader = new FileDownloader();
        URL url = null;

        try {
            url = new URL(getUrl());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            fileDownloader.downloadFile(url, "dataFile.xls");
            File file = new File("dataFile.xls");
            FileInputStream fileInputStream = new FileInputStream(file);
            Workbook wb = WorkbookFactory.create(fileInputStream);
            Sheet sheet = wb.getSheetAt(0);

            Row rowDate = sheet.getRow(2);
            Cell cellDate = rowDate.getCell(0);
            String cellDateStr = cellDate.getStringCellValue().substring(45, 55);

            for (int i = 1, j = 0; i <= 13; i++) {
                String time = "";
                String name = "";
                for (Row row : sheet) {
                    Cell cellHour = row.getCell(j);
                    Cell cell = row.getCell(i);


                    if (cellHour != null && cellHour.getCellType().equals(CellType.NUMERIC)) {
                        if (cellHour.getNumericCellValue() < 10) {
                            time = "0" + ((int) cellHour.getNumericCellValue() - 1) + ":00";
                        } else {
                            time = ((int) cellHour.getNumericCellValue() - 1) + ":00";
                        }
                    }

                    if (cell != null && cell.getCellType().equals(CellType.STRING)) {
                        name = cell.getStringCellValue().replaceAll("\\(.*\\)", "");
                    } else if (cell != null && !name.trim().equals("")) {
                        System.out.println(name.trim() + ";" + cellDateStr + " " +
                                time + "; " + cell.getNumericCellValue());
                    }
                }
            }
            fileInputStream.close();
        } catch (
                Exception e) {
            e.printStackTrace();
        }
    }

    public static String getUrl() {
        final String urlSite = "https://www.ote-cr.cz/en/statistics/electricity-imbalances";
        String url = "";
        try {
            final Document document = Jsoup.connect(urlSite).get();
            Element link = document.select("p.report_attachment_links > a").first();
            if (link != null) {
                url = "https://www.ote-cr.cz" + link.attr("href");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return url;
    }
}

