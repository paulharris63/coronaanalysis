package eu.paulharris.coronaanalysis.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ECDCCountryNotFoundException extends RuntimeException {

    private String country;
}
