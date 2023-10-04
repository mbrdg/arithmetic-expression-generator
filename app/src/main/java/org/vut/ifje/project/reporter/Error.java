package org.vut.ifje.project.reporter;

import java.util.Objects;

public record Error(ErrorType type, String explanation, int line) {
    public Error {
        Objects.requireNonNull(type);
        Objects.requireNonNull(explanation);
    }
}
