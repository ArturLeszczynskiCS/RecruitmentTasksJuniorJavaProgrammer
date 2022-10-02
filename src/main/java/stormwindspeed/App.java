package stormwindspeed;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

public class App {
    public static void main(String[] args) throws IOException {
        FileDownloader fileDownloader = new FileDownloader();
        URL url = null;

        try {
            url = new URL("https://www.nhc.noaa.gov/data/hurdat/hurdat2-nepac-1949-2016-041317.txt");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        fileDownloader.downloadFile(url, "dataFile.xls");

        ArrayList<ArrayList<Integer>> windSpeedValues = new ArrayList<>();
        ArrayList<Integer> windSpeedMaxValues = new ArrayList<>();
        ArrayList<String> stormName = new ArrayList<>();

        try {
            BufferedReader in = new BufferedReader(new FileReader("dataFile.xls"));

            String line;
            String stormNameStr = "";
            int row = -1;
            int column = -1;

            boolean saveStormName = false;
            boolean saveLine = false;

            while ((line = in.readLine()) != null) {
                if (saveLine) {
                    try {
                        int year = Integer.parseInt(line.substring(0, 4));
                        if (year >= 2015) {
                            if (saveStormName) {
                                stormName.add(stormNameStr);
                                row++;
                            }
                            column++;
                            try {
                                if (saveStormName) {
                                    windSpeedValues.add(new ArrayList<>());
                                    saveStormName = false;
                                }
                                windSpeedValues.get(row).add(column, Integer.parseInt(line.substring(39, 41)));
                            } catch (NumberFormatException e) {
                                e.getStackTrace();
                            }
                        }
                    } catch (NumberFormatException e) {
                        e.getStackTrace();
                    }
                }

                if (line.contains("EP")) {
                    saveStormName = true;
                    String stormNameCheck = StringUtils.substringBetween(line, ",", ",").trim();

                    if (stormNameCheck.substring((stormNameCheck.length() - 1)).equals(("A"))) {
                        stormNameStr = stormNameCheck;
                        saveLine = true;
                    } else {
                        saveLine = false;
                        column = -1;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        getWindSpeedMaxValues(windSpeedValues, windSpeedMaxValues);
        printStormNamesAndWindSpeedMaxValues(windSpeedMaxValues, stormName);
    }

    private static void printStormNamesAndWindSpeedMaxValues(ArrayList<Integer> windSpeedMaxValues, ArrayList<String> stormName) {
        for (int i = 0; i < windSpeedMaxValues.size(); i++) {
            System.out.println(stormName.get(i) + " " + windSpeedMaxValues.get(i));
        }
    }

    private static void getWindSpeedMaxValues(ArrayList<ArrayList<Integer>> windSpeedValues, ArrayList<Integer> windSpeedMaxValues) {
        for (ArrayList<Integer> row : windSpeedValues) {
            Integer max = Collections.max(row);
            windSpeedMaxValues.add(max);
        }
    }
}
