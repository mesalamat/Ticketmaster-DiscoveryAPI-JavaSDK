package de.godly.javaticketmaster.request.details;

import de.godly.javaticketmaster.JavaTicketMaster;
import de.godly.javaticketmaster.request.DiscoveryRequest;
import de.godly.javaticketmaster.response.details.ClassificationDetailsResponse;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ClassificationDetailsRequest extends DiscoveryRequest {


    private Map<ClassificationDetailsRequestParameter, Object> parameters;


    public ClassificationDetailsRequest(JavaTicketMaster ticketMaster){
        super(ticketMaster);
        parameters = new HashMap<>();

    }

    public ClassificationDetailsRequest withId(String eventId) {
        parameters.put(ClassificationDetailsRequestParameter.ID, eventId);
        return this;
    }

    public ClassificationDetailsResponse request() throws IOException {
        if(parameters.get(ClassificationDetailsRequestParameter.ID) == null){
            throw new NullPointerException("ClassificationDetailsRequest has no set ID. Please provide an ID by using the withId Method");
        }
        StringBuilder builder = new StringBuilder("https://app.ticketmaster.com/discovery/v2/classifications/" + parameters.get(ClassificationDetailsRequestParameter.ID) + "?apikey=" +  getJavaTicketMaster().getApiKey());
        Request request = new Request.Builder().url(builder.toString()).addHeader("User-Agent", getJavaTicketMaster().getUserAgent()).build();
        Response response = getJavaTicketMaster().getOkHttpClient().newCall(request).execute();
        getJavaTicketMaster().getRatelimit().handle(response.headers());
        return  getJavaTicketMaster().getGson().fromJson(response.body().string(), ClassificationDetailsResponse.class);
    }

    public enum ClassificationDetailsRequestParameter {


        ID("id", String.class);
        private String paramName;
        private Type type;

        ClassificationDetailsRequestParameter(String paramName, Type type) {
            this.paramName = paramName;
            this.type = type;
        }
    }


}
