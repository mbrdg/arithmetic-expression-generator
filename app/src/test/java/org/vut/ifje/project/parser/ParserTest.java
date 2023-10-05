package org.vut.ifje.project.parser;

import org.junit.jupiter.api.Test;
import org.vut.ifje.project.ast.AstVisitor;
import org.vut.ifje.project.ast.Visitor;
import org.vut.ifje.project.ast.expr.Expr;
import org.vut.ifje.project.ast.expr.binary.AddExpr;
import org.vut.ifje.project.ast.expr.binary.MulExpr;
import org.vut.ifje.project.ast.expr.binary.PowExpr;
import org.vut.ifje.project.ast.expr.binary.SubExpr;
import org.vut.ifje.project.ast.expr.literal.NumExpr;
import org.vut.ifje.project.reporter.Cursor;
import org.vut.ifje.project.reporter.ErrorReporter;
import org.vut.ifje.project.scanner.Token;
import org.vut.ifje.project.scanner.TokenType;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {
    @Test void parseNumber() {
        List<Token> tokens = List.of(
                new Token(TokenType.NEGATIVE_NUMBER, "123", new Cursor()),
                new Token(TokenType.EOF, "", new Cursor(1, 4))
        );

        Expr expected = new NumExpr(new Token(TokenType.NEGATIVE_NUMBER, "123", new Cursor()));

        Visitor<String> visitor = new AstVisitor();
        ErrorReporter reporter = new ErrorReporter();

        Parser parser = new Parser(tokens, reporter);
        Optional<? extends Expr> actual = parser.parse();

        assertTrue(actual.isPresent());
        assertFalse(reporter.hasErrors());
        assertEquals(expected.accept(visitor), actual.get().accept(visitor));
    }

    @Test void parseOperation() {
        List<Token> tokens = List.of(
                new Token(TokenType.SUB, "sub", new Cursor()),
                new Token(TokenType.LEFT_PARENTHESIS, "(", new Cursor()),
                new Token(TokenType.NEGATIVE_NUMBER, "123", new Cursor()),
                new Token(TokenType.COMMA, ",", new Cursor()),
                new Token(TokenType.POSITIVE_NUMBER, "10.42", new Cursor()),
                new Token(TokenType.RIGHT_PARENTHESIS, ")", new Cursor()),
                new Token(TokenType.EOF, "", new Cursor())
        );

        Expr expected = new SubExpr(
                new NumExpr(new Token(TokenType.NEGATIVE_NUMBER, "123", new Cursor())),
                new NumExpr(new Token(TokenType.POSITIVE_NUMBER, "10.42", new Cursor()))
        );

        Visitor<String> visitor = new AstVisitor();
        ErrorReporter reporter = new ErrorReporter();

        Parser parser = new Parser(tokens, reporter);
        Optional<? extends Expr> actual = parser.parse();

        assertTrue(actual.isPresent());
        assertFalse(reporter.hasErrors());
        assertEquals(expected.accept(visitor), actual.get().accept(visitor));
    }

    @Test void parseNestedOperations() {
        List<Token> tokens = Arrays.asList(
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

        Expr expected = new AddExpr(
                new NumExpr(new Token(TokenType.POSITIVE_NUMBER, "5", new Cursor())),
                new MulExpr(
                        new NumExpr(new Token(TokenType.POSITIVE_NUMBER, "3", new Cursor())),
                        new SubExpr(
                                new NumExpr(new Token(TokenType.POSITIVE_NUMBER, "10", new Cursor())),
                                new PowExpr(
                                        new NumExpr(new Token(TokenType.POSITIVE_NUMBER, "6", new Cursor())),
                                        new NumExpr(new Token(TokenType.POSITIVE_NUMBER, "4", new Cursor()))
                                )
                        )
                )
        );

        Visitor<String> visitor = new AstVisitor();
        ErrorReporter reporter = new ErrorReporter();

        Parser parser = new Parser(tokens, reporter);
        Optional<? extends Expr> actual = parser.parse();

        assertTrue(actual.isPresent());
        assertFalse(reporter.hasErrors());
        assertEquals(expected.accept(visitor), actual.get().accept(visitor));
    }
}
