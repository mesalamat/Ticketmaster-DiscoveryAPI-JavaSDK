package de.godly.javaticketmaster.objects.embedded;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import org.jetbrains.annotations.Nullable;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
public class Sales {


    @SerializedName("public")
    private final Sale publicSale;
    @Nullable
    private final List<PreSale> presales;

    @Data
    public class PreSale{

        @Nullable
        private final String startDateTime;
        @Nullable
        private final String endDateTime;
        private final String url;
        private final String name;
        private final String description;
        @Nullable
        public ZonedDateTime getZonedDateTimeStart() {
            if(getStartDateTime() == null)return null;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
            ZonedDateTime dateTime = ZonedDateTime.parse(getStartDateTime() , formatter);
            return dateTime;
        }
        @Nullable
        public ZonedDateTime getZonedDateTimeEnd() {
            if(getEndDateTime() == null)return null;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
            ZonedDateTime dateTime = ZonedDateTime.parse(getEndDateTime() , formatter);
            return dateTime;
        }
    }
    @Data
    public class Sale{

        @Nullable
        private final String startDateTime;
        @Nullable
        private final String endDateTime;
        private final boolean startTBD;
        @Nullable
        public ZonedDateTime getZonedDateTimeStart() {
            if(getStartDateTime() == null)return null;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
            ZonedDateTime dateTime = ZonedDateTime.parse(getStartDateTime() , formatter);
            return dateTime;
        }
        @Nullable
        public ZonedDateTime getZonedDateTimeEnd() {
            if(getEndDateTime() == null)return null;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
            ZonedDateTime dateTime = ZonedDateTime.parse(getEndDateTime() , formatter);
            return dateTime;
        }
    }

}
