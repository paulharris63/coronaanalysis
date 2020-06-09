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
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
            return getCountryStream(countryName).collect(Collectors.toList());
        }
    }

    @Override
    public Integer getTotalCasesByCountry(String countryName) {
        validate(countryName);
        return getCountryStream(countryName).mapToInt(ECDCCountryDailySummaryByPosition::getCases).sum();
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
        return getCountryStream(countryName).mapToInt(ECDCCountryDailySummaryByPosition::getDeaths).sum();
    }

    @Override
    public Integer getTotalCasesByCountryAndDateRange(String countryName, LocalDate start, LocalDate end) {
        validate(countryName);
        validate(start, end);
        return getCountryStream(countryName).filter(x -> !x.getDate().isBefore(start) && !x.getDate().isAfter(end)).mapToInt(ECDCCountryDailySummaryByPosition::getCases).sum();
    }

    private Stream<ECDCCountryDailySummaryByPosition> getCountryStream(String countryName) {
        return reader.getAllDataAsBeans().stream().filter(x -> countryName.equalsIgnoreCase(x.getGeoId()));
    }

    @Override
    public Map<Integer, Long> getCasesPerWeek(String countryName) {
        Function<ECDCCountryDailySummaryByPosition, Integer> getWeekNumber = this::getWeekOfYearFromDate;
        return getCountryStream(countryName).collect(Collectors.groupingBy(getWeekNumber, Collectors.summingLong(ECDCCountryDailySummaryByPosition::getCases)));
    }

    @Override
    public Map<Integer, Long> getDeathsPerWeek(String countryName) {
        Function<ECDCCountryDailySummaryByPosition, Integer> getWeekNumber = this::getWeekOfYearFromDate;
        return getCountryStream(countryName).collect(Collectors.groupingBy(getWeekNumber, Collectors.summingLong(ECDCCountryDailySummaryByPosition::getDeaths)));
    }

    @Override
    public Map<Integer, Integer> getCasesPerDay(String countryName) {
        return getCountryStream(countryName).collect(Collectors.toMap(x -> x.getDate().getDayOfYear(), ECDCCountryDailySummaryByPosition::getCases));
    }

    private Integer getWeekOfYearFromDate(ECDCCountryDailySummaryByPosition dailyData) {
        TemporalField woy = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear();
        return dailyData.getDate().get(woy);
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
