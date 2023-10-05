package org.vut.ifje.project.scanner;

import org.junit.jupiter.api.Test;
import org.vut.ifje.project.reporter.Cursor;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

public class ScannerTest {
    @Test void scanPositiveNumberWithSign() {
        String number = "+100";

        List<Token> expected = List.of(
                new Token(TokenType.POSITIVE_NUMBER, "100", new Cursor()),
                new Token(TokenType.EOF, "", new Cursor(1, 5))
        );

        Scanner scanner = new Scanner(number);
        List<Token> actual = scanner.scan();

        assertIterableEquals(expected, actual);
    }

    @Test void scanPositiveNumberWithoutSign() {
        String number = "100";

        List<Token> expected = List.of(
                new Token(TokenType.POSITIVE_NUMBER, "100", new Cursor()),
                new Token(TokenType.EOF, "", new Cursor(1, 4))
        );

        Scanner scanner = new Scanner(number);
        List<Token> actual = scanner.scan();

        assertIterableEquals(expected, actual);
    }

    @Test void scanNegativeNumber() {
        String number = "-100";

        List<Token> expected = List.of(
                new Token(TokenType.NEGATIVE_NUMBER, "100", new Cursor()),
                new Token(TokenType.EOF, "", new Cursor(1, 5))
        );

        Scanner scanner = new Scanner(number);
        List<Token> actual = scanner.scan();

        assertIterableEquals(expected, actual);
    }

    @Test void scanFractionalNumber() {
        String number = "100.00";

        List<Token> expected = List.of(
                new Token(TokenType.POSITIVE_NUMBER, "100.00", new Cursor()),
                new Token(TokenType.EOF, "", new Cursor(1, 7))
        );

        Scanner scanner = new Scanner(number);
        List<Token> actual = scanner.scan();

        assertIterableEquals(expected, actual);
    }

    @Test void scanExponentialNumber() {
        String number = "100e2";

        List<Token> expected = List.of(
                new Token(TokenType.POSITIVE_NUMBER, "100e2", new Cursor()),
                new Token(TokenType.EOF, "", new Cursor(1, 6))
        );

        Scanner scanner = new Scanner(number);
        List<Token> actual = scanner.scan();

        assertIterableEquals(expected, actual);
    }

    @Test void scanPositiveFractionalExponentialNumber() {
        String number = "100.00e2";

        List<Token> expected = List.of(
                new Token(TokenType.POSITIVE_NUMBER, "100.00e2", new Cursor()),
                new Token(TokenType.EOF, "", new Cursor(1, 9))
        );

        Scanner scanner = new Scanner(number);
        List<Token> actual = scanner.scan();

        assertIterableEquals(expected, actual);
    }

    @Test void scanNegativeFractionalExponentialNumber() {
        String number = "-100.00e2";

        List<Token> expected = List.of(
                new Token(TokenType.NEGATIVE_NUMBER, "100.00e2", new Cursor()),
                new Token(TokenType.EOF, "", new Cursor(1, 10))
        );

        Scanner scanner = new Scanner(number);
        List<Token> actual = scanner.scan();

        assertIterableEquals(expected, actual);
    }

    @Test void scanSimpleProgram() {
        String program = "mod(pow(-100.0, 002e12), 3)";

        List<Token> expected = List.of(
                new Token(TokenType.MOD, "mod", new Cursor()),
                new Token(TokenType.LEFT_PARENTHESIS, "(", new Cursor(1, 4)),
                new Token(TokenType.POW, "pow", new Cursor(1, 5)),
                new Token(TokenType.LEFT_PARENTHESIS, "(", new Cursor(1, 8)),
                new Token(TokenType.NEGATIVE_NUMBER, "100.0", new Cursor(1, 9)),
                new Token(TokenType.COMMA, ",", new Cursor(1, 15)),
                new Token(TokenType.POSITIVE_NUMBER, "002e12", new Cursor(1, 17)),
                new Token(TokenType.RIGHT_PARENTHESIS, ")", new Cursor(1, 23)),
                new Token(TokenType.COMMA, ",", new Cursor(1, 24)),
                new Token(TokenType.POSITIVE_NUMBER, "3", new Cursor(1, 26)),
                new Token(TokenType.RIGHT_PARENTHESIS, ")", new Cursor(1, 27)),
                new Token(TokenType.EOF, "", new Cursor(1, 28))
        );

        Scanner scanner = new Scanner(program);
        List<Token> actual = scanner.scan();

        assertIterableEquals(expected, actual);
    }

    @Test void scanProgramWithComments() {
        String program = "add( 1, /* This is 1 + 1 */ 1 )";

        List<Token> expected = List.of(
                new Token(TokenType.ADD, "add", new Cursor()),
                new Token(TokenType.LEFT_PARENTHESIS, "(", new Cursor(1, 4)),
                new Token(TokenType.POSITIVE_NUMBER, "1", new Cursor(1, 6)),
                new Token(TokenType.COMMA, ",", new Cursor(1, 7)),
                new Token(TokenType.POSITIVE_NUMBER, "1", new Cursor(1, 29)),
                new Token(TokenType.RIGHT_PARENTHESIS, ")", new Cursor(1, 31)),
                new Token(TokenType.EOF, "", new Cursor(1, 32))
        );

        Scanner scanner = new Scanner(program);
        List<Token> actual = scanner.scan();

        assertIterableEquals(expected, actual);
    }

    @Test void scanProgramWithNestedOperators() {
        String program = "add(5, mul(3, sub(10, pow(6, 4))))";

        List<Token> expected = Arrays.asList(
                new Token(TokenType.ADD, "add", new Cursor()),
                new Token(TokenType.LEFT_PARENTHESIS, "(", new Cursor(1, 4)),
                new Token(TokenType.POSITIVE_NUMBER, "5", new Cursor(1, 5)),
                new Token(TokenType.COMMA, ",", new Cursor(1, 6)),
                new Token(TokenType.MUL, "mul", new Cursor(1, 8)),
                new Token(TokenType.LEFT_PARENTHESIS, "(", new Cursor(1, 11)),
                new Token(TokenType.POSITIVE_NUMBER, "3", new Cursor(1, 12)),
                new Token(TokenType.COMMA, ",", new Cursor(1, 13)),
                new Token(TokenType.SUB, "sub", new Cursor(1, 15)),
                new Token(TokenType.LEFT_PARENTHESIS, "(", new Cursor(1, 18)),
                new Token(TokenType.POSITIVE_NUMBER, "10", new Cursor(1, 19)),
                new Token(TokenType.COMMA, ",", new Cursor(1, 21)),
                new Token(TokenType.POW, "pow", new Cursor(1, 23)),
                new Token(TokenType.LEFT_PARENTHESIS, "(", new Cursor(1, 26)),
                new Token(TokenType.POSITIVE_NUMBER, "6", new Cursor(1, 27)),
                new Token(TokenType.COMMA, ",", new Cursor(1, 28)),
                new Token(TokenType.POSITIVE_NUMBER, "4", new Cursor(1, 30)),
                new Token(TokenType.RIGHT_PARENTHESIS, ")", new Cursor(1, 31)),
                new Token(TokenType.RIGHT_PARENTHESIS, ")", new Cursor(1, 32)),
                new Token(TokenType.RIGHT_PARENTHESIS, ")", new Cursor(1, 33)),
                new Token(TokenType.RIGHT_PARENTHESIS, ")", new Cursor(1, 34)),
                new Token(TokenType.EOF, "", new Cursor(1, 35))
        );

        Scanner scanner = new Scanner(program);
        List<Token> actual = scanner.scan();

        assertIterableEquals(expected, actual);
    }
}
