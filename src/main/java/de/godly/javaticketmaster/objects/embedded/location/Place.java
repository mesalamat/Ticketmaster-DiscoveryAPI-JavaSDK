package de.godly.javaticketmaster.objects.embedded.location;

import de.godly.javaticketmaster.objects.embedded.State;
import lombok.Data;

@Data
public class Place {

    private final Area area;
    private final Address address;
    private final City city;
    private final State state;
    private final Country country;
    private final String postalCode;
    private final Location location;
    private final String name;



}
