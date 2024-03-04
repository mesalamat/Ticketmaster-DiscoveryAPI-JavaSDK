package de.godly.javaticketmaster.response.details;

import com.google.gson.annotations.SerializedName;
import de.godly.javaticketmaster.objects.Attraction;
import de.godly.javaticketmaster.objects.Event;
import de.godly.javaticketmaster.objects.embedded.*;
import de.godly.javaticketmaster.response.DiscoveryResponse;
import lombok.Data;
import org.jetbrains.annotations.Nullable;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
@Data
public class EventDetailsResponse extends DiscoveryResponse {

    @SerializedName("_embedded")
    private final Event.VenueAttractionContainer venueAttractionContainer;


    private final double distance;
    private final String units;
    private final String id;
    private final String locale;
    private final String name;
    private final String description;
    private final String additionalInfo;
    private final String url;
    private final List<Image> images;
    private final EventDates dates;
    private final Sales sales;
    private final String info;
    private final String pleaseNote;
    private final List<PriceRange> priceRanges;
    private final Event.Promoter promoter;
    private final List<Event.Promoter> promoters;
    private final List<Outlet> outlets;
    private final String productType;
    @SerializedName("products")
    private final List<Product> relatedProducts;
    private final Seatmap seatmap;
    private final Accessibility accessibility;
    private final TicketLimit ticketLimit;
    private final List<Attraction.AttractionClassification> classifications;



    @Data
    public class PriceRange{

        private final double minPrice;
        private final double maxPrice;
        private final String currency;
    }

    @Data
    public class EventDates {

        private final Dates.LocalDate start;
        private final Dates.LocalDate end;
        private final String timezone;
        private Dates.Status status;
        private final ApproximatedLocalDate access;
        @Nullable
        private boolean spanMultipleDays;



        @Data
        public class ApproximatedLocalDate {

            private final String startDateTime;
            private final String endDateTime;
            private final boolean startApproximate;
            private final boolean endApproximate;


            @Nullable
            public ZonedDateTime getZonedDateTimeStart() {
                if(getStartDateTime() == null)return null;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
                ZonedDateTime dateTime = ZonedDateTime.parse(getStartDateTime(), formatter);
                return dateTime;
            }
            @Nullable
            public ZonedDateTime getZonedDateTimeEnd() {
                if(getEndDateTime() == null)return null;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
                ZonedDateTime dateTime = ZonedDateTime.parse(getEndDateTime(), formatter);
                return dateTime;
            }
        }


    }

}