package org.example.safedrivingawareness.component;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.example.safedrivingawareness.dto.NominatimSearchResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Class used to interact with Nominatim through REST calls.
 * All the Nominatim APIs can be found at the following url:
 * 
 * @see <a href=
 *      "https://nominatim.org/release-docs/latest/api/Search/">
 *      Nominatim Search API</a>
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class NominatimClient {

    @Value("${nominatim.api}")
    private final String nominatimServiceApi;
    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Call the nominatim search api using the free form query.
     *
     * @param freeFormQuery the query
     * @return list of {@link NominatimSearchResponse}
     * @see <a href=
     *      "https://nominatim.org/release-docs/latest/api/Search/">Nominatim Search
     *      API</a>
     * @see NominatimSearchResponse
     */
    public List<NominatimSearchResponse> search(String freeFormQuery) {
        try {
            URI nominatimSearchUri = buildNominatimSearchUri(freeFormQuery);
            log.debug("Performing GET request to: {}", nominatimSearchUri);
            ResponseEntity<NominatimSearchResponse[]> response = restTemplate
                    .getForEntity(nominatimSearchUri, NominatimSearchResponse[].class);
            return handleResponse(freeFormQuery, response);
        } catch (RestClientResponseException ex) {
            String error = String.format("Error while calling nominatim service search api. Query used: %s",
                    freeFormQuery);
            log.error(error);
            return Collections.emptyList();
        }
    }

    private URI buildNominatimSearchUri(String freeFormQuery) {
        return UriComponentsBuilder.fromHttpUrl(nominatimServiceApi)
                .path("search")
                .queryParam("q", freeFormQuery).build().toUri();
    }

    private List<NominatimSearchResponse> handleResponse(String freeFormQuery,
            ResponseEntity<NominatimSearchResponse[]> response) {
        if (response.getBody() != null) {
            return Arrays.asList(response.getBody());
        } else {
            log.warn("Null response retrieved while calling nominatim service search api. Query used: {}",
                    freeFormQuery);
            return Collections.emptyList();
        }
    }
}
