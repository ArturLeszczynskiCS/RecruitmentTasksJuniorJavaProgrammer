package stormwindspeed;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class FileDownloader {

    public void downloadFile(URL url, String fileName) throws IOException {
        FileUtils.copyURLToFile(url, new File(fileName));
    }
}
