package eu.paulharris.coronaanalysis.controller;

import eu.paulharris.coronaanalysis.domain.ECDCCountryDailySummaryByPosition;
import eu.paulharris.coronaanalysis.exception.InvalidDateStringException;
import eu.paulharris.coronaanalysis.service.ECDCCaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class ECDCCasesController implements BaseController {

    private final ECDCCaseService casesService;

    @Autowired
    ECDCCasesController(final ECDCCaseService casesService) {
        this.casesService = casesService;
    }

    @RequestMapping(path = BASE_URL + "/{country}", method = RequestMethod.GET)
    ResponseEntity<List<ECDCCountryDailySummaryByPosition>> get(@PathVariable final String country) {
        return new ResponseEntity<>(casesService.getByCountry(country), new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(path = TOTAL_CASES_URL + "/{country}", method = RequestMethod.GET)
    ResponseEntity<Integer> getTotalCasesByCountry(@PathVariable final String country) {
        return new ResponseEntity<>(casesService.getTotalCasesByCountry(country), new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(path = TOTAL_CASES_PER_DATE_URL + "/{country}/{start}/{end}", method = RequestMethod.GET)
    ResponseEntity<Integer> getTotalCasesByCountryByDataRange(@PathVariable final String country, @PathVariable final String start, @PathVariable final String end) {
        try {
            LocalDate startDate = convertFromString(start);
            LocalDate endDate = convertFromString(end);
            return new ResponseEntity<>(casesService.getTotalCasesByCountryAndDateRange(country, startDate, endDate), new HttpHeaders(), HttpStatus.OK);
        } catch (DateTimeException e) {
            throw new InvalidDateStringException(start, end);
        }
    }

    @RequestMapping(path = TOTAL_CASES_PER_WEEK_URL + "/{country}/{week}", method = RequestMethod.GET)
    ResponseEntity<Integer> getTotalCasesByCountryByWeek(@PathVariable final String country, @PathVariable final Integer week) {
        return new ResponseEntity<>(casesService.getTotalCasesByCountryAndWeekNumber(country, week), new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(path = TOTAL_DEATHS_URL + "/{country}", method = RequestMethod.GET)
    ResponseEntity<Integer> getTotalDeathsByCountry(@PathVariable final String country) {
        return new ResponseEntity<>(casesService.getTotalDeathsByCountry(country), new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(path = TOTAL_CASES_PER_WEEK_URL + "/{country}", method = RequestMethod.GET)
    ResponseEntity<Map<Integer, Long>> getTotalCasesPerWeekByCountry(@PathVariable final String country) {
        return new ResponseEntity<>(casesService.getCasesPerWeek(country), new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(path = TOTAL_DEATHS_PER_WEEK_URL + "/{country}", method = RequestMethod.GET)
    ResponseEntity<Map<Integer, Long>> getTotalDeathsPerWeekByCountry(@PathVariable final String country) {
        return new ResponseEntity<>(casesService.getDeathsPerWeek(country), new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(path = TOTAL_CASES_PER_DAY_URL + "/{country}", method = RequestMethod.GET)
    ResponseEntity<Map<Integer, Integer>> getTotalCasesPerDayByCountry(@PathVariable final String country) {
        return new ResponseEntity<>(casesService.getCasesPerDay(country), new HttpHeaders(), HttpStatus.OK);
    }

    private LocalDate convertFromString(final String dateString) {
        return LocalDate.of(Integer.parseInt(dateString.substring(0, 4)), Integer.parseInt(dateString.substring(5, 7)), Integer.parseInt(dateString.substring(8, 10)));

    }
}
