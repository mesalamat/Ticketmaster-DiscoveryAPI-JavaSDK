package de.godly.javaticketmaster.objects;

import lombok.Data;

@Data
public class PageInfo {

    int size;
    int totalElements;
    int totalPages;
    int number;

}
