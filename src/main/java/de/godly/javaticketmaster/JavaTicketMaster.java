package de.godly.javaticketmaster;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.neovisionaries.i18n.CountryCode;
import de.godly.javaticketmaster.objects.Attraction;
import de.godly.javaticketmaster.request.details.*;
import de.godly.javaticketmaster.request.search.AttractionsSearchRequest;
import de.godly.javaticketmaster.request.search.ClassificationsRequest;
import de.godly.javaticketmaster.request.search.EventsSearchRequest;
import de.godly.javaticketmaster.request.search.VenueSearchRequest;
import de.godly.javaticketmaster.response.details.*;
import de.godly.javaticketmaster.response.search.AttractionsResponse;
import de.godly.javaticketmaster.response.search.ClassificationsResponse;
import de.godly.javaticketmaster.response.search.EventsResponse;
import de.godly.javaticketmaster.response.search.VenuesResponse;
import lombok.*;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Getter
public class JavaTicketMaster {

    @Getter(value = AccessLevel.PUBLIC)
    private final String apiKey;
    @Getter(value = AccessLevel.PUBLIC)
    private final OkHttpClient okHttpClient;
    @Getter(value = AccessLevel.PUBLIC)
    private final Gson gson;
    @Getter (value = AccessLevel.PUBLIC)
    private final Ratelimit ratelimit;
    private String userAgent = "Ticketmaster JavaSDK";


    /**
     * Constructs a new JavaTicketMaster Instance with provided parameters
     * @param apiKey API-Key for the Ticketmaster API
     * @param userAgent Custom UserAgent given you want to use a Custom One
     */
    public JavaTicketMaster(String apiKey, String userAgent){
        this.apiKey = apiKey;
        this.okHttpClient = new OkHttpClient.Builder().callTimeout(Duration.of(15, ChronoUnit.SECONDS)).build();
        this.userAgent = userAgent != null ? userAgent : this.userAgent;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.ratelimit = new Ratelimit();
    }

    /**
     * Constructs a new JavaTicketMaster Instance with provided API-Key, User-Agent default("Ticketmaster JavaSDK").
     * @param apiKey API-Key for the Ticketmaster API
     */
    public JavaTicketMaster(String apiKey){
        this.apiKey = apiKey;
        this.okHttpClient = new OkHttpClient.Builder().callTimeout(Duration.of(15, ChronoUnit.SECONDS)).build();
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.ratelimit = new Ratelimit();
    }

}
