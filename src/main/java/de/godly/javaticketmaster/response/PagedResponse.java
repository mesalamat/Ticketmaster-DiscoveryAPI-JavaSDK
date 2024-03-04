package de.godly.javaticketmaster.response;

import com.google.gson.annotations.SerializedName;
import de.godly.javaticketmaster.objects.PageInfo;

public abstract class PagedResponse extends DiscoveryResponse {
    @SerializedName("page")
    private PageInfo pageInfo;
}
