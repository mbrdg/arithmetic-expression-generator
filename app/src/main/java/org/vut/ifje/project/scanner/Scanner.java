package org.vut.ifje.project.scanner;


import org.vut.ifje.project.reporter.Cursor;
import org.vut.ifje.project.reporter.Reporter;
import org.vut.ifje.project.reporter.error.LexicalError;

import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Scanner {
    private static final Map<String, TokenType> functions = Map.of(
            "add", TokenType.ADD,
            "sub", TokenType.SUB,
            "mul", TokenType.MUL,
            "div", TokenType.DIV,
            "mod", TokenType.MOD,
            "pow", TokenType.POW
    );

    private final StringCharacterIterator iterator;
    private final List<Token> tokens = new ArrayList<>();
    private final Reporter reporter = new Reporter();
    private final Cursor cursor = new Cursor();

    public Scanner(String source) {
        this.iterator = new StringCharacterIterator(source);
    }

    public Reporter getReporter() {
        return reporter;
    }

    public List<Token> scan() {
        while (!done()) {
            scanToken();
        }

        tokens.add(new Token(TokenType.EOF, "", cursor));
        return tokens;
    }

    private boolean done() {
        return iterator.current() == StringCharacterIterator.DONE;
    }

    private void scanToken() {
        switch (iterator.current()) {
            case '(' -> tokens.add(new Token(TokenType.LEFT_PARENTHESIS, "(", cursor));
            case ')' -> tokens.add(new Token(TokenType.RIGHT_PARENTHESIS, ")", cursor));
            case ',' -> tokens.add(new Token(TokenType.COMMA, ",", cursor));
            case '+' -> tokens.add(number(TokenType.POSITIVE_NUMBER, true));
            case '-' -> tokens.add(number(TokenType.NEGATIVE_NUMBER, true));
            case '/' -> comment();
            case '\n' -> cursor.incrementLine();
            case ' ', '\t', '\r' -> {}
            default -> {
                if (isDigit(iterator.current())) {
                    tokens.add(number(TokenType.POSITIVE_NUMBER, false));
                } else if (isAlpha(iterator.current())) {
                    tokens.add(identifier());
                } else {
                    String explanation = String.format("unknown symbol '%c'", iterator.current());
                    reporter.add(new LexicalError(explanation, cursor));
                }
            }
        }

        iterator.next();
        cursor.incrementColumn();
    }

    private void comment() {
        if (iterator.next() != '*') {
            reporter.add(new LexicalError("unknown symbol '/'", cursor));
            return;
        }

        cursor.incrementColumn();

        while (!done()) {
            iterator.next();
            cursor.incrementColumn();

            if (iterator.current() == '\n') {
                cursor.incrementLine();
            }

            if (iterator.current() == '*' && iterator.next() == '/') {
                cursor.incrementColumn();
                break;
            }
        }
    }

    private Token number(TokenType type, boolean tokenContainsSign) {
        StringBuilder lexeme = new StringBuilder();
        Cursor tokenCursor = new Cursor(cursor.line(), cursor.column());

        if (tokenContainsSign) {
            iterator.next();
            cursor.incrementColumn();
        }

        while (isDigit(iterator.current())) {
            lexeme.append(iterator.current());
            iterator.next();
            cursor.incrementColumn();
        }

        if (iterator.current() == '.' && isDigit(lookahead())) {
            lexeme.append('.');
            iterator.next();
            cursor.incrementColumn();

            do {
                lexeme.append(iterator.current());
                iterator.next();
                cursor.incrementColumn();
            } while (isDigit(iterator.current()));
        }

        if (iterator.current() == 'e' && isDigit(lookahead())) {
            lexeme.append('e');
            iterator.next();
            cursor.incrementColumn();

            do {
                lexeme.append(iterator.current());
                iterator.next();
                cursor.incrementColumn();
            } while (isDigit(iterator.current()));
        }

        // Put the iterator in the correct position to advance in scanToken()
        iterator.previous();

        return new Token(type, lexeme.toString(), tokenCursor);
    }

    private Token identifier() {
        StringBuilder lexeme = new StringBuilder();
        Cursor tokenCursor = new Cursor(cursor.line(), cursor.column());

        do {
            lexeme.append(iterator.current());
            iterator.next();
            cursor.incrementColumn();
        } while (isAlpha(iterator.current()));

        // Put the iterator in the correct position to advance in scanToken()
        iterator.previous();

        TokenType type = functions.getOrDefault(lexeme.toString(), TokenType.UNKNOWN);
        if (type == TokenType.UNKNOWN) {
            String explanation = String.format("unknown identifier '%s'", lexeme);
            reporter.add(new LexicalError(explanation, cursor));
        }

        return new Token(type, lexeme.toString(), tokenCursor);
    }

    private boolean isDigit(char character) {
        return character >= '0' && character <= '9';
    }

    private boolean isAlpha(char character) {
        return character >= 'a' && character <= 'z';
    }

    private char lookahead() {
        char character = iterator.next();
        iterator.previous();

        return character;
    }
}
