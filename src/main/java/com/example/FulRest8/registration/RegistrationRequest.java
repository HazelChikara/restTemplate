package com.example.FulRest8.registration;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
//Building the request parameters
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {
//    When a client sends a request we capture:
    private final String firstName;
    private final String lastName;
    private final String email;
    private  final String password;
}
