package com.enigma;

public class InvalidConfigurationException extends RuntimeException {
    public InvalidConfigurationException(String errorMessage) {
        super(errorMessage);
    }
}
