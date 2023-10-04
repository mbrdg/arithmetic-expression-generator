package org.vut.ifje.project.scanner;


import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.List;

public class Scanner {
    private final StringCharacterIterator iterator;
    private final List<Token> tokens = new ArrayList<>();
    private int line = 1;

    public Scanner(String source) {
        this.iterator = new StringCharacterIterator(source);
    }

    public List<Token> scan() {
        while (!done()) {
            scanToken();
        }

        tokens.add(new Token(TokenType.EOF, ""));
        return tokens;
    }

    private boolean done() {
        return iterator.current() == StringCharacterIterator.DONE;
    }

    private void scanToken() {
        switch (iterator.current()) {
            case '(' -> tokens.add(new Token(TokenType.LEFT_PARENTHESIS, "("));
            case ')' -> tokens.add(new Token(TokenType.RIGHT_PARENTHESIS, ")"));
            case ',' -> tokens.add(new Token(TokenType.COMMA, ","));
            case ' ' | '\r' | '\t' -> {}
            case '\n' -> ++line;
            default -> {}
        }

        iterator.next();
    }
}
