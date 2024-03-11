package de.godly.javaticketmaster.response;

import com.google.gson.annotations.SerializedName;
import de.godly.javaticketmaster.objects.PageInfo;
import lombok.Data;


@Data
public abstract class PagedResponse extends DiscoveryResponse {
    @SerializedName("page")
    private final PageInfo pageInfo;
}
