package de.godly.javaticketmaster.objects.embedded;

import lombok.Data;

@Data
public class BoxOfficeInfo {

    private final String phoneNumberDetail;
    private final String openHoursDetail;
    private final String acceptedPaymentDetail;
    private final String willCallDetail;

}
