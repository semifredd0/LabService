package com.uniba.di.dfmdevelop.labservice.exception;

public class ErrorMessage {

    // TODO Resend confirmation email, to activate the previous account
    public final static String EMAIL_ALREADY_TAKEN = "Indirizzo email gia' registrato";
    public final static String EMAIL_FAIL_SEND = "Impossibile inviare il link di conferma all'indirizzo email specificato";
    public final static String EMAIL_ALREADY_CONFIRMED = "Indirizzo email gia' confermato";
    public final static String TOKEN_NOT_FOUND = "Token non valido: token non trovato";
    public final static String TOKEN_EXPIRED = "Token non valido: token scaduto";
    public final static String PASSWORD_DOESNT_MATCH = "Errore: le password non coincidono";
}
