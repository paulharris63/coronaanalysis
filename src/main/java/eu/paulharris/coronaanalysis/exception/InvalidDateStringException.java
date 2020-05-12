package eu.paulharris.coronaanalysis.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class InvalidDateStringException extends RuntimeException {
    private String start;
    private String end;
}
