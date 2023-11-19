package org.example.safedrivingawareness.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.safedrivingawareness.component.NominatimClient;
import org.example.safedrivingawareness.dto.NominatimSearchResult;
import org.example.safedrivingawareness.model.AutoCompleteEntry;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NominatimService {

    private final NominatimClient nominatimClient;

    /**
     * Return the
     *
     * @param query freeform query
     * @return list of {@link AutoCompleteEntry}
     */
    public Set<AutoCompleteEntry> autoComplete(String query) {
        List<NominatimSearchResult> nominatimResponse = nominatimClient.search(query);
        if (nominatimResponse.isEmpty()) {
            log.warn("No results found by geocoding service for query: {}", query);
            return Collections.emptySet();
        }
        log.info("Geocoding service found: {} results for query: {}", nominatimResponse.size(), query);
        return nominatimResponse.stream()
                .map(searchResult -> new AutoCompleteEntry(searchResult.latitude(), searchResult.longitude(),
                        searchResult.displayName()))
                .collect(Collectors.toSet());
    }
}
