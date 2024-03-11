package example;

import com.neovisionaries.i18n.CountryCode;
import de.godly.javaticketmaster.JavaTicketMaster;
import de.godly.javaticketmaster.objects.Attraction;
import de.godly.javaticketmaster.objects.Event;
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
import lombok.SneakyThrows;

public class ExampleMain {


    /**
     * Retrieves 3 Metal & Hard Rock Events by descending Alphabetical Order (Z-A) taking place in the United States in Denver & prints out their Name & URL
     * @param args
     */
    @SneakyThrows
    public static void main(String[] args) {
        JavaTicketMaster javaTicketMaster=  new JavaTicketMaster("YourApiKey" /*Optionally you can add a User-Agent here too with the other Constructor**/);
        EventsResponse response = new EventsSearchRequest(javaTicketMaster).addParameter(EventsSearchRequest.EventRequestParameter.SEGMENT_NAME, "Music").
                addParameter(EventsSearchRequest.EventRequestParameter.CLASSIFICATION_NAME,
                /*
                You can add multiple Genres/Segments/Classifications by adding them to the String Array
                */ new String[]{"Metal","Hard Rock"}).
                addParameter(EventsSearchRequest.EventRequestParameter.SORT, EventsSearchRequest.EventRequestParameter.SortMode.BY_NAME_DESCENDING).
                addParameter(EventsSearchRequest.EventRequestParameter.CITY,
                /*
                 You can add multiple Cities by adding them to the String Array
                */
                new String[]{"Denver"}).
                addParameter(EventsSearchRequest.EventRequestParameter.SIZE, (int)3).addParameter(EventsSearchRequest.EventRequestParameter.COUNTRY_CODE, CountryCode.US.getAlpha2()).
                request();
        for(Event event : response.getEventsWrapper().getEvents()){
            System.out.println(event.getName());
            System.out.println(event.getUrl());
        }

    }
}
