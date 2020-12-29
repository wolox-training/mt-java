package com.wolox.training.services;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wolox.training.dtos.BookInfoDTO;
import com.wolox.training.exceptions.MyJsonProcessingException;
import java.util.HashMap;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenLibraryService {

    private static final String BASE_URL = "https://openlibrary.org/api/books";

    public Optional<BookInfoDTO> bookInfo(String isbn) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, Visibility.NONE);
        objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        RestTemplate restTemplate = new RestTemplate();

        String url = new StringBuilder(BASE_URL).append("?bibkeys=ISBN:").append(isbn)
                .append("&format=json&jscmd=data").toString();
        BookInfoDTO bookInfo = new BookInfoDTO();

        try {
            JsonNode bookInfoJsonNode = objectMapper
                    .readTree(restTemplate.getForObject(url, String.class));
            HashMap<String, Object> mapa = objectMapper
                    .treeToValue(bookInfoJsonNode.get("ISBN:" + isbn), HashMap.class);
            bookInfo = objectMapper.convertValue(mapa, BookInfoDTO.class);

            if (bookInfo != null) {
                bookInfo.setIsbn(isbn);
            }

        } catch (JsonProcessingException e) {
            throw new MyJsonProcessingException(e.getMessage());
        }

        return Optional.ofNullable(bookInfo);
    }

}
