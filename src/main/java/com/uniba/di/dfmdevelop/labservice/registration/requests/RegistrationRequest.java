package com.uniba.di.dfmdevelop.labservice.registration.requests;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {

    private final String indirizzoEmail;
    private final String password;
    private final String ruolo;
}
