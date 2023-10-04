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
            case '+' -> tokens.add(number(TokenType.POSITIVE_NUMBER, true));
            case '-' -> tokens.add(number(TokenType.NEGATIVE_NUMBER, true));
            case '\r', ' ', '\t' -> {}
            case '\n' -> ++line;
            default -> {
                if (isDigit(iterator.current())) {
                    tokens.add(number(TokenType.POSITIVE_NUMBER, false));
                }
            }
        }

        iterator.next();
    }

    private Token number(TokenType type, boolean consumeSign) {
        StringBuilder lexeme = new StringBuilder();

        if (consumeSign) {
            iterator.next();
        }

        while (isDigit(iterator.current())) {
            lexeme.append(iterator.current());
            iterator.next();
        }

        if (iterator.current() == '.' && isDigit(lookahead())) {
            lexeme.append('.');
            iterator.next();

            do {
                lexeme.append(iterator.current());
                iterator.next();
            } while (isDigit(iterator.current()));
        }

        if (iterator.current() == 'e' && isDigit(lookahead())) {
            lexeme.append('e');
            iterator.next();

            do {
                lexeme.append(iterator.current());
                iterator.next();
            } while (isDigit(iterator.current()));
        }

        // Put the iterator in the correct position to advance in scanToken()
        iterator.previous();

        return new Token(type, lexeme.toString());
    }

    private boolean isDigit(char character) {
        return character >= '0' && character <= '9';
    }

    private char lookahead() {
        char character = iterator.next();
        iterator.previous();

        return character;
    }
}
