package org.vut.ifje.project.scanner;

import java.util.Objects;

public record Token(TokenType type, String lexeme) {
    public Token {
        Objects.requireNonNull(type);
        Objects.requireNonNull(lexeme);
    }
}
