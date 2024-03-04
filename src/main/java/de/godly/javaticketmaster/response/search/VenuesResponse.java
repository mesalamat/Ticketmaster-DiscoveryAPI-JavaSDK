package de.godly.javaticketmaster.response.search;

import com.google.gson.annotations.SerializedName;
import de.godly.javaticketmaster.objects.Attraction;
import de.godly.javaticketmaster.objects.Venue;
import de.godly.javaticketmaster.response.PagedResponse;
import lombok.Data;

import java.util.List;


@Data
public class VenuesResponse extends PagedResponse {

    @SerializedName("_embedded")
    private final Venues venuesWrapper;

    @Data
    public class Venues{

        private final List<Venue> venues;

    }

}
