package eu.paulharris.coronaanalysis.service;

import eu.paulharris.coronaanalysis.domain.ECDCCountryDailySummaryByPosition;
import eu.paulharris.coronaanalysis.exception.ECDCCountryNotFoundException;
import eu.paulharris.coronaanalysis.exception.InvalidDateRangeException;
import eu.paulharris.coronaanalysis.reader.ECDCCsvReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ECDCCaseServiceImpl implements ECDCCaseService {

    private final ECDCCsvReader reader;

    @Autowired
    ECDCCaseServiceImpl(final ECDCCsvReader reader) {
        this.reader = reader;
    }

    @Override
    public List<ECDCCountryDailySummaryByPosition> getByCountry(final String countryName) {
        if ("all".equalsIgnoreCase(countryName)) {
            return reader.getAllDataAsBeans();
        } else {
            validate(countryName);
            return reader.getAllDataAsBeans().stream().filter(x -> countryName.equalsIgnoreCase(x.getGeoId())).collect(Collectors.toList());
        }
    }

    @Override
    public Integer getTotalCasesByCountry(String countryName) {
        validate(countryName);
        return reader.getAllDataAsBeans().stream().filter(x -> countryName.equalsIgnoreCase(x.getGeoId())).mapToInt(ECDCCountryDailySummaryByPosition::getCases).sum();
    }

    @Override
    public Integer getTotalCasesByCountryAndWeekNumber(String countryName, Integer weekNumber) {
        LocalDate week = LocalDate.now().with(ChronoField.ALIGNED_WEEK_OF_YEAR, weekNumber - 1);
        LocalDate start = week.with(DayOfWeek.MONDAY);
        LocalDate end = start.plusDays(6);
        return getTotalCasesByCountryAndDateRange(countryName, start, end);
    }

    @Override
    public Integer getTotalDeathsByCountry(String countryName) {
        validate(countryName);
        return reader.getAllDataAsBeans().stream().filter(x -> countryName.equalsIgnoreCase(x.getGeoId())).mapToInt(ECDCCountryDailySummaryByPosition::getDeaths).sum();
    }

    @Override
    public Integer getTotalCasesByCountryAndDateRange(String countryName, LocalDate start, LocalDate end) {
        validate(countryName);
        validate(start, end);
        return reader.getAllDataAsBeans().stream().filter(x -> countryName.equalsIgnoreCase(x.getGeoId())).filter(x -> !x.getDate().isBefore(start) && !x.getDate().isAfter(end)).mapToInt(ECDCCountryDailySummaryByPosition::getCases).sum();
    }

    private void validate(final String countryName) {
        if (!reader.getCountryNames().contains(countryName.toUpperCase())) {
            throw new ECDCCountryNotFoundException(countryName);
        }
    }

    private void validate(final LocalDate start, final LocalDate end) {
        if (start.isAfter(end)) {
            throw new InvalidDateRangeException(start, end);
        }
    }

}
