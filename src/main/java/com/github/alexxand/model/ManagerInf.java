package com.github.alexxand.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.io.InputStream;

@Getter
@Setter
public class ManagerInf {
    private String email;
    private String passwordHash;
    private String fullName;
    private CompanyInf companyInf;
    /*private InputStream photo;*/
}
