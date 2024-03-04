package de.godly.javaticketmaster.objects.embedded;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Links {

    private Link first;
    private Link self;
    private Link next;
    private Link last;



    @Data
    @AllArgsConstructor
    public class Link{
        private final String href;
    }


}
