package de.godly.javaticketmaster.objects.embedded;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.Nullable;

import java.time.*;
import java.time.format.DateTimeFormatter;

@Data
public class Dates {

    private final LocalDate start;
    private final LocalDate end;
    private final String timezone;
    private Status status;
    @Nullable
    private boolean spanMultipleDays;


    @Data
    public class LocalDate {

        private final String localDate;
        private final String dateTime;
        private final boolean dateTBD;
        private final boolean dateTBA;
        private final boolean timeTBA;
        private final boolean noSpecificTime;


        @Nullable
        public ZonedDateTime getZonedDateTime() {
            if(getDateTime() == null)return null;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
            ZonedDateTime dateTime = ZonedDateTime.parse(getDateTime(), formatter);
            return dateTime;
        }
        @Nullable
        public java.util.Date getDate(){
            if(getZonedDateTime() != null)return new java.util.Date(getZonedDateTime().toEpochSecond() * 1000L);
            else return java.util.Date.from(java.time.LocalDate.parse(localDate).atStartOfDay(ZoneId.systemDefault()).toInstant());
        }

    }

    @AllArgsConstructor
    public class Status {

        private final String code;


        public StatusCode getStatusCode() {
            return StatusCode.getById(this.code);
        }

        public enum StatusCode {

            ON_SALE("onsale"), OFF_SALE("offsale"), CANCELLED("canceled"), POSTPONED("postponed"), RESCHEDULED("rescheduled");

            String id;

            StatusCode(String id) {
                this.id = id;
            }

            public static StatusCode getById(String id) {
                for (StatusCode statusCode : values()) {
                    if (statusCode.id.equalsIgnoreCase(id)) {
                        return statusCode;
                    }
                }
                return null;
            }

        }

    }

}
