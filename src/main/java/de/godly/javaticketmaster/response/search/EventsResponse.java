package de.godly.javaticketmaster.response.search;

import com.google.gson.annotations.SerializedName;
import de.godly.javaticketmaster.objects.Event;
import de.godly.javaticketmaster.response.PagedResponse;
import lombok.Data;

import java.util.List;


@Data
public class EventsResponse extends PagedResponse {

    @SerializedName("_embedded")
    private final Events eventsWrapper;

    @Data
    public class Events{

        private final List<Event> events;

    }

}
