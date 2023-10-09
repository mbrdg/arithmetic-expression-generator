package org.vut.ifje.project.scanner;


import org.vut.ifje.project.reporter.Cursor;
import org.vut.ifje.project.reporter.ErrorReporter;
import org.vut.ifje.project.reporter.error.LexicalError;

import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A scanner is responsible for splitting a stream of characters into a list of tokens and of performing lexical analysis.
 * The input of this is a string that contains the program that will be translated.
 * The output of this is a list of tokens which shall be passed to a parser for syntactical analysis.
 *
 * @see org.vut.ifje.project.parser.Parser
 */
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
    private final ErrorReporter reporter;
    private final List<Token> tokens = new ArrayList<>();
    private final Cursor cursor = new Cursor();

    /**
     * Constructs and initializes this a scanner with the source program and a reporter
     *
     * @param source   the source program as a string
     * @param reporter the reporter to which lexical errors may be reported
     */
    public Scanner(String source, ErrorReporter reporter) {
        this.iterator = new StringCharacterIterator(source);
        this.reporter = reporter;
    }

    /**
     * Instructs this scanner to split the source program into a list of tokens
     *
     * @return the list of tokens
     */
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
            case '(' -> tokens.add(new Token(TokenType.LEFT_PARENTHESIS, "(", new Cursor(cursor)));
            case ')' -> tokens.add(new Token(TokenType.RIGHT_PARENTHESIS, ")", new Cursor(cursor)));
            case ',' -> tokens.add(new Token(TokenType.COMMA, ",", new Cursor(cursor)));
            case '+' -> tokens.add(number(TokenType.POSITIVE_NUMBER, true));
            case '-' -> tokens.add(number(TokenType.NEGATIVE_NUMBER, true));
            case '/' -> comment();
            case '\n' -> cursor.newline();
            case ' ', '\t', '\r' -> {
            }
            default -> {
                if (isDigit(iterator.current())) {
                    tokens.add(number(TokenType.POSITIVE_NUMBER, false));
                } else if (isAlpha(iterator.current())) {
                    tokens.add(identifier());
                } else {
                    String explanation = String.format("unknown symbol '%c'", iterator.current());
                    reporter.add(new LexicalError(explanation, new Cursor(cursor)));
                }
            }
        }

        iterator.next();
        cursor.advance();
    }

    private void comment() {
        if (iterator.next() != '*') {
            reporter.add(new LexicalError("unknown symbol '/'", cursor));
            return;
        }

        cursor.advance();

        while (!done()) {
            iterator.next();
            cursor.advance();

            if (iterator.current() == '\n') {
                cursor.advance();
            }

            if (iterator.current() == '*' && iterator.next() == '/') {
                cursor.advance();
                break;
            }
        }
    }

    private Token number(TokenType type, boolean tokenContainsSign) {
        StringBuilder lexeme = new StringBuilder();
        Cursor start = new Cursor(cursor);

        if (tokenContainsSign) {
            iterator.next();
            cursor.advance();
        }

        while (isDigit(iterator.current())) {
            lexeme.append(iterator.current());
            iterator.next();
            cursor.advance();
        }

        if (iterator.current() == '.' && isDigit(lookahead())) {
            lexeme.append('.');
            iterator.next();
            cursor.advance();

            do {
                lexeme.append(iterator.current());
                iterator.next();
                cursor.advance();
            } while (isDigit(iterator.current()));
        }

        if (iterator.current() == 'e' && isDigit(lookahead())) {
            lexeme.append('e');
            iterator.next();
            cursor.advance();

            do {
                lexeme.append(iterator.current());
                iterator.next();
                cursor.advance();
            } while (isDigit(iterator.current()));
        }

        // Put the iterator and cursor in the correct position to advance in scanToken()
        iterator.previous();
        cursor.rewind();

        return new Token(type, lexeme.toString(), start);
    }

    private Token identifier() {
        StringBuilder lexeme = new StringBuilder();
        Cursor start = new Cursor(cursor);

        do {
            lexeme.append(iterator.current());
            iterator.next();
            cursor.advance();
        } while (isAlpha(iterator.current()));

        // Put the iterator and cursor in the correct position to advance in scanToken()
        iterator.previous();
        cursor.rewind();

        TokenType type = functions.getOrDefault(lexeme.toString(), TokenType.UNKNOWN);
        if (type == TokenType.UNKNOWN) {
            String explanation = String.format("unknown identifier '%s'", lexeme);
            reporter.add(new LexicalError(explanation, start));
        }

        return new Token(type, lexeme.toString(), start);
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
