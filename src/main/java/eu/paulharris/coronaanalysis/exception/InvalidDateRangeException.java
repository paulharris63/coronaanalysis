package eu.paulharris.coronaanalysis.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class InvalidDateRangeException extends RuntimeException {
    private LocalDate start;
    private LocalDate end;
}
