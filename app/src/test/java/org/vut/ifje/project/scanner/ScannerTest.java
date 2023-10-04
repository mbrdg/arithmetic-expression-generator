package org.vut.ifje.project.scanner;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

public class ScannerTest {
    @Test void scanTest() {
        String mock = "add(5, mul(3, sub(10, pow(6, 4))))";
        
        List<Token> expected = Arrays.asList(
                new Token(TokenType.ADD, "add"),
                new Token(TokenType.LEFT_PARENTHESIS, "("),
                new Token(TokenType.POSITIVE_NUMBER, "5"),
                new Token(TokenType.COMMA, ","),
                new Token(TokenType.MUL, "mul"),
                new Token(TokenType.LEFT_PARENTHESIS, "("),
                new Token(TokenType.SUB, "sub"),
                new Token(TokenType.LEFT_PARENTHESIS, "("),
                new Token(TokenType.POSITIVE_NUMBER, "10"),
                new Token(TokenType.COMMA, ","),
                new Token(TokenType.POW, "pow"),
                new Token(TokenType.LEFT_PARENTHESIS, "("),
                new Token(TokenType.POSITIVE_NUMBER, "6"),
                new Token(TokenType.COMMA, ","),
                new Token(TokenType.POSITIVE_NUMBER, "4"),
                new Token(TokenType.RIGHT_PARENTHESIS, ")"),
                new Token(TokenType.RIGHT_PARENTHESIS, ")"),
                new Token(TokenType.RIGHT_PARENTHESIS, ")"),
                new Token(TokenType.RIGHT_PARENTHESIS, ")"),
                new Token(TokenType.EOF, "")
        );

        Scanner scanner = new Scanner(mock);
        List<Token> actual = scanner.scan();

        assertIterableEquals(expected, actual);
    }
}
