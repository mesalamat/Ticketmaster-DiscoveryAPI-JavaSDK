package de.godly.javaticketmaster.response.details;


import com.google.gson.annotations.SerializedName;
import de.godly.javaticketmaster.objects.embedded.Classification;
import de.godly.javaticketmaster.response.PagedResponse;
import lombok.Data;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Data
public class ClassificationDetailsResponse extends PagedResponse {

    private final boolean primary;
    private final Classification.ClassificationSegment segment;
    @Nullable("Only appears when using Classification Search!")
    private final Classification.ClassificationType type;
    private final Classification.ClassificationSubType subType;
    private boolean family;


}
