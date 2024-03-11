package de.godly.javaticketmaster.request.details;

import de.godly.javaticketmaster.JavaTicketMaster;
import de.godly.javaticketmaster.request.DiscoveryRequest;
import de.godly.javaticketmaster.response.details.EventDetailsResponse;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class EventDetailsRequest extends DiscoveryRequest {


    private Map<EventDetailsRequestParameter, Object> parameters;

    /**
     * Create a new EventDetailsRequest.
     * @param ticketMaster Instance of JavaTicketMaster
     */
    public EventDetailsRequest(JavaTicketMaster ticketMaster){
        super(ticketMaster);
        parameters = new HashMap<>();

    }

    /**
     * Creates a new EventDetailsRequest, makes call to "withId" redundant.
     * @param ticketMaster Instance of JavaTicketMaster
     * @param eventId ID of the Event you want details about
     */
    public EventDetailsRequest(JavaTicketMaster ticketMaster, String eventId){
        this(ticketMaster);
        withId(eventId);
    }

    /**
     * Specifies the ID Parameter of the EventDetailsRequest
     * @param eventId EventID that you retrieved through other means
     * @return this Request Instance
     */
    public EventDetailsRequest withId(String eventId) {
        parameters.put(EventDetailsRequestParameter.ID, eventId);
        return this;
    }
    /**
     * Executes the request to the Ticketmaster Discovery API, parses the Response from HAL/JSON to a EventDetailsResponse Object
     * @return An EventDetailsResponse Object with Details about the Event with provided ID
     * @throws NullPointerException if the ID parameter is null. Use the 2-param Constructor or call "withId"
     * @throws IOException if the API can't be reached
     */
    public EventDetailsResponse request() throws IOException {
        if(parameters.get(EventDetailsRequestParameter.ID) == null){
            throw new NullPointerException("EventDetailsRequest has no set ID. Please provide an ID by using the withId Method");
        }
        Request request = new Request.Builder().url("https://app.ticketmaster.com/discovery/v2/events/" + parameters.get(EventDetailsRequestParameter.ID) + ".json?apikey=" + getJavaTicketMaster().getApiKey()).addHeader("User-Agent", getJavaTicketMaster().getUserAgent()).build();
        Response response = getJavaTicketMaster().getOkHttpClient().newCall(request).execute();
        getJavaTicketMaster().getRatelimit().handle(response.headers());
        return  getJavaTicketMaster().getGson().fromJson(response.body().string(), EventDetailsResponse.class);
    }

    public enum EventDetailsRequestParameter {


        ID("id", String.class);
        private String paramName;
        private Type type;

        EventDetailsRequestParameter(String paramName, Type type) {
            this.paramName = paramName;
            this.type = type;
        }
    }


}
