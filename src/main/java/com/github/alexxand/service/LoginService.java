package com.github.alexxand.service;

import com.github.alexxand.model.Credentials;

public interface LoginService {
    boolean check(Credentials credentials);
    int getId(Credentials credentials);
}
