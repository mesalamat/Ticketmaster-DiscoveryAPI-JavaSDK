package de.godly.javaticketmaster.response.details;

import com.google.gson.annotations.SerializedName;
import de.godly.javaticketmaster.objects.Attraction;
import de.godly.javaticketmaster.objects.Event;
import de.godly.javaticketmaster.objects.Venue;
import de.godly.javaticketmaster.objects.embedded.*;
import de.godly.javaticketmaster.objects.embedded.location.Address;
import de.godly.javaticketmaster.objects.embedded.location.City;
import de.godly.javaticketmaster.objects.embedded.location.Location;
import de.godly.javaticketmaster.response.DiscoveryResponse;
import lombok.Data;
import org.jetbrains.annotations.Nullable;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
public class VenueDetailsResponse extends DiscoveryResponse {

    private final String name;
    private final String id;
    private final String description;
    private final String additionalInfo;
    private final String url;
    private final String currency;
    private boolean test;
    private double distance;
    private final String units;
    private final String locale;
    private final String postalCode;
    private final String timezone;
    private final State state;
    private final Address address;
    private final Location location;
    private final City city;
    private final List<Market> markets;
    private final List<DMA> dma;
    private final List<Image> images;
    private final Venue.Social social;
    private final BoxOfficeInfo boxOfficeInfo;
    private final String parkingDetail;
    private final String accessibleSeatingDetail;
    @SerializedName("generalInfo")
    private final Venue.VenueRules venueRules;



}