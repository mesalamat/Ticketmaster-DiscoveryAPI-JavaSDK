package de.godly.javaticketmaster;

import lombok.Data;
import okhttp3.Headers;

import java.time.LocalDateTime;
import java.util.Date;


@Data
public class Ratelimit {


    /**
     * Max Requests until the rateLimitReset for the API-Key
     */
    private int maxRequests;

    /**
     * Remaining Requests until the rateLimitReset for the API-Key
     */
    private int remainingRequests;

    /**
     * Amount of Requests over the Max Requests.
     */
    private int rateLimitOver;
    /**
     * Date when the Ratelimit will Reset for the API-Key
     *
     */
    private Date rateLimitReset;


    /**
     * Parses the Headers Object and gets the current Rate Limits for the API Key
     * @param headers
     */
    public void handle(Headers headers) {
        this.maxRequests = Integer.parseInt(headers.get("Rate-Limit"));
        this.remainingRequests = Integer.parseInt(headers.get("Rate-Limit-Available"));
        this.rateLimitOver = Integer.parseInt(headers.get("Rate-Limit-Over"));
        this.rateLimitReset = new Date(Long.parseLong(headers.get("Rate-Limit-Reset")));
    }
}
