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


    public EventDetailsRequest(JavaTicketMaster ticketMaster){
        super(ticketMaster);
        parameters = new HashMap<>();

    }

    public EventDetailsRequest withId(String eventId) {
        parameters.put(EventDetailsRequestParameter.ID, eventId);
        return this;
    }

    public EventDetailsResponse request() throws IOException {
        if(parameters.get(EventDetailsRequestParameter.ID) == null){
            throw new NullPointerException("EventDetailsRequest has no set ID. Please provide an ID by using the withId Method");
        }
        StringBuilder builder = new StringBuilder("https://app.ticketmaster.com/discovery/v2/events/" + parameters.get(EventDetailsRequestParameter.ID) + ".json?apikey=" +  getJavaTicketMaster().getApiKey());
        Request request = new Request.Builder().url(builder.toString()).addHeader("User-Agent", getJavaTicketMaster().getUserAgent()).build();
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
