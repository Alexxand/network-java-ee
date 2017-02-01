package com.github.alexxand.utils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Wrapper which {@link #available()} method returns the given to constructor size.
 */
public class InputStreamWrapper extends InputStream{
    private final InputStream inner;
    private final int size;

    public InputStreamWrapper(InputStream inner,int size){
        this.inner = inner;
        this.size = size;
    }

    @Override
    public int read() throws IOException {
        return inner.read();
    }

    public int available() {
        return size;
    }
}
