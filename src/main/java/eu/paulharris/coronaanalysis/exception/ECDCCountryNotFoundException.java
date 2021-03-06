package eu.paulharris.coronaanalysis.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class ECDCCountryNotFoundException extends RuntimeException {

    private String country;
}
