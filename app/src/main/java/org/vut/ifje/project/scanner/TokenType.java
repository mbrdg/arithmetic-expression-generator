package org.vut.ifje.project.scanner;

public enum TokenType {
    // Single-character tokens
    LEFT_PARENTHESIS,
    RIGHT_PARENTHESIS,
    COMMA,

    // Number literal token
    POSITIVE_NUMBER,
    NEGATIVE_NUMBER,

    // Keyword operation tokens
    ADD,
    SUB,
    MUL,
    DIV,
    MOD,
    POW,

    // End-of-file token
    EOF,

    // Unknown token
    UNKNOWN,
}
