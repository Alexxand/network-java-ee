package com.github.alexxand.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@AllArgsConstructor //Somehow, when the class has a constructor without arguments
                    // @Value does not add a constructor with all arguments.
@Builder
public class CompanyInf {
    String company;
    String position;
    public CompanyInf(){
        company = "";
        position = "";
    }
}
