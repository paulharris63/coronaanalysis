package eu.paulharris.coronaanalysis.service;

import eu.paulharris.coronaanalysis.domain.ECDCCountryDailySummaryByPosition;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ECDCCaseService {

    List<ECDCCountryDailySummaryByPosition> getByCountry(String countryName);

    Integer getTotalCasesByCountry(String countryName);

    Integer getTotalCasesByCountryAndWeekNumber(String countryName, Integer weekNumber);

    Integer getTotalDeathsByCountry(String countryName);

    Integer getTotalCasesByCountryAndDateRange(String countryName, LocalDate start, LocalDate end);

    Map<Integer, Long> getCasesPerWeek(String countryName);

    Map<Integer, Long> getDeathsPerWeek(String countryName);

    Map<Integer, Integer> getCasesPerDay(String countryName);
}
