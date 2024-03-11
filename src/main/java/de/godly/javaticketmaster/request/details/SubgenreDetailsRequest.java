package de.godly.javaticketmaster.request.details;

import de.godly.javaticketmaster.JavaTicketMaster;
import de.godly.javaticketmaster.request.DiscoveryRequest;
import de.godly.javaticketmaster.response.details.SubgenreDetailsResponse;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class SubgenreDetailsRequest extends DiscoveryRequest {


    private Map<SubgenreDetailsRequestParameter, Object> parameters;

    /**
     * Creates a new SubgenreDetailsRequest.
     * @param ticketMaster Instance of JavaTicketMaster
     */
    public SubgenreDetailsRequest(JavaTicketMaster ticketMaster){
        super(ticketMaster);
        parameters = new HashMap<>();

    }

    /**
     * Creates a new SubgenreDetailsRequest, makes call to "withId" redundant.
     * @param ticketMaster Instance of JavaTicketMaster
     * @param subgenreId ID of the Subgenre you want details about. This is not the Segment Name.
     */
    public SubgenreDetailsRequest(JavaTicketMaster ticketMaster, String subgenreId){
        this(ticketMaster);
        withId(subgenreId);
    }

    /**
     * Sets the Parameter Genre ID to provided Value
     * @param genreId provided Value
     * @return this Object, ready to continue building or request
     */
    public SubgenreDetailsRequest withId(String genreId) {
        parameters.put(SubgenreDetailsRequestParameter.ID, genreId);
        return this;
    }

    /** Builds the request based on the Parameters and executes it
     *
     * @return the Deserialized SubgenreDetailsResponse Object
     * @throws IOException URL cannot be reached
     * @throws NullPointerException if the ID parameter == null
     */
    public SubgenreDetailsResponse request() throws IOException {
        if(parameters.get(SubgenreDetailsRequestParameter.ID) == null){
            throw new NullPointerException("SegmentDetailsRequestParameter has no set ID. Please provide an ID by using the withId Method");
        }
        Request request = new Request.Builder().url("https://app.ticketmaster.com/discovery/v2/classifications/subgenres/" + parameters.get(SubgenreDetailsRequestParameter.ID) + ".json" + "?apikey=" + getJavaTicketMaster().getApiKey()).addHeader("User-Agent", getJavaTicketMaster().getUserAgent()).build();
        Response response = getJavaTicketMaster().getOkHttpClient().newCall(request).execute();
        getJavaTicketMaster().getRatelimit().handle(response.headers());
        return getJavaTicketMaster().getGson().fromJson(response.body().string(), SubgenreDetailsResponse.class);
    }

    public enum SubgenreDetailsRequestParameter {
        ID("id", String.class);
        private String paramName;
        private Type type;

        SubgenreDetailsRequestParameter(String paramName, Type type) {
            this.paramName = paramName;
            this.type = type;
        }
    }


}
