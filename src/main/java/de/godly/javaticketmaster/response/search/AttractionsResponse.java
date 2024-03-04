package de.godly.javaticketmaster.response.search;

import com.google.gson.annotations.SerializedName;
import de.godly.javaticketmaster.objects.Attraction;
import de.godly.javaticketmaster.response.PagedResponse;
import lombok.Data;

import java.util.List;


@Data
public class AttractionsResponse extends PagedResponse {

    @SerializedName("_embedded")
    private final Attractions attractionsWrapper;

    @Data
    public class Attractions{

        private final List<Attraction> attractions;

    }

}
