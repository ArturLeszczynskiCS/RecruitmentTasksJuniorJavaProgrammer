package statisticselectricityimbalancehtml;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class App {

    public static void main(String[] args) {

        File f = new File("electricityImbalancesStatistics.html");
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(f));
            bw.write(createTableHeader());
        } catch (IOException e) {
            e.printStackTrace();
        }

        int hoursCounter = 1;
        final String url = "https://www.ote-cr.cz/en/statistics/electricity-imbalances";

        try {
            if (bw != null) {
                final Document document = Jsoup.connect(url).get();

                for (Element row : document.select("table.report_table tr")) {
                    if (!row.select("td:nth-of-type(1)").text().equals("")) {
                        bw.write("<tr>");
                        bw.write("<td> " + hoursCounter + "</td>");

                        for (int i = 1; i < 14; i++) {
                            bw.write("<td> " + row.select("td:nth-of-type(" + i + ")").text() + "</td>");
                        }

                        bw.write("</tr>");
                        hoursCounter++;
                    }
                }
                bw.write("</table></body></html>");
                bw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String createTableHeader() {
        return "<html>" + "<body>" + "<table border ='2'>" + "<tr>" + "<th>Hour</td>" + "<th>System Imbalance (Mhw)</th>" + "<th>Absolute imbalance sum (Mhw)</th>" + "<th>Positive imbalance (Mhw)</th>" + "<th>Negative imbalance (Mhw)</th>" + "<th>Rounded imbalance (Mhw)</th>" + "<th>Cost of RE (CZK)</th>" + "<th>Cost of imbalance (CZK)</th>" + "<th>Settlement price - imbalance (CZK/MWh)</th>" + "<th>Settlement price - counter imbalance (CZK/MWh)</th>" + "<th>Price according to protective RE component (CZK/MWh)</th>" + "<th>Price according to RE component (CZK/MWh)</th>" + "<th>Price according to IM component (CZK/MWh)</th>" + "<th>Price according to SO component (CZK/MWh)</th>" + "</tr>";
    }
}

