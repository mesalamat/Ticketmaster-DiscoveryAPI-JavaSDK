package de.godly.javaticketmaster.request.details;

import de.godly.javaticketmaster.JavaTicketMaster;
import de.godly.javaticketmaster.request.DiscoveryRequest;
import de.godly.javaticketmaster.response.details.VenueDetailsResponse;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class VenueDetailsRequest extends DiscoveryRequest {


    private Map<VenueDetailsRequestParameter, Object> parameters;

    /**
     * Create a new VenueDetailsRequest.
     * @param ticketMaster Instance of JavaTicketMaster
     */
    public VenueDetailsRequest(JavaTicketMaster ticketMaster){
        super(ticketMaster);
        parameters = new HashMap<>();

    }

    /**
     * Create a new VenueDetailsRequest.
     * @param ticketMaster Instance of JavaTicketMaster
     * @param venueId ID of the Venue you want Details about. ID is not the name.
     */
    public VenueDetailsRequest(JavaTicketMaster ticketMaster, String venueId){
        this(ticketMaster);
        withId(venueId);

    }

    public VenueDetailsRequest withId(String venueId) {
        parameters.put(VenueDetailsRequestParameter.ID, venueId);
        return this;
    }
    /** Builds the request based on the Parameters and executes it
     *
     * @return the Deserialized SubgenreDetailsResponse Object
     * @throws IOException URL cannot be reached
     * @throws NullPointerException if the ID parameter == null
     */
    public VenueDetailsResponse request() throws IOException {
        if(parameters.get(VenueDetailsRequestParameter.ID) == null){
            throw new NullPointerException("VenueDetailsRequest has no set ID. Please provide an ID by using the withId Method");
        }
        Request request = new Request.Builder().url("https://app.ticketmaster.com/discovery/v2/venues/" + parameters.get(VenueDetailsRequestParameter.ID) + ".json?apikey=" + getJavaTicketMaster().getApiKey()).addHeader("User-Agent", getJavaTicketMaster().getUserAgent()).build();
        Response response = getJavaTicketMaster().getOkHttpClient().newCall(request).execute();
        getJavaTicketMaster().getRatelimit().handle(response.headers());
        return  getJavaTicketMaster().getGson().fromJson(response.body().string(), VenueDetailsResponse.class);
    }

    public enum VenueDetailsRequestParameter {


        ID("id", String.class);
        private String paramName;
        private Type type;

        VenueDetailsRequestParameter(String paramName, Type type) {
            this.paramName = paramName;
            this.type = type;
        }
    }


}
