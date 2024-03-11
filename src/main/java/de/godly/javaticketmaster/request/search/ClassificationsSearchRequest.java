package de.godly.javaticketmaster.request.search;

import de.godly.javaticketmaster.JavaTicketMaster;
import de.godly.javaticketmaster.objects.search.LimitSearchEnum;
import de.godly.javaticketmaster.objects.search.Source;
import de.godly.javaticketmaster.request.DiscoveryRequest;
import de.godly.javaticketmaster.response.search.ClassificationsResponse;
import lombok.Getter;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ClassificationsSearchRequest extends DiscoveryRequest {
    @Getter
    private Map<ClassificationRequestParameter, Object> parameters;


    public ClassificationsSearchRequest(JavaTicketMaster javaTicketMaster){
        super(javaTicketMaster);
        parameters = new HashMap<>();
    }

    /**
     * Adds a Parameter of provided type with provided value
     * @param paramType Provided Type
     * @param value Provided Value
     * @throws IncompatibleClassificationParameter if Provided Type's required Value Type does not meet Value Type
     * e.g. INCLUDE_TBA requires LimitSearchEnum.class value type
     */

    public ClassificationsSearchRequest addParameter(ClassificationRequestParameter paramType, Object value) throws IncompatibleClassificationParameter {
        if(value.getClass() != paramType.type){
            throw new IncompatibleClassificationParameter(paramType, value);
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
    public ClassificationsResponse request() throws IOException {
        String requestString = getRequestString();
        Request request = new Request.Builder().url(requestString).addHeader("User-Agent", getJavaTicketMaster().getUserAgent()).build();
        Response response = getJavaTicketMaster().getOkHttpClient().newCall(request).execute();
       // System.out.println(response.body().string());
        getJavaTicketMaster().getRatelimit().handle(response.headers());
       // FileWriter fileWriter = new FileWriter("response.json");
        //fileWriter.write(response.body().string());
        //fileWriter.close();
        return getJavaTicketMaster().getGson().fromJson(response.body().string(), ClassificationsResponse.class);
    }

    @NotNull
    private String getRequestString() {
        StringBuilder builder = new StringBuilder("https://app.ticketmaster.com/discovery/v2/classifications.json?apikey=" + this.getJavaTicketMaster().getApiKey());
        for(ClassificationRequestParameter parameter : parameters.keySet()){
            Object value = parameters.get(parameter);
            if(value instanceof String[] array){
                for(String s : array){
                    builder.append("&").append(parameter.paramName).append("=").append(s);
                }
            }else if(value instanceof ClassificationRequestParameter.ClassificationSortMode sortMode){
                builder.append("&").append(parameter.paramName).append("=").append(sortMode.sortName);
            }else if(value instanceof LimitSearchEnum limitSearchEnum){
                builder.append("&").append(parameter.paramName).append("=").append(limitSearchEnum.getParamId());
            }else if(value instanceof Source source){
                builder.append("&").append(parameter.paramName).append("=").append(source.getParamId());
            }else builder.append("&").append(parameter.paramName).append("=").append(parameters.get(parameter));
        }
        return builder.toString();
    }


    public enum ClassificationRequestParameter {


        ID("id", String.class),
        KEY_WORD("keyword", String.class)
        ,SOURCE("source", Source.class)
        ,LOCALE("locale", String.class)
        , PAGE("page", Integer.class)
        ,INCLUDE_TEST("includeTest", LimitSearchEnum.class)
        ,SIZE("size", Integer.class)
        ,SORT("sort", ClassificationSortMode.class)
        , PREFERRED_COUNTRY("preferredCountry", String.class)
        , INCLUDE_SPELL_CHECK("includeSpellCheck", String.class)
        , DOMAIN("domain", String[].class);


        private String paramName;
        private Type type;

        ClassificationRequestParameter(String paramName, Type type){
            this.paramName = paramName;
            this.type = type;
        }

        private enum ClassificationSortMode {

            BY_NAME_ASCENDING("name,asc");
            private String sortName;
            ClassificationSortMode(String sortName){
                this.sortName = sortName;
            }
        }


    }

    /**
     *
     *
     */
    private class IncompatibleClassificationParameter extends Exception{

        private ClassificationRequestParameter type;
        private Object incompatibleValue;

        private IncompatibleClassificationParameter(ClassificationRequestParameter type, Object faultyObject){
            this.type = type;
            this.incompatibleValue = faultyObject;
        }

        @Override
        public String getMessage() {
            return "Incompatible ClassificationRequestParameter " + type.name() + " with " + this.incompatibleValue.getClass().getName() + "." + type.name() + " requires Value of type " + type.type.getTypeName();
        }
    }
}
