package eu.paulharris.coronaanalysis.util;

import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

public class ECDCRawDataProcessorTest {

    @Ignore
    @Test
    public void testDownload() throws IOException {
        ECDCRawDataProcessor proc = new ECDCRawDataProcessor();
        proc.fetchDataFile();
    }
}
