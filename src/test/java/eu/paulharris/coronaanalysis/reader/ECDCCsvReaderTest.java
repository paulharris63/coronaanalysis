package eu.paulharris.coronaanalysis.reader;

import eu.paulharris.coronaanalysis.domain.ECDCCountryDailySummaryByPosition;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class ECDCCsvReaderTest {

    private static final String FILENAME = "csv/big-withheader";
    private static final String TINY_FILENAME_NOHEADER = "csv/tinyfile-noheader.csv";

    @Before
    public void setUp() {
    }

    @Test
    public void teastReadAllAsStrings() throws Exception {
        ECDCCsvReader cr = new ECDCCsvReader(TINY_FILENAME_NOHEADER);
        List<String[]> result = cr.readAllAsStrings(FILENAME);
        assertEquals(13212, result.size());
        result.forEach(entry -> assertEquals(11, entry.length));
    }

    @Test
    public void testReadAllAsBeans() throws Exception {
        ECDCCsvReader cr = new ECDCCsvReader(TINY_FILENAME_NOHEADER);
        cr.readAllAsBeansByPosition(TINY_FILENAME_NOHEADER);
        List<ECDCCountryDailySummaryByPosition> result = cr.getAllDataAsBeans();
        assertEquals(50, result.size());
        assertEquals(37172386, result.get(49).getPopulation());
        assertEquals(LocalDate.of(2020, 02, 21), result.get(49).getDate());
    }
}
