package de.godly.javaticketmaster.response.details;

import com.google.gson.annotations.SerializedName;
import de.godly.javaticketmaster.objects.embedded.Classification;
import de.godly.javaticketmaster.objects.embedded.Genre;
import de.godly.javaticketmaster.response.DiscoveryResponse;
import lombok.Data;

import java.util.List;

@Data
public class GenreDetailsResponse extends DiscoveryResponse {

    private final String id;
    private final String name;

    @SerializedName("_embedded")
    private final Classification.ClassificationSegment.SubgenreContainer subgenreContainer;

    @SerializedName("subGenres")
    private final List<Genre> tertiarySubGenres;
}
