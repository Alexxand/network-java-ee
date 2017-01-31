package com.github.alexxand.model;

import lombok.Builder;
import lombok.Value;

import java.util.Date;

@Value
@Builder
public class Message {
    private int messageId;
    private Date date;
    private String text;
    private int senderId;
    private int receiverId;
}
