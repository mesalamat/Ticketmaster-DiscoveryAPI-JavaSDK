package de.godly.javaticketmaster.response;

import com.google.gson.annotations.SerializedName;
import de.godly.javaticketmaster.objects.embedded.Links;
import lombok.Data;

@Data
public abstract class DiscoveryResponse {


    @SerializedName("_links")
    private Links links;


}
