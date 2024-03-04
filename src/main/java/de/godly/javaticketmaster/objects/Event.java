package de.godly.javaticketmaster.objects;

import com.google.gson.annotations.SerializedName;
import de.godly.javaticketmaster.objects.embedded.Dates;
import de.godly.javaticketmaster.objects.embedded.Image;
import de.godly.javaticketmaster.objects.embedded.Links;
import de.godly.javaticketmaster.objects.embedded.Sales;
import lombok.Data;

import java.util.List;

@Data
public class Event {

    private final String id;
    private final String name;
    private final String url;
    private final String locale;

    private final List<Image> images;
    @SerializedName("_embedded")
    private final VenueAttractionContainer eventAttractionsVenues;
    @SerializedName("_links")
    private final EventLinks links;
    private final List<Attraction.AttractionClassification> classifications;
    private final Promoter promoter;
    private final Dates dates;
    private final Sales sales;





    @Data
    public class VenueAttractionContainer{

        private final List<Venue> venues;
        private final List<Attraction> attractions;

    }

    @Data
    public class Promoter{

        private final String id;

    }


    @Data
    public class EventLinks{

        private final Links.Link self;
        private final List<Links.Link> attractions;
        private final List<Links.Link> venues;
    }

}
