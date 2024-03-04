package de.godly.javaticketmaster.objects.embedded;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
/**
 *
 */
public class Image {

    private final String ratio;
    private final String url;
    private final int width;
    private final int height;
    private boolean fallback;

}
