package de.godly.javaticketmaster;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
    public JavaTicketMaster(String apiKey, @Nullable String userAgent){
        this.apiKey = apiKey;
        this.okHttpClient = new OkHttpClient.Builder().callTimeout(Duration.of(15, ChronoUnit.SECONDS)).build();
        this.userAgent = userAgent != null ? userAgent : this.userAgent;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.ratelimit = new Ratelimit();
    }

    @SneakyThrows
    public static void main(String[] args) {
       JavaTicketMaster javaTicketMaster=  new JavaTicketMaster("TPkmuIgjK33iLLGBAowSYsXocPvYneHH", null);
        EventsResponse response = new EventsSearchRequest(javaTicketMaster).addParameter(EventsSearchRequest.EventRequestParameter.CITY, new String[]{"Stuttgart"}).
        addParameter(EventsSearchRequest.EventRequestParameter.SIZE, (int)3).addParameter(EventsSearchRequest.EventRequestParameter.COUNTRY_CODE, "de").request();
        System.out.println(response.getEventsWrapper().getEvents().get(1).getName());
        System.out.println(response.getEventsWrapper().getEvents().get(1).getUrl());
        System.out.println(response.getEventsWrapper().getEvents().get(1).getEventAttractionsVenues().getAttractions().get(0).getClassifications().get(0).getGenre().getName());
        System.out.println(response.getEventsWrapper().getEvents().get(1).getDates().getStart().getLocalDate());
        System.out.println(response.getEventsWrapper().getEvents().get(1).getDates().getStart().getDate().toString());

        AttractionsResponse attractionsResponse = new AttractionsSearchRequest(javaTicketMaster).addParameter(AttractionsSearchRequest.AttractionRequestParameter.PREFERRED_COUNTRY, "de").addParameter(AttractionsSearchRequest.AttractionRequestParameter.SIZE, 2).addParameter(AttractionsSearchRequest.AttractionRequestParameter.SEGMENT_NAME, "Music").request();
        System.out.println(attractionsResponse.getAttractionsWrapper().getAttractions().get(0).getName());
        System.out.println(attractionsResponse.getAttractionsWrapper().getAttractions().get(0).getClassifications().get(0).getGenre().getName());

        EventDetailsResponse eventImageResponse = new EventDetailsRequest(javaTicketMaster).withId(response.getEventsWrapper().getEvents().get(1).getId()).request();
        for(Attraction.AttractionClassification m : eventImageResponse.getClassifications()){
            System.out.println(m.getGenre().getName());
        }

        ClassificationsResponse classificationsResponse = new ClassificationsRequest(javaTicketMaster).addParameter(ClassificationsRequest.ClassificationRequestParameter.SIZE, 1).addParameter(ClassificationsRequest.ClassificationRequestParameter.PREFERRED_COUNTRY, "de").request();

        System.out.println(classificationsResponse.getClassificationsWrapper().getClassifications().get(0).getSegment().getGenreContainer().getGenres().get(0).getName());
        ClassificationDetailsResponse classificationDetailsResponse = new ClassificationDetailsRequest(javaTicketMaster).withId(classificationsResponse.getClassificationsWrapper().getClassifications().get(0).getSegment().getId()).request();
        System.out.println(classificationDetailsResponse.getSegment().getName());

        GenreDetailsResponse genreDetailsResponse = new GenreDetailsRequest(javaTicketMaster).withId(classificationsResponse.getClassificationsWrapper().getClassifications().get(0).getSegment().getGenreContainer().getGenres().get(0).getId()).request();
        System.out.println(genreDetailsResponse.getName());
        SegmentDetailsResponse segmentDetailsResponse = new SegmentDetailsRequest(javaTicketMaster).withId(classificationsResponse.getClassificationsWrapper().getClassifications().get(0).getSegment().getId()).request();
        System.out.println(segmentDetailsResponse.getName());
        SubgenreDetailsResponse subgenreDetailsRequest = new SubgenreDetailsRequest(javaTicketMaster).withId(genreDetailsResponse.getSubgenreContainer().getSubgenres().get(0).getId()).request();
        System.out.println(subgenreDetailsRequest.getName());

        VenuesResponse venuesResponse = new VenueSearchRequest(javaTicketMaster).addParameter(VenueSearchRequest.VenueRequestParameter.SIZE, 1).addParameter(VenueSearchRequest.VenueRequestParameter.COUNTRY_CODE, "de").request();
        System.out.println(venuesResponse.getVenuesWrapper().getVenues().get(0).getName());
        System.out.println(venuesResponse.getVenuesWrapper().getVenues().get(0).getUrl());
        VenueDetailsResponse venueDetailsResponse = new VenueDetailsRequest(javaTicketMaster).withId(venuesResponse.getVenuesWrapper().getVenues().get(0).getId()).request();
        System.out.println(venueDetailsResponse.getCity().getName());
    }

}
