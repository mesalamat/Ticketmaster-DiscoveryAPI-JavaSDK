package de.godly.javaticketmaster.objects.embedded;

import com.google.gson.annotations.SerializedName;
import de.godly.javaticketmaster.objects.Attraction;
import lombok.Data;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Data
public class Classification {

    private final boolean primary;
    private final ClassificationSegment segment;
    @Nullable("Only appears when using Classification Search!")
    private final ClassificationType type;
    private final ClassificationSubType subType;
    private final boolean family;

    @Data
    public class ClassificationSubType {

        private final String id;
        private final String name;
        private final String locale;

    }
    @Data
    public class ClassificationType {

        private final List<ClassificationSubType> subTypes;
        private final String id;
        private final String name;
        private final String locale;

    }

    @Data
    public class ClassificationSegment {
        private final String id;
        private final String name;
        @SerializedName("_embedded")
        private final GenreContainer genreContainer;

        @SerializedName("_links")
        private final Links links;

        @Data
        public class GenreContainer {

            private final List<ClassificationGenre> genres;

        }

        @Data
        public class ClassificationGenre {

            @SerializedName("_embedded")
            private final SubgenreContainer subgenreContainer;


            private final String id;
            private final String name;

        }

        @Data
        public class SubgenreContainer {

            private final List<Genre> subgenres;

        }
    }
}