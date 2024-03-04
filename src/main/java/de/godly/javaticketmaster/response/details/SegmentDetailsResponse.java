package de.godly.javaticketmaster.response.details;

import com.google.gson.annotations.SerializedName;
import de.godly.javaticketmaster.objects.embedded.Classification;
import de.godly.javaticketmaster.objects.embedded.Genre;
import de.godly.javaticketmaster.objects.embedded.Links;
import de.godly.javaticketmaster.response.DiscoveryResponse;
import lombok.Data;

import java.util.List;

@Data
public class SegmentDetailsResponse extends DiscoveryResponse {

    private final String id;
    private final String name;

    @SerializedName("_embedded")
    private final Segment segment;


    @Data
    public class Segment {

        private final List<SegmentGenre> genres;

    }
    @Data
    public class SegmentGenre {
        private final String id;
        private final String name;
        @SerializedName("_embedded")
        private final SubgenreContainer subgenreContainer;

    }
    @Data
    public class SubgenreContainer {

        private final List<Genre> subgenres;

    }
}
