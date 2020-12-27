package com.wolox.training.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class BookInfoDTO {

    @JsonProperty("isbn_10")
    private String isbn;

    private String title = "-";

    private String subtitle = "-";

    private List<PublisherDTO> publishers = new ArrayList<>();

    @JsonProperty("publish_date")
    private String publishDate = "-";

    @JsonProperty("number_of_pages")
    private Integer numberOfPages = 0;

    private List<AuthorDTO> authors = new ArrayList<>();

}
