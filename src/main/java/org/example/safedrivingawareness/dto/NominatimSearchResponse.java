package org.example.safedrivingawareness.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO containing the relevant information returned by the Nominatim Search API.
 * @see <a href="https://nominatim.org/release-docs/latest/api/Search/#json-with-address-details">
 * Nominatim Search API response
 * </a>
 */
public record NominatimSearchResponse(
        @JsonProperty("place_id")
        String placeId,
        @JsonProperty("lat")
        String latitude,
        @JsonProperty("lon")
        String longitude,
        @JsonProperty("display_name")
        String displayName,
        @JsonProperty("category")
        String category,
        @JsonProperty("type")
        String type,
        @JsonProperty("importance")
        double importance
) {
}
