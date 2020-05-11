package eu.paulharris.coronaanalysis.service;

import eu.paulharris.coronaanalysis.domain.ECDCCountryDailySummaryByPosition;

import java.util.List;

public interface ECDCCaseService {

    List<ECDCCountryDailySummaryByPosition> getByCountry(String countryName);
    Integer getTotalCasesByCountry(String countryName);
    Integer getTotalDeathsByCountry(String countryName);
}
