package org.vut.ifje.project.scanner;

import org.vut.ifje.project.reporter.Cursor;

import java.util.Objects;

/**
 * A token is a lexical unit that is part of the source program
 *
 * @param type   the type of the token
 * @param lexeme the representation of the token
 * @param cursor the position of the beginning of the token
 */
public record Token(TokenType type, String lexeme, Cursor cursor) {
    public Token {
        Objects.requireNonNull(type);
        Objects.requireNonNull(lexeme);
        Objects.requireNonNull(cursor);
    }
}
