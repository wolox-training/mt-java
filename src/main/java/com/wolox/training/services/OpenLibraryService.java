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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenLibraryService {

    @Value("${external.api.url}")
    private String baseUrl;

    public Optional<BookInfoDTO> bookInfo(String isbn) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, Visibility.NONE);
        objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        RestTemplate restTemplate = new RestTemplate();
        String queryParam = "ISBN:" + isbn;
        String url = String.format(baseUrl, queryParam);
        BookInfoDTO bookInfo = new BookInfoDTO();

        try {
            JsonNode bookInfoJsonNode = objectMapper
                    .readTree(restTemplate.getForObject(url, String.class));
            HashMap<String, Object> map = objectMapper
                    .treeToValue(bookInfoJsonNode.get(queryParam), HashMap.class);
            bookInfo = objectMapper.convertValue(map, BookInfoDTO.class);

            if (bookInfo != null) {
                bookInfo.setIsbn(isbn);
            }

        } catch (JsonProcessingException e) {
            throw new MyJsonProcessingException(e.getMessage());
        }

        return Optional.ofNullable(bookInfo);
    }

}
