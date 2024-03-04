package de.godly.javaticketmaster.objects.embedded.location;

import lombok.Data;
import org.jetbrains.annotations.Nullable;

@Data
public class Address{

    private String line1;
    @Nullable
    private String line2;
    @Nullable
    private String line3;
}

