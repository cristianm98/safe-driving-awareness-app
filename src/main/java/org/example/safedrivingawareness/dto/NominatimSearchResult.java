package org.example.safedrivingawareness.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record NominatimSearchResult(
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
