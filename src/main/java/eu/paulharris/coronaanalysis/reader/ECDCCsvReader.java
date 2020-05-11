package eu.paulharris.coronaanalysis.reader;

import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBeanBuilder;
import eu.paulharris.coronaanalysis.domain.ECDCCountryDailySummaryByPosition;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Data
@Component
public class ECDCCsvReader {

    private static final String FILENAME_NOHEADER = "csv/download-noheader.csv";

    private List<ECDCCountryDailySummaryByPosition> allDataAsBeans;
    private List<String> countryNames;

    ECDCCsvReader() {
        readAllAsBeansByPosition(FILENAME_NOHEADER);
        countryNames = getUniqueCountryNames();
    }

    ECDCCsvReader(final String fileName) {
        readAllAsBeansByPosition(fileName);
        countryNames = getUniqueCountryNames();
    }

    public List<String[]> readAllAsStrings(final String fileName) throws Exception {
        Reader reader = Files.newBufferedReader(Paths.get(
                ClassLoader.getSystemResource(fileName).toURI()));
        CSVReader csvReader = new CSVReader(reader);
        List<String[]> list = new ArrayList<>();
        list = csvReader.readAll();
        reader.close();
        csvReader.close();
        return list;
    }

    protected void readAllAsBeansByPosition(final String fileName) {
        try {
            log.info("Reading file as beans");
            Path path = Paths.get(
                    ClassLoader.getSystemResource(fileName).toURI());
            ColumnPositionMappingStrategy ms = new ColumnPositionMappingStrategy();
            ms.setType(ECDCCountryDailySummaryByPosition.class);

            try (Reader reader = Files.newBufferedReader(path);) {
                allDataAsBeans = new CsvToBeanBuilder(reader)
                        .withType(ECDCCountryDailySummaryByPosition.class)
                        .withMappingStrategy(ms)
                        .build()
                        .parse();
                log.info("Completed file read");
            }
        } catch (Exception e) {
            log.error("Failed to read data file", e);
        }
    }

    private List<String> getUniqueCountryNames() {
        return allDataAsBeans.stream().map(ECDCCountryDailySummaryByPosition::getGeoId).distinct().map(String::toUpperCase).collect(Collectors.toList());
    }

}
