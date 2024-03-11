package de.godly.javaticketmaster.request.details;

import de.godly.javaticketmaster.JavaTicketMaster;
import de.godly.javaticketmaster.request.DiscoveryRequest;
import de.godly.javaticketmaster.response.details.SegmentDetailsResponse;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class SegmentDetailsRequest extends DiscoveryRequest {


    private Map<SegmentDetailsRequestParameter, Object> parameters;


    public SegmentDetailsRequest(JavaTicketMaster ticketMaster){
        super(ticketMaster);
        parameters = new HashMap<>();

    }

    /**
     * Creates a new SegmentDetailsRequest, makes call to "withId" redundant.
     * @param ticketMaster Instance of JavaTicketMaster
     * @param segmentId ID of the Segment you want details about. This is not the Segment Name.
     */
    public SegmentDetailsRequest(JavaTicketMaster ticketMaster, String segmentId){
        this(ticketMaster);
        withId(segmentId);
    }

    /**
     * Sets the Parameter Genre ID to provided Value
     * @param genreId provided Value
     * @return this, ready to continue building or request
     */
    public SegmentDetailsRequest withId(String genreId) {
        parameters.put(SegmentDetailsRequestParameter.ID, genreId);
        return this;
    }

    /** Builds the request based on the Parameters and executes it
     *
     * @return the Deserialized ClassificationDetailsResponse Object
     * @throws IOException URL cannot be reached
     * @throws NullPointerException if the ID parameter == null
     */

    public SegmentDetailsResponse request() throws IOException {
        if(parameters.get(SegmentDetailsRequestParameter.ID) == null){
            throw new NullPointerException("SegmentDetailsRequestParameter has no set ID. Please provide an ID by using the withId Method");
        }
        Request request = new Request.Builder().url("https://app.ticketmaster.com/discovery/v2/classifications/segments/" + parameters.get(SegmentDetailsRequestParameter.ID) + ".json" + "?apikey=" + getJavaTicketMaster().getApiKey()).addHeader("User-Agent", getJavaTicketMaster().getUserAgent()).build();
        Response response = getJavaTicketMaster().getOkHttpClient().newCall(request).execute();
        getJavaTicketMaster().getRatelimit().handle(response.headers());
        return getJavaTicketMaster().getGson().fromJson(response.body().string(), SegmentDetailsResponse.class);
    }

    public enum SegmentDetailsRequestParameter {
        ID("id", String.class);
        private String paramName;
        private Type type;

        SegmentDetailsRequestParameter(String paramName, Type type) {
            this.paramName = paramName;
            this.type = type;
        }
    }


}
