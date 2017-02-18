package com.github.alexxand.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@AllArgsConstructor //Somehow, when the class has a constructor without arguments
                    // @Value does not add a constructor with all arguments.
@Builder
public class BasicManagerInf {
    String name;
    String lastName;
    String email;
    String password;
    public BasicManagerInf(){
        name = "";
        lastName = "";
        email = "";
        password = "";
    }
}
