package com.ignitiv.dto;

import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EventDTO {

    private String eventId;
    private String topic;
    private String entityId;
    private String correlationId;
    private Instant timestamp;

    @JsonProperty("isTest")
    private boolean test;

    private List<ExtendedPropertyDTO> extendedProperties;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isTest() {
        return test;
    }

    public void setTest(boolean test) {
        this.test = test;
    }

    public List<ExtendedPropertyDTO> getExtendedProperties() {
        return extendedProperties;
    }

    public void setExtendedProperties(List<ExtendedPropertyDTO> extendedProperties) {
        this.extendedProperties = extendedProperties;
    }
}
