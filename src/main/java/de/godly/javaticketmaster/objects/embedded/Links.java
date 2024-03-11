package de.godly.javaticketmaster.objects.embedded;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Links {

    private final Link first;
    private final Link self;
    private final Link next;
    private final Link last;



    @Data
    @AllArgsConstructor
    public class Link{
        private final String href;
    }


}
