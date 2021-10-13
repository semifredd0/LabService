package com.uniba.di.dfmdevelop.labservice.exception;

public class CustomException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Costruttore aparamterico.<br>
     * Si limita a richiamare il costruttore
     * aparametrico della classe Exception.
     * @see Exception#Exception()
     */
    public CustomException() {
        super();
    }

    /**
     * Costruttore parametrico.<br>
     * Riceve come parametro un messaggio di errore
     * e si occupa di invocare il costruttore della
     * classe Exception che prende in input una stringa.
     * @param message Messaggio di errore.
     * @see Exception#Exception(String)
     */
    public CustomException(String message) {
        super(message);
    }

    /**
     * Costruttore parametrico.<br>
     * Riceve come parametro un oggetto Throwable
     * che indica la causa dell'eccezione, e si occupa
     * di invocare il costruttore della classe Exception
     * che prende in input un oggetto Throwable.
     * @param cause Causa dell'eccezione.
     * @see Exception#Exception(Throwable)
     */
    public CustomException(Throwable cause) {
        super(cause);
    }

    /**
     * Costruttore parametrico.<br>
     * Riceve come parametri sia un oggetto Throwable
     * sia un messaggio di errore, e si occupa
     * di invocare il costruttore della classe Exception
     * che prende in input i due parametri.
     * @param message Messaggio di errore.
     * @param cause Causa dell'eccezione.
     * @see Exception#Exception(String, Throwable)
     */
    public CustomException(String message, Throwable cause) {
        super(message,cause);
    }
}
