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


    /**
     * Create a new ClassificationDetailsRequest.
     * @param ticketMaster Instance of JavaTicketMaster
     */
    public ClassificationDetailsRequest(JavaTicketMaster ticketMaster){
        super(ticketMaster);
        parameters = new HashMap<>();
    }

    /**
     * Creates a new ClassificationDetailsRequest, makes call to "withId" redundant.
     * @param ticketMaster Instance of JavaTicketMaster
     * @param classificationId ID of the Classification you want details about
     */
    public ClassificationDetailsRequest(JavaTicketMaster ticketMaster, String classificationId){
        this(ticketMaster);
        withId(classificationId);
    }
    /**
     * Specifies the ID Parameter of the ClassificationDetailsRequest
     * @param classificationId ClassificationID that you retrieved through other means
     * @return this Request Instance
     */
    public ClassificationDetailsRequest withId(String classificationId) {
        parameters.put(ClassificationDetailsRequestParameter.ID, classificationId);
        return this;
    }

    /**
     * Executes the request to the Ticketmaster Discovery API, parses the Response from HAL/JSON to a ClassificationDetailsResponse Object
     * @return A ClassificationDetailsResponse Object with Details about the Classification with provided ID
     * @throws NullPointerException if the ID parameter is null. Use the 2-param Constructor or call "withId"
     * @throws IOException if the API can't be reached
     */
    public ClassificationDetailsResponse request() throws IOException {
        if(parameters.get(ClassificationDetailsRequestParameter.ID) == null){
            throw new NullPointerException("ClassificationDetailsRequest has no set ID. Please provide an ID by using the withId Method");
        }
        StringBuilder builder = new StringBuilder("https://app.ticketmaster.com/discovery/v2/classifications/" + parameters.get(ClassificationDetailsRequestParameter.ID) + "?apikey=" +  getJavaTicketMaster().getApiKey());
        Request request = new Request.Builder().url(builder.toString()).addHeader("User-Agent", getJavaTicketMaster().getUserAgent()).build();
        Response response = getJavaTicketMaster().getOkHttpClient().newCall(request).execute();
        getJavaTicketMaster().getRatelimit().handle(response.headers());
        return getJavaTicketMaster().getGson().fromJson(response.body().string(), ClassificationDetailsResponse.class);
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
