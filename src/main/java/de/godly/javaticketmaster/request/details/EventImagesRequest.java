package de.godly.javaticketmaster.request.details;

import de.godly.javaticketmaster.JavaTicketMaster;
import de.godly.javaticketmaster.request.DiscoveryRequest;
import de.godly.javaticketmaster.response.details.EventImageResponse;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class EventImagesRequest extends DiscoveryRequest {


    private Map<EventImageRequestParameter, Object> parameters;

    /**
     * Create a new EventImagesRequest.
     * @param javaTicketMaster Instance of JavaTicketMaster
     */
    public EventImagesRequest(JavaTicketMaster javaTicketMaster){
        super(javaTicketMaster);
        parameters = new HashMap<>();
    }

    /**
     * Creates a new EventImagesRequest, makes call to "withId" redundant.
     * @param ticketMaster Instance of JavaTicketMaster
     * @param eventId ID of the Event you want Images of
     */
    public EventImagesRequest(JavaTicketMaster ticketMaster, String eventId){
        this(ticketMaster);
        withId(eventId);
    }

    /**
     * Specifies the ID Parameter of the EventImagesRequest
     * @param eventId EventID that you retrieved through other means
     * @return this Request Instance
     */
    public EventImagesRequest withId(String eventId) {
        parameters.put(EventImageRequestParameter.ID, eventId);
        return this;
    }

    /**
     * Executes the request to the Ticketmaster Discovery API, parses the Response from HAL/JSON to a EventDetailsResponse Object
     * @return An EventImageResponse Object with Details about the Event with provided ID
     * @throws NullPointerException if the ID parameter is null. Use the 2-param Constructor or call "withId"
     * @throws IOException if the API can't be reached
     */
    public EventImageResponse request() throws IOException {
        if(parameters.get(EventImagesRequest.EventImageRequestParameter.ID) == null){
            throw new NullPointerException("EventImagesRequest has no set ID. Please provide an ID by using the withId Method");
        }
        Request request = new Request.Builder().url("https://app.ticketmaster.com/discovery/v2/events/" + parameters.get(EventImageRequestParameter.ID) + "/images.json?apikey=" + getJavaTicketMaster().getApiKey()).addHeader("User-Agent", getJavaTicketMaster().getUserAgent()).build();
        Response response =  getJavaTicketMaster().getOkHttpClient().newCall(request).execute();
        getJavaTicketMaster().getRatelimit().handle(response.headers());
        return  getJavaTicketMaster().getGson().fromJson(response.body().string(), EventImageResponse.class);
    }

    public enum EventImageRequestParameter {


        ID("id", String.class);
        private String paramName;
        private Type type;

        EventImageRequestParameter(String paramName, Type type) {
            this.paramName = paramName;
            this.type = type;
        }
    }


}
