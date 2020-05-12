package eu.paulharris.coronaanalysis.controller;

import eu.paulharris.coronaanalysis.exception.ECDCCountryNotFoundException;
import eu.paulharris.coronaanalysis.exception.InvalidDateRangeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = ECDCCountryNotFoundException.class)
    public ResponseEntity<String> exception(ECDCCountryNotFoundException exception) {
        return new ResponseEntity<>(String.format("Country (\"%s\") not found", exception.getCountry()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = InvalidDateRangeException.class)
    public ResponseEntity<String> exception(InvalidDateRangeException exception) {
        return new ResponseEntity<>(String.format("Start date (\"%s\") is after end date(\"%s\")", exception.getStart(), exception.getEnd()), HttpStatus.BAD_REQUEST);
    }
}
