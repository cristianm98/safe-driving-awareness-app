package org.example.safedrivingawareness.properties;

import lombok.Data;
import org.example.safedrivingawareness.model.PenaltyCategory;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "messages")
@Data
public class PenaltyMessagesProperties {

    private Map<PenaltyCategory, String> penalties;
}
