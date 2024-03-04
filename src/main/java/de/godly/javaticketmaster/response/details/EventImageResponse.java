package de.godly.javaticketmaster.response.details;

import de.godly.javaticketmaster.objects.embedded.Image;
import de.godly.javaticketmaster.response.DiscoveryResponse;
import lombok.Data;

import java.util.List;

@Data
public class EventImageResponse extends DiscoveryResponse {

    private final List<Image> images;

}
