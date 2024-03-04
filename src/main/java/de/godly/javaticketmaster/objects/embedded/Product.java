package de.godly.javaticketmaster.objects.embedded;

import lombok.Data;

@Data
public class Product {


    private final String name;
    private final String id;
    private final String url;
    private final String productType;

}
