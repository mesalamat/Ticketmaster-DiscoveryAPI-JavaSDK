package de.godly.javaticketmaster.response.details;

import de.godly.javaticketmaster.response.DiscoveryResponse;
import lombok.Data;

@Data
public class SubgenreDetailsResponse extends DiscoveryResponse {

    private final String id;
    private final String name;

}
