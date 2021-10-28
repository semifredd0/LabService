package com.uniba.di.dfmdevelop.labservice.email;

import com.uniba.di.dfmdevelop.labservice.exception.CustomException;

public interface EmailSender {
    void send(String to, String email, String subject) throws CustomException;
}