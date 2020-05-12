package eu.paulharris.coronaanalysis.controller;

public interface BaseController {

    String BASE_URL = "/api/coronavirus";
    String TOTAL_CASES_URL = BASE_URL + "/cases/total";
    String TOTAL_DEATHS_URL = BASE_URL + "/deaths/total";
    String TOTAL_CASES_PER_DATE_URL = BASE_URL + "/cases/daterange";

}
