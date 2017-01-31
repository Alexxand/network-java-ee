package com.github.alexxand.model;

import lombok.Builder;
import lombok.Value;

import java.io.InputStream;

@Value
@Builder
public class Manager {
    private int id;
    private String email;
    private String passwordHash;
    private String company;
    private String position;
    private InputStream photo;
}
