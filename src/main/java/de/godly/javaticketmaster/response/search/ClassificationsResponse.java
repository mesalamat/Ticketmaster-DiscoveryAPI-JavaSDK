package de.godly.javaticketmaster.response.search;


import com.google.gson.annotations.SerializedName;
import de.godly.javaticketmaster.objects.embedded.Classification;
import de.godly.javaticketmaster.response.PagedResponse;
import lombok.Data;

import java.util.List;

@Data
public class ClassificationsResponse extends PagedResponse{

    @SerializedName("_embedded")
    private final Classifications classificationsWrapper;
    @Data
    public class Classifications{
        private final List<Classification> classifications;
    }


}
