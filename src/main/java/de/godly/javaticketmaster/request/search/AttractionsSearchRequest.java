package de.godly.javaticketmaster.request.search;

import com.neovisionaries.i18n.CountryCode;
import de.godly.javaticketmaster.JavaTicketMaster;
import de.godly.javaticketmaster.objects.search.LimitSearchEnum;
import de.godly.javaticketmaster.objects.search.Source;
import de.godly.javaticketmaster.request.DiscoveryRequest;
import de.godly.javaticketmaster.response.search.AttractionsResponse;
import lombok.Getter;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class AttractionsSearchRequest extends DiscoveryRequest {

    @Getter
    private Map<AttractionRequestParameter, Object> parameters;


    public AttractionsSearchRequest(JavaTicketMaster javaTicketMaster){
        super(javaTicketMaster);
        parameters = new HashMap<>();
    }

    /**
     * Adds a Parameter of provided type with provided value
     * @param paramType Provided Type
     * @param value Provided Value
     * @throws IncompatibleAttractionParameter if Provided Type's required Value Type does not meet Value Type
     * e.g. INCLUDE_TBA requires LimitSearchEnum.class value type
     */

    public AttractionsSearchRequest addParameter(AttractionRequestParameter paramType, Object value) throws IncompatibleAttractionParameter {
        if(value.getClass() != paramType.type){
            throw new IncompatibleAttractionParameter(paramType, value);
        }
        parameters.put(paramType, value);
        return this;
    }

    /**
     *  Generates the OkHttp Request by appending all Parameters to the GET-Request including the API-Key.
     *  Also handles the Ratelimit & updates it through the Headers of the Response
     *  The raw serialized JSON Response looks like this @see https://developer.ticketmaster.com/products-and-docs/raw-view/
     *
     * @return the deserialized JSON Response as EventsResponse Object.
     * @throws IOException if URL cannot be found.
     */
    public AttractionsResponse request() throws IOException {
        StringBuilder builder = new StringBuilder("https://app.ticketmaster.com/discovery/v2/attractions.json?apikey=" + this.getJavaTicketMaster().getApiKey());

        for(AttractionRequestParameter parameter : parameters.keySet()){
            Object value = parameters.get(parameter);
            if(value instanceof String[] array){
                for(String s : array){
                    builder.append("&").append(parameter.paramName).append("=").append(s);
                }
            }else if(value instanceof AttractionRequestParameter.AttractionSortMode sortMode){
                builder.append("&").append(parameter.paramName).append("=").append(sortMode.sortName);
            }else if(value instanceof LimitSearchEnum limitSearchEnum){
                builder.append("&").append(parameter.paramName).append("=").append(limitSearchEnum.getParamId());
            }else if(value instanceof Source source){
                builder.append("&").append(parameter.paramName).append("=").append(source.getParamId());
            }else builder.append("&").append(parameter.paramName).append("=").append(parameters.get(parameter));
        }

        Request request = new Request.Builder().url(builder.toString()).addHeader("User-Agent", getJavaTicketMaster().getUserAgent()).build();
        Response response = getJavaTicketMaster().getOkHttpClient().newCall(request).execute();
        getJavaTicketMaster().getRatelimit().handle(response.headers());
        return getJavaTicketMaster().getGson().fromJson(response.body().string(), AttractionsResponse.class);
    }


    public enum AttractionRequestParameter{


        ID("id", String.class),
        KEY_WORD("keyword", String.class)
        ,SOURCE("source", Source.class)
        ,LOCALE("locale", String.class)
        , PAGE("page", Integer.class)
        ,INCLUDE_TEST("includeTest", LimitSearchEnum.class)
        ,SIZE("size", Integer.class)
        ,SORT("sort", AttractionSortMode.class)
        , CLASSIFICATION_NAME("classificationName", String.class)
        , CLASSIFICATION_ID("classificationId", String.class)
        , SEGMENT_ID("segmentId", String[].class)
        , SEGMENT_NAME("segmentName", String.class)
        , FAMILY_FRIENDLY("includeFamily", LimitSearchEnum.class)
        , PROMOTER_ID("promoterId", String.class)
        , GENRE_ID("genreId", String[].class)
        , SUB_GENRE_ID("subGenreId", String[].class)
        , TYPE_ID("typeId", String[].class)
        , SUB_TYPE_ID("subTypeId", String[].class)
        , PREFERRED_COUNTRY("preferredCountry", String.class)
        , INCLUDE_SPELL_CHECK("includeSpellCheck", String.class)
        , DOMAIN("domain", String[].class);


        private String paramName;
        private Type type;

        AttractionRequestParameter(String paramName, Type type){
            this.paramName = paramName;
            this.type = type;
        }

        private enum AttractionSortMode {

            BY_NAME_ASCENDING("name,asc")
            ,BY_NAME_DESCENDING("name,desc")
            ,BY_RELEVANCE_ASCENDING("relevance,asc")
            ,BY_RELEVANCE_DESCENDING("relevance,desc")
            ,RANDOM("random");
            private String sortName;
            AttractionSortMode(String sortName){
                this.sortName = sortName;
            }
        }


    }

    /**
     *
     *
     */
    private class IncompatibleAttractionParameter extends Exception{

        private AttractionRequestParameter type;
        private Object incompatibleValue;

        private IncompatibleAttractionParameter(AttractionRequestParameter type, Object faultyObject){
            this.type = type;
            this.incompatibleValue = faultyObject;
        }

        @Override
        public String getMessage() {
            return "Incompatible AttractionRequestParameter " + type.name() + " with " + this.incompatibleValue.getClass().getName() + "." + type.name() + " requires Value of type " + type.type.getTypeName();
        }
    }
}
