package org.example.safedrivingawareness.component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.safedrivingawareness.dto.NominatimSearchResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class NominatimClient {

    @Value("${service.nominatim.api}")
    private final String nominatimServiceApi;
    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Call the nominatim search api using free form query
     *
     * @param freeFormQuery the query
     * @return list of {@link NominatimSearchResult}
     * @see <a href="https://nominatim.org/release-docs/latest/api/Search/">Nominatim Search API</a>
     */
    public List<NominatimSearchResult> search(String freeFormQuery) {
        try {
            URI nominatimSearchUri = UriComponentsBuilder.fromHttpUrl(nominatimServiceApi)
                    .path("search")
                    .queryParam("q", freeFormQuery).build().toUri();
            log.debug("Performing GET request to: {}",nominatimSearchUri);
            ResponseEntity<NominatimSearchResult[]> response = restTemplate
                    .getForEntity(nominatimSearchUri, NominatimSearchResult[].class);
            if (response.getBody() != null) {
                return Arrays.asList(response.getBody());
            } else {
                log.warn("Null response retrieved while calling nominatim service search api. Query used: {}", freeFormQuery);
                return Collections.emptyList();
            }
        } catch (RestClientResponseException ex) {
            String error = String.format("Error while calling nominatim service search api. Query used: %s", freeFormQuery);
            log.error(error);
            return Collections.emptyList();
        }
    }
}
