package de.godly.javaticketmaster.objects.search;

import lombok.Getter;

public enum Source {

    TICKET_MASTER("ticketmaster"), UNIVERSE("universe"), FRONT_GATE("frontgate")
    ,TMR("tmr");

    @Getter
    private String paramId;
    Source(String paramId){
        this.paramId = paramId;
    }

}