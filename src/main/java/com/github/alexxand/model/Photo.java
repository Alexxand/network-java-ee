package com.github.alexxand.model;

import lombok.Builder;
import lombok.Value;

import java.io.InputStream;

@Value
@Builder
public class Photo {
    InputStream inputStream;
    int size;
}
