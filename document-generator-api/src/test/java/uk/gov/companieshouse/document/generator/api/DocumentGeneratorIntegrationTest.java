package uk.gov.companieshouse.document.generator.api;

import static com.github.tomakehurst.wiremock.client.WireMock.created;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.test.web.servlet.client.assertj.RestTestClientResponse;
import org.wiremock.spring.EnableWireMock;
import uk.gov.companieshouse.document.generator.api.models.DocumentRequest;

@EnableWireMock
@ActiveProfiles("test")
@AutoConfigureRestTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DocumentGeneratorIntegrationTest {

    @Test
    void shouldGenerateDocument(@Autowired RestTestClient restTestClient) {
        // Given
        stubFor(get("/transactions/12345/accounts/6789")
                .willReturn(okJson("{" +
                        "  \"id\": 6789," +
                        "  \"links\": {" +
                        "    \"transaction\": \"/transactions/12345\"," +
                        "    \"abridged_accounts\": \"/transactions/12345/accounts/6789/abridged/1234\"" +
                        "  }" +
                        "}")));

        stubFor(get("/private/transactions/12345")
                .willReturn(okJson("{" +
                        "  \"id\": 12345," +
                        "  \"company_number\": \"12345678\"" +
                        "}")));

        stubFor(get("/transactions/12345/accounts/6789/abridged/1234")
                .willReturn(okJson("{" +
                        "  \"id\": 12345," +
                        "  \"approval\": {" +
                        "    \"name\": \"John Doe\"," +
                        "    \"date\": \"2024-06-01\"" +
                        "  }," +
                        "  \"current_period\": {" +
                        "    \"period_start_on\": \"2024-01-01\"," +
                        "    \"period_end_on\": \"2024-06-30\"," +
                        "    \"balance_sheet\": {}" +
                        "  }" +
                        "}")));

        stubFor(get("/company/12345678")
                .willReturn(okJson("{" +
                        "  \"company_name\": \"Test Company Ltd\"," +
                        "  \"company_number\": \"12345678\"," +
                        "  \"company_status\": \"active\"" +
                        "}")));

        stubFor(post("/document-render/store")
                .willReturn(created()
                        .withBody("{ \"document_size\": 99999 }")
                        .withHeader("Location", "s3://bucket")));

        DocumentRequest documentRequest = new DocumentRequest();
        documentRequest.setDocumentType("ACCOUNTS");
        documentRequest.setResourceUri("/transactions/12345/accounts/6789");
        documentRequest.setMimeType("text/html");

        // When
        RestTestClient.ResponseSpec responseSpec = restTestClient
                .post().uri("/private/documents/generate")
                .accept(MediaType.APPLICATION_JSON)
                .body(documentRequest)
                .exchange();

        // Then
        assertThat(RestTestClientResponse.from(responseSpec))
                .hasStatus(HttpStatus.CREATED)
                .bodyJson().satisfies(json -> {
                    assertThat(json).extractingPath("$.description").isEqualTo("");
                    assertThat(json).extractingPath("$.description_identifier").isEqualTo("abridged-accounts");
                    assertThat(json).extractingPath("$.description_values.period_end_on").isEqualTo("2024-06-30");
                    assertThat(json).extractingPath("$.links.location").isEqualTo("s3://bucket");
                    assertThat(json).extractingPath("$.size").isEqualTo("99999");
                });
    }
}
