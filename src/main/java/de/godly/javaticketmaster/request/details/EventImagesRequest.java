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


    public EventImagesRequest(JavaTicketMaster javaTicketMaster){
        super(javaTicketMaster);
        parameters = new HashMap<>();
    }

    public EventImagesRequest withId(String eventId) {
        parameters.put(EventImageRequestParameter.ID, eventId);
        return this;
    }

    public EventImageResponse request() throws IOException {
        StringBuilder builder = new StringBuilder("https://app.ticketmaster.com/discovery/v2/events/" + parameters.get(EventImageRequestParameter.ID) + "/images.json?apikey=" +  getJavaTicketMaster().getApiKey());
        Request request = new Request.Builder().url(builder.toString()).addHeader("User-Agent", getJavaTicketMaster().getUserAgent()).build();
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
