package eu.paulharris.coronaanalysis.service;

import eu.paulharris.coronaanalysis.domain.ECDCCountryDailySummaryByPosition;
import eu.paulharris.coronaanalysis.exception.ECDCCountryNotFoundException;
import eu.paulharris.coronaanalysis.exception.InvalidDateRangeException;
import eu.paulharris.coronaanalysis.reader.ECDCCsvReader;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ECDCCaseServiceImplTest {

    private static final int ENTRY_1_CASES = 100;
    private static final int ENTRY_1_DEATHS = 10;
    private static final int ENTRY_1_POPULATION = 1000 * 1000;
    private static final String ENTRY_1_FULLCOUNTRYNAME = "Country1";
    private static final String ENTRY_1_GEOID = "GeoId1";
    private static final int ENTRY_2_CASES = 200;
    private static final int ENTRY_2_DEATHS = 20;
    private static final int ENTRY_2_POPULATION = 2 * 1000 * 1000;
    private static final String ENTRY_2_AND_3_FULLCOUNTRYNAME = "Country2";
    private static final String ENTRY_2_AND_3_GEOID = "GeoId2";
    private static final int ENTRY_3_CASES = 300;
    private static final int ENTRY_3_DEATHS = 30;
    private static final int ENTRY_3_POPULATION = 3 * 1000 * 1000;
    private static final String INVALID_COUNTRY = "Lolaland";

    private List<ECDCCountryDailySummaryByPosition> allDataAsBeans;

    private ECDCCaseService ecdcCaseService;

    @Before
    public void setUp() {
        populateReader();
        ECDCCsvReader reader = mock(ECDCCsvReader.class);
        when(reader.getAllDataAsBeans()).thenReturn(allDataAsBeans);
        when(reader.getCountryNames()).thenReturn(allDataAsBeans.stream().map(ECDCCountryDailySummaryByPosition::getGeoId).distinct().map(String::toUpperCase).collect(Collectors.toList()));
        ecdcCaseService = new ECDCCaseServiceImpl(reader);
    }

    @Test
    public void testGetByCountry() {
        List<ECDCCountryDailySummaryByPosition> result = ecdcCaseService.getByCountry(ENTRY_1_GEOID);
        assertEquals(1, result.size());
        assertEquals(ENTRY_1_FULLCOUNTRYNAME, result.get(0).getFullCountryName());
    }

    @Test
    public void testGetByCountryInvalidCountry() {
        try {
            ecdcCaseService.getByCountry(INVALID_COUNTRY);
            fail();
        } catch (ECDCCountryNotFoundException e) {
            assertEquals(INVALID_COUNTRY, e.getCountry());
        }
    }

    @Test
    public void testGetTotalCasesByCountry() {
        Integer result = ecdcCaseService.getTotalCasesByCountry(ENTRY_2_AND_3_GEOID);
        assertEquals(ENTRY_2_CASES + ENTRY_3_CASES, result);
    }

    @Test
    public void testGetTotalDeathsByCountry() {
        Integer result = ecdcCaseService.getTotalDeathsByCountry(ENTRY_2_AND_3_GEOID);
        assertEquals(ENTRY_2_DEATHS + ENTRY_3_DEATHS, result);
    }

    @Test
    public void testGetTotalCasesByCountryAndDateRangeOneDay() {
        Integer result = ecdcCaseService.getTotalCasesByCountryAndDateRange(ENTRY_2_AND_3_GEOID, LocalDate.of(2020, 2, 2), LocalDate.of(2020, 2, 2));
        assertEquals(ENTRY_2_CASES, result);
    }

    @Test
    public void testGetTotalCasesByCountryAndDateRangeTwoDays() {
        Integer result = ecdcCaseService.getTotalCasesByCountryAndDateRange(ENTRY_2_AND_3_GEOID, LocalDate.of(2020, 2, 2), LocalDate.of(2020, 2, 3));
        assertEquals(ENTRY_2_CASES + ENTRY_3_CASES, result);
    }

    @Test
    public void testGetTotalCasesByCountryAndDateRangeInvalidRange() {
        try {
            ecdcCaseService.getTotalCasesByCountryAndDateRange(ENTRY_2_AND_3_GEOID, LocalDate.of(2020, 2, 3), LocalDate.of(2020, 2, 2));
            fail();
        } catch (InvalidDateRangeException e) {
            assertEquals(LocalDate.of(2020, 2, 3), e.getStart());
            assertEquals(LocalDate.of(2020, 2, 2), e.getEnd());
        }
    }

    private void populateReader() {
        ECDCCountryDailySummaryByPosition entry1 = new ECDCCountryDailySummaryByPosition();
        entry1.setDate(LocalDate.of(2020, 2, 1));
        entry1.setDay(1);
        entry1.setMonth(2);
        entry1.setYear(2020);
        entry1.setCases(ENTRY_1_CASES);
        entry1.setDeaths(ENTRY_1_DEATHS);
        entry1.setFullCountryName(ENTRY_1_FULLCOUNTRYNAME);
        entry1.setGeoId(ENTRY_1_GEOID);
        entry1.setCountryTerritoryCode("countryTerritoryCode1");
        entry1.setPopulation(ENTRY_1_POPULATION);
        entry1.setContinent("continent1");
        ECDCCountryDailySummaryByPosition entry2 = new ECDCCountryDailySummaryByPosition();
        entry2.setDate(LocalDate.of(2020, 2, 2));
        entry2.setDay(2);
        entry2.setMonth(2);
        entry2.setYear(2020);
        entry2.setCases(ENTRY_2_CASES);
        entry2.setDeaths(ENTRY_2_DEATHS);
        entry2.setFullCountryName(ENTRY_2_AND_3_FULLCOUNTRYNAME);
        entry2.setGeoId(ENTRY_2_AND_3_GEOID);
        entry2.setCountryTerritoryCode("countryTerritoryCode2");
        entry2.setPopulation(ENTRY_2_POPULATION);
        entry2.setContinent("continent2");
        ECDCCountryDailySummaryByPosition entry3 = new ECDCCountryDailySummaryByPosition();
        entry3.setDate(LocalDate.of(2020, 2, 3));
        entry3.setDay(3);
        entry3.setMonth(2);
        entry3.setYear(2020);
        entry3.setCases(ENTRY_3_CASES);
        entry3.setDeaths(ENTRY_3_DEATHS);
        entry3.setFullCountryName(ENTRY_2_AND_3_FULLCOUNTRYNAME);
        entry3.setGeoId(ENTRY_2_AND_3_GEOID);
        entry3.setCountryTerritoryCode("countryTerritoryCode3");
        entry3.setPopulation(ENTRY_3_POPULATION);
        entry3.setContinent("continent3");
        allDataAsBeans = Stream.of(new ECDCCountryDailySummaryByPosition[]{entry1, entry2, entry3}).collect(Collectors.toList());
    }
}