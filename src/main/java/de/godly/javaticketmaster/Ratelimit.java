package de.godly.javaticketmaster;

import lombok.Data;
import okhttp3.Headers;

import java.time.LocalDateTime;
import java.util.Date;


@Data
public class Ratelimit {

    private int maxRequests;
    private int remainingRequests;

    private int rateLimitOver;
    private Date rateLimitReset;

    public void handle(Headers headers) {
        this.maxRequests = Integer.parseInt(headers.get("Rate-Limit"));
        this.remainingRequests = Integer.parseInt(headers.get("Rate-Limit-Available"));
        this.rateLimitOver = Integer.parseInt(headers.get("Rate-Limit-Over"));
        this.rateLimitReset = new Date(Long.parseLong(headers.get("Rate-Limit-Reset")));
        //System.out.println(rateLimitReset.toLocaleString());
    }
}
