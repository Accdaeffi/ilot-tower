package ru.ilot.ilottower.telegram.exception;

import lombok.Getter;

public class DataLogicException extends RuntimeException {
    @Getter
    private final String description;

    public DataLogicException(String description) {
        super();
        this.description = description;
    }

}
