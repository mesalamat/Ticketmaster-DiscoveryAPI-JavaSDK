package de.godly.javaticketmaster.request.details;

import de.godly.javaticketmaster.JavaTicketMaster;
import de.godly.javaticketmaster.request.DiscoveryRequest;
import de.godly.javaticketmaster.response.details.GenreDetailsResponse;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class GenreDetailsRequest extends DiscoveryRequest {


    private Map<GenreDetailsRequestParameter, Object> parameters;


    public GenreDetailsRequest(JavaTicketMaster ticketMaster){
        super(ticketMaster);
        parameters = new HashMap<>();

    }

    /**
     * Sets the Parameter Genre ID to provided Value
     * @param genreId provided Value
     * @return this, ready to continue building or request
     */
    public GenreDetailsRequest withId(String genreId) {
        parameters.put(GenreDetailsRequestParameter.ID, genreId);
        return this;
    }

    /** Builds the request based on the Parameters and executes it
     *
     * @return the Deserialized ClassificationDetailsResponse Object
     * @throws IOException URL cannot be reached
     * @throws NullPointerException if the ID parameter == null
     */

    public GenreDetailsResponse request() throws IOException {
        if(parameters.get(GenreDetailsRequestParameter.ID) == null){
            throw new NullPointerException("GenreDetailsRequestParameter has no set ID. Please provide an ID by using the withId Method");
        }
        StringBuilder builder = new StringBuilder("https://app.ticketmaster.com/discovery/v2/classifications/genres/" + parameters.get(GenreDetailsRequestParameter.ID) + ".json"+ "?apikey=" +  getJavaTicketMaster().getApiKey());
        Request request = new Request.Builder().url(builder.toString()).addHeader("User-Agent", getJavaTicketMaster().getUserAgent()).build();
        Response response = getJavaTicketMaster().getOkHttpClient().newCall(request).execute();
        getJavaTicketMaster().getRatelimit().handle(response.headers());
        return getJavaTicketMaster().getGson().fromJson(response.body().string(), GenreDetailsResponse.class);
    }

    public enum GenreDetailsRequestParameter {
        ID("id", String.class);
        private String paramName;
        private Type type;

        GenreDetailsRequestParameter(String paramName, Type type) {
            this.paramName = paramName;
            this.type = type;
        }
    }


}
