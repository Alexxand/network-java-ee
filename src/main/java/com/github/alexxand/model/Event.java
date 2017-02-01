package com.github.alexxand.model;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Value
@Builder
public class Event {
    private int eventId;
    private LocalDateTime dateTime;
    private String text;
    int creatorId;
    List<Integer> participantIds;
}
