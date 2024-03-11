package de.godly.javaticketmaster.request.search;

import com.neovisionaries.i18n.CountryCode;
import de.godly.javaticketmaster.JavaTicketMaster;
import de.godly.javaticketmaster.objects.search.LimitSearchEnum;
import de.godly.javaticketmaster.objects.search.Source;
import de.godly.javaticketmaster.request.DiscoveryRequest;
import de.godly.javaticketmaster.response.search.EventsResponse;
import lombok.Getter;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class EventsSearchRequest extends DiscoveryRequest {

    @Getter
    private Map<EventRequestParameter, Object> parameters;


    public EventsSearchRequest(JavaTicketMaster javaTicketMaster) {
        super(javaTicketMaster);
        parameters = new HashMap<>();
    }

    /**
     * Adds a Parameter of provided type with provided value
     *
     * @param paramType Provided Type. Note: Please use only compatible types, e.g. LimitSearchEnum for "includeTBA"
     * @param value     Provided Value
     * @throws IncompatibleEventParameter if Provided Type's required Value Type does not meet Value Type
     *                                    e.g. INCLUDE_TBA requires LimitSearchEnum.class value type
     */
    public EventsSearchRequest addParameter(EventRequestParameter paramType, Object value) throws IncompatibleEventParameter {
        if (value.getClass() != paramType.type) {
            throw new IncompatibleEventParameter(paramType, value);
        }
        parameters.put(paramType, value);
        return this;
    }

    /**
     * Generates the OkHttp Request by appending all Parameters to the GET-Request including the API-Key.
     * Also handles the Ratelimit & updates it through the Headers of the Response
     * The raw serialized JSON Response looks like this @see https://developer.ticketmaster.com/products-and-docs/raw-view/
     *
     * @return the deserialized JSON Response as EventsResponse Object.
     * @throws IOException if URL cannot be found.
     */
    public EventsResponse request() throws IOException {
        String requestString = getRequestString();
        Request request = new Request.Builder().url(requestString).addHeader("User-Agent", getJavaTicketMaster().getUserAgent()).build();
        Response response = getJavaTicketMaster().getOkHttpClient().newCall(request).execute();
        getJavaTicketMaster().getRatelimit().handle(response.headers());
        return getJavaTicketMaster().getGson().fromJson(response.body().string(), EventsResponse.class);
    }

    @NotNull
    private String getRequestString() {
        StringBuilder builder = new StringBuilder("https://app.ticketmaster.com/discovery/v2/events.json?apikey=" + getJavaTicketMaster().getApiKey());
        for (EventRequestParameter parameter : parameters.keySet()) {
            Object value = parameters.get(parameter);
            if (value instanceof String[] array) {
                for (String s : array) {
                    builder.append("&").append(parameter.paramName).append("=").append(s);
                }
            } else if (value instanceof EventRequestParameter.SortMode sortMode) {
                builder.append("&").append(parameter.paramName).append("=").append(sortMode.sortName);
            } else if (value instanceof LimitSearchEnum limitSearchEnum) {
                builder.append("&").append(parameter.paramName).append("=").append(limitSearchEnum.getParamId());
            } else if (value instanceof Source source) {
                builder.append("&").append(parameter.paramName).append("=").append(source.getParamId());
            } else builder.append("&").append(parameter.paramName).append("=").append(parameters.get(parameter));
        }
        return builder.toString();
    }


    public enum EventRequestParameter {


        ID("id", String.class),
        KEY_WORD("keyword", String.class),
        ATTRACTION_ID("attractionId", String.class), VENUE_ID("venueId", String.class), POSTALCODE("postalCode", String.class),
        /**
         * Filter by Location
         *
         * @deprecated This method is marked as deprecated in the Discovery API Docs and might even be removed
         */
        @Deprecated LATLONG("latlong", String.class), RADIUS("radius", int.class), UNIT("unit", String.class), SOURCE("source", Source.class), LOCALE("locale", String.class), MARKET_ID("marketId", String.class), START_DATE_TIME("startDateTime", String.class), END_DATE_TIME("endDateTime", String.class), INCLUDE_TBA("includeTBA", LimitSearchEnum.class), INCLUDE_TBD("includeTBD", LimitSearchEnum.class), INCLUDE_TEST("includeTest", LimitSearchEnum.class), SIZE("size", Integer.class), SORT("sort", SortMode.class), ON_SALE_START_DATE_TIME("onsaleStartDateTime", String.class), ON_SALE_END_DATE_TIME("onsaleEndDateTime", String.class), CITY("city", String[].class), COUNTRY_CODE("countryCode", String.class), STATE_CODE("stateCode", String.class), CLASSIFICATION_NAME("classificationName", String[].class), CLASSIFICATION_ID("classificationId", String[].class), DMA_ID("dmaId", String.class), LOCAL_START_DATE_TIME("localStartDateTime", String[].class), LOCAL_START_END_DATE_TIME("localStartEndDateTime", String[].class), LOCAL_END_DATE_TIME("localEndDateTime", String[].class), PUBLIC_VISIBILITY_START_DATE_TIME("publicVisibilityStartDateTime", String[].class), PRE_SALE_DATE_TIME("preSaleDateTime", String[].class), ON_SALE_ON_START_DATE("onsaleOnStartDate", String.class), ON_SALE_ON_AFTER_START_DATE("onsaleOnAfterStartDate", String.class), COLLECTION_ID("collectionId", String[].class), SEGMENT_ID("segmentId", String[].class), SEGMENT_NAME("segmentName", String.class), FAMILY_FRIENDLY("includeFamily", LimitSearchEnum.class), PROMOTER_ID("promoterId", String.class), GENRE_ID("genreId", String[].class), SUB_GENRE_ID("subGenreId", String[].class), TYPE_ID("typeId", String[].class), SUB_TYPE_ID("subTypeId", String[].class), GEO_POINT("geoPoint", String.class), PREFERRED_COUNTRY("preferredCountry", CountryCode.class), INCLUDE_SPELL_CHECK("includeSpellCheck", String.class), DOMAIN("domain", String[].class);


        private String paramName;
        private Type type;

        EventRequestParameter(String paramName, Type type) {
            this.paramName = paramName;
            this.type = type;
        }

        public enum SortMode {

            BY_NAME_ASCENDING("name,asc"), BY_NAME_DESCENDING("name,desc"), BY_DATE_ASCENDING("date,asc"), BY_DATE_DESCENDING("date,desc"), BY_RELEVANCE_ASCENDING("relevance,asc"), BY_RELEVANCE_DESCENDING("relevance,desc"), BY_DISTANCE_ASCENDING("distance,asc"), BY_NAME_DATE_ASCENDING("name,date,asc"), BY_NAME_DATE_DESCENDING("name,date,desc"), BY_DATE_NAME_ASCENDING("date,name,asc"), BY_DATE_NAME_DESCENDING("date,name,desc"), BY_DISTANCE_DATE_ASCENDING("distance,date,asc"), BY_ON_SALE_START_DATE_ASCENDING("onSaleStartDate,asc"), BY_ID_ASCENDING("id,asc"), BY_VENUE_NAME_ASCENDING("venueName,asc"), BY_VENUE_NAME_DESCENDING("venueName,desc"), RANDOM("random");
            private String sortName;

            SortMode(String sortName) {
                this.sortName = sortName;
            }
        }


    }

    /**
     *
     */
    private class IncompatibleEventParameter extends Exception {

        private EventRequestParameter type;
        private Object incompatibleValue;

        private IncompatibleEventParameter(EventRequestParameter type, Object faultyObject) {
            this.type = type;
            this.incompatibleValue = faultyObject;
        }

        @Override
        public String getMessage() {
            return "Incompatible EventRequestParameter " + type.name() + " with " + this.incompatibleValue.getClass().getName() + "." + type.name() + " requires Value of type " + type.type.getTypeName();
        }
    }
}
