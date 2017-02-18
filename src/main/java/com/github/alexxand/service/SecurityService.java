package com.github.alexxand.service;

public interface SecurityService {
    String encrypt(String password);
    boolean check(String password, String passwordHash);
}
