package de.godly.javaticketmaster.objects.embedded;

import lombok.Data;

import java.util.List;

@Data
public class Twitter {

    private final String handle;
    private final List<String> hashtags;
}
