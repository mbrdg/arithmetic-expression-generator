package org.vut.ifje.project.scanner;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

public class ScannerTest {
    @Test void scanPositiveNumberWithSign() {
        String number = "+100";

        List<Token> expected = List.of(
                new Token(TokenType.POSITIVE_NUMBER, "100"),
                new Token(TokenType.EOF, "")
        );

        Scanner scanner = new Scanner(number);
        List<Token> actual = scanner.scan();

        assertIterableEquals(expected, actual);
    }

    @Test void scanPositiveNumberWithoutSign() {
        String number = "100";

        List<Token> expected = List.of(
                new Token(TokenType.POSITIVE_NUMBER, "100"),
                new Token(TokenType.EOF, "")
        );

        Scanner scanner = new Scanner(number);
        List<Token> actual = scanner.scan();

        assertIterableEquals(expected, actual);
    }

    @Test void scanNegativeNumber() {
        String number = "-100";

        List<Token> expected = List.of(
                new Token(TokenType.NEGATIVE_NUMBER, "100"),
                new Token(TokenType.EOF, "")
        );

        Scanner scanner = new Scanner(number);
        List<Token> actual = scanner.scan();

        assertIterableEquals(expected, actual);
    }

    @Test void scanFractionalNumber() {
        String number = "100.00";

        List<Token> expected = List.of(
                new Token(TokenType.POSITIVE_NUMBER, "100.00"),
                new Token(TokenType.EOF, "")
        );

        Scanner scanner = new Scanner(number);
        List<Token> actual = scanner.scan();

        assertIterableEquals(expected, actual);
    }

    @Test void scanExponentialNumber() {
        String number = "100e2";

        List<Token> expected = List.of(
                new Token(TokenType.POSITIVE_NUMBER, "100e2"),
                new Token(TokenType.EOF, "")
        );

        Scanner scanner = new Scanner(number);
        List<Token> actual = scanner.scan();

        assertIterableEquals(expected, actual);
    }

    @Test void scanPositiveFractionalExponentialNumber() {
        String number = "100.00e2";

        List<Token> expected = List.of(
                new Token(TokenType.POSITIVE_NUMBER, "100.00e2"),
                new Token(TokenType.EOF, "")
        );

        Scanner scanner = new Scanner(number);
        List<Token> actual = scanner.scan();

        assertIterableEquals(expected, actual);
    }

    @Test void scanNegativeFractionalExponentialNumber() {
        String number = "-100.00e2";

        List<Token> expected = List.of(
                new Token(TokenType.NEGATIVE_NUMBER, "100.00e2"),
                new Token(TokenType.EOF, "")
        );

        Scanner scanner = new Scanner(number);
        List<Token> actual = scanner.scan();

        assertIterableEquals(expected, actual);
    }

    @Test void scanSimpleProgram() {
        String program = "mod(pow(-100.0, 002e12), 3)";

        List<Token> expected = List.of(
                new Token(TokenType.MOD, "mod"),
                new Token(TokenType.LEFT_PARENTHESIS, "("),
                new Token(TokenType.POW, "pow"),
                new Token(TokenType.LEFT_PARENTHESIS, "("),
                new Token(TokenType.NEGATIVE_NUMBER, "100.0"),
                new Token(TokenType.COMMA, ","),
                new Token(TokenType.POSITIVE_NUMBER, "002e12"),
                new Token(TokenType.RIGHT_PARENTHESIS, ")"),
                new Token(TokenType.COMMA, ","),
                new Token(TokenType.POSITIVE_NUMBER, "3"),
                new Token(TokenType.RIGHT_PARENTHESIS, ")"),
                new Token(TokenType.EOF, "")
        );

        Scanner scanner = new Scanner(program);
        List<Token> actual = scanner.scan();

        assertIterableEquals(expected, actual);
    }

    @Test void scanProgramWithComments() {
        String program = "add( 1, /* This is 1 + 1 */ 1 )";

        List<Token> expected = List.of(
                new Token(TokenType.ADD, "add"),
                new Token(TokenType.LEFT_PARENTHESIS, "("),
                new Token(TokenType.POSITIVE_NUMBER, "1"),
                new Token(TokenType.COMMA, ","),
                new Token(TokenType.POSITIVE_NUMBER, "1"),
                new Token(TokenType.RIGHT_PARENTHESIS, ")"),
                new Token(TokenType.EOF, "")
        );

        Scanner scanner = new Scanner(program);
        List<Token> actual = scanner.scan();

        assertIterableEquals(expected, actual);
    }

    @Test void scanProgramWithNestedOperators() {
        String program = "add(5, mul(3, sub(10, pow(6, 4))))";

        List<Token> expected = Arrays.asList(
                new Token(TokenType.ADD, "add"),
                new Token(TokenType.LEFT_PARENTHESIS, "("),
                new Token(TokenType.POSITIVE_NUMBER, "5"),
                new Token(TokenType.COMMA, ","),
                new Token(TokenType.MUL, "mul"),
                new Token(TokenType.LEFT_PARENTHESIS, "("),
                new Token(TokenType.POSITIVE_NUMBER, "3"),
                new Token(TokenType.COMMA, ","),
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

        Scanner scanner = new Scanner(program);
        List<Token> actual = scanner.scan();

        assertIterableEquals(expected, actual);
    }
}
