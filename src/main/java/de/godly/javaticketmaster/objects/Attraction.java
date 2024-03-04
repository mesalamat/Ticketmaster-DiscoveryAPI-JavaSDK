package de.godly.javaticketmaster.objects;

import com.google.gson.annotations.SerializedName;
import de.godly.javaticketmaster.objects.embedded.Genre;
import de.godly.javaticketmaster.objects.embedded.Image;
import lombok.Data;

import java.util.List;

@Data
public class Attraction {


    private final String id;
    private final String name;
    private final String locale;

    private final List<Image> images;
    @SerializedName("_links")
    private final Event.EventLinks links;
    private final List<AttractionClassification> classifications;


    /**
     * This class represents the Classification of the Attraction or Event
     * Boolean primary represents whether the Classification is the primary Classification of the Event
     * Segment represents the Category/Segment of TicketMaster, e.g. Sports, Music etc.
     * Genre & Subgenre represent the Genre of the Attraction or Event, e.g. Hard Rock in Segment Music
     */
    @Data
    public class AttractionClassification {

        private final boolean primary;
        private final AttractionSegment segment;
        private final Genre genre;
        private final Genre subGenre;
        private boolean family;
        /**
         * This class represents the Genre of the Segment/Attraction, e.g. Golf for Sports or Hard Rock for Music
         */
        @Data
        public class AttractionSegment {
            private final String id;
            private final String name;
        }

    }


}
