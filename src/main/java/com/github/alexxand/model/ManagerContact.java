package com.github.alexxand.model;

import lombok.Builder;
import lombok.Value;

@Value
public class ManagerContact {
    Manager manager;
    Type type;

    public enum Type{
        NONE,
        BOSS,
        EMPLOYEE
    }

}
