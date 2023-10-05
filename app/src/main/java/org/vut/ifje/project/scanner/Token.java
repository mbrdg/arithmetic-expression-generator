package org.vut.ifje.project.scanner;

import org.vut.ifje.project.reporter.Cursor;

import java.util.Objects;

public record Token(TokenType type, String lexeme, Cursor cursor) {
    public Token {
        Objects.requireNonNull(type);
        Objects.requireNonNull(lexeme);
        Objects.requireNonNull(cursor);
    }
}
