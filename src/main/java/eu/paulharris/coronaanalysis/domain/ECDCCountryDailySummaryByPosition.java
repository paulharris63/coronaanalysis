package eu.paulharris.coronaanalysis.domain;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ECDCCountryDailySummaryByPosition {

    @CsvDate(value = "dd/MM/yyyy")
    @CsvBindByPosition(position = 0)
    LocalDate date;
    @CsvBindByPosition(position = 1)
    Integer day;
    @CsvBindByPosition(position = 2)
    Integer month;
    @CsvBindByPosition(position = 3)
    Integer year;
    @CsvBindByPosition(position = 4)
    Integer cases;
    @CsvBindByPosition(position = 5)
    Integer deaths;
    @CsvBindByPosition(position = 6)
    String fullCountryName;
    @CsvBindByPosition(position = 7)
    String geoId;
    @CsvBindByPosition(position = 8)
    String countryTerritoryCode;
    @CsvBindByPosition(position = 9)
    Integer population;
    @CsvBindByPosition(position = 10)
    String continent;

}
