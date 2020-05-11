package eu.paulharris.coronaanalysis.service;

import eu.paulharris.coronaanalysis.domain.ECDCCountryDailySummaryByPosition;
import eu.paulharris.coronaanalysis.exception.ECDCCountryNotFoundException;
import eu.paulharris.coronaanalysis.reader.ECDCCsvReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
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
    public Integer getTotalDeathsByCountry(String countryName) {
        validate(countryName);
        return reader.getAllDataAsBeans().stream().filter(x -> countryName.equalsIgnoreCase(x.getGeoId())).mapToInt(ECDCCountryDailySummaryByPosition::getDeaths).sum();
    }

    private void validate(String countryName) {
        if (!reader.getCountryNames().contains(countryName.toUpperCase())) {
            throw new ECDCCountryNotFoundException(countryName);
        }
    }

}
