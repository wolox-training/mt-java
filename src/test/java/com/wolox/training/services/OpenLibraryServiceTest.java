package com.wolox.training.services;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.junit.Assert.assertEquals;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.wolox.training.dtos.BookInfoDTO;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@Import(value = OpenLibraryService.class)
@TestPropertySource("classpath:test.application.properties")
public class OpenLibraryServiceTest {

    private WireMockServer wireMockServer;

    @Autowired
    private OpenLibraryService openLibraryService;

    @BeforeEach
    public void setUp() {
        wireMockServer = new WireMockServer(8082, 8083);
        this.wireMockServer.start();
    }

    @Test
    public void wiremockTest() {
        String isbn = "0385472579";
        String url = String.format("/api/books?bibkeys=ISBN:%s&format=json&jscmd=data", isbn);
        this.wireMockServer.stubFor(
                WireMock.get(url)
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                .withBody("{\n"
                                        + "\"ISBN:0385472579\": {\n"
                                        + "\"url\": \"https://openlibrary.org/books/OL1397864M/Zen_speaks\",\n"
                                        + "\"key\": \"/books/OL1397864M\",\n"
                                        + "\"title\": \"Zen speaks\",\n"
                                        + "\"subtitle\": \"shouts of nothingness\",\n"
                                        + "\"authors\": [\n"
                                        + "{\n"
                                        + "\"url\": \"https://openlibrary.org/authors/OL223368A/Zhizhong_Cai\",\n"
                                        + "\"name\": \"Zhizhong Cai\"\n"
                                        + "}\n"
                                        + "],\n"
                                        + "\"number_of_pages\": 159,\n"
                                        + "\"pagination\": \"159 p. :\",\n"
                                        + "\"identifiers\": {\n"
                                        + "\"librarything\": [\n"
                                        + "\"192819\"\n"
                                        + "],\n"
                                        + "\"goodreads\": [\n"
                                        + "\"979250\"\n"
                                        + "],\n"
                                        + "\"isbn_10\": [\n"
                                        + "\"0385472579\"\n"
                                        + "],\n"
                                        + "\"lccn\": [\n"
                                        + "\"93005405\"\n"
                                        + "],\n"
                                        + "\"openlibrary\": [\n"
                                        + "\"OL1397864M\"\n"
                                        + "]\n"
                                        + "},\n"
                                        + "\"classifications\": {\n"
                                        + "\"lc_classifications\": [\n"
                                        + "\"BQ9265.6 .T7313 1994\"\n"
                                        + "],\n"
                                        + "\"dewey_decimal_class\": [\n"
                                        + "\"294.3/927\"\n"
                                        + "]\n"
                                        + "},\n"
                                        + "\"publishers\": [\n"
                                        + "{\n"
                                        + "\"name\": \"Anchor Books\"\n"
                                        + "}\n"
                                        + "],\n"
                                        + "\"publish_places\": [\n"
                                        + "{\n"
                                        + "\"name\": \"New York\"\n"
                                        + "}\n"
                                        + "],\n"
                                        + "\"publish_date\": \"1994\",\n"
                                        + "\"subjects\": [\n"
                                        + "{\n"
                                        + "\"name\": \"Caricatures and cartoons\",\n"
                                        + "\"url\": \"https://openlibrary.org/subjects/caricatures_and_cartoons\"\n"
                                        + "},\n"
                                        + "{\n"
                                        + "\"name\": \"Zen Buddhism\",\n"
                                        + "\"url\": \"https://openlibrary.org/subjects/zen_buddhism\"\n"
                                        + "}\n"
                                        + "],\n"
                                        + "\"cover\": {\n"
                                        + "\"small\": \"https://covers.openlibrary.org/b/id/240726-S.jpg\",\n"
                                        + "\"medium\": \"https://covers.openlibrary.org/b/id/240726-M.jpg\",\n"
                                        + "\"large\": \"https://covers.openlibrary.org/b/id/240726-L.jpg\"\n"
                                        + "}\n"
                                        + "}\n"
                                        + "}"))
        );

        Optional<BookInfoDTO> optionalBookInfoDTO = openLibraryService.bookInfo(isbn);

        assertEquals(optionalBookInfoDTO.get().getIsbn(), isbn);
    }
}
