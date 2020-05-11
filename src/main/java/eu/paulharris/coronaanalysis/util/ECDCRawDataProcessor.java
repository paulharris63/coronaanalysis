package eu.paulharris.coronaanalysis.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

// https://www.baeldung.com/java-download-file

public class ECDCRawDataProcessor {
    private static final String DATA_FILE_URL = "https://opendata.ecdc.europa.eu/covid19/casedistribution/csv";
    private static final String DATA_FILE_NAME = "csv/download";
    private static final int CONNECT_TIMEOUT = 10*1000;
    private static final int READ_TIMEOUT = 10*1000;

    public void fetchDataFile() throws IOException {
        FileUtils.copyURLToFile(
                new URL(DATA_FILE_URL),
                new File(DATA_FILE_NAME),
                CONNECT_TIMEOUT,
                READ_TIMEOUT);
    }
}
