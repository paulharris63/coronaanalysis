package eu.paulharris.coronaanalysis.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class InvalidDateRangeException extends RuntimeException {
    private LocalDate start;
    private LocalDate end;
}
