package org.vut.ifje.project.ast;

import org.junit.jupiter.api.Test;
import org.vut.ifje.project.ast.expr.Expr;
import org.vut.ifje.project.ast.expr.binary.AddExpr;
import org.vut.ifje.project.ast.expr.binary.MulExpr;
import org.vut.ifje.project.ast.expr.literal.NumExpr;
import org.vut.ifje.project.reporter.Cursor;
import org.vut.ifje.project.scanner.Token;
import org.vut.ifje.project.scanner.TokenType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AstVisitorTest {
    @Test
    void visitNumExpr() {
        List<String> expected = List.of("100", "-100");

        List<Expr> expressions = List.of(
                new NumExpr(new Token(TokenType.POSITIVE_NUMBER, "100", new Cursor())),
                new NumExpr(new Token(TokenType.NEGATIVE_NUMBER, "100", new Cursor()))
        );

        AstVisitor visitor = new AstVisitor();
        List<String> actual = expressions.stream()
                .map(expr -> expr.accept(visitor))
                .toList();

        assertIterableEquals(actual, expected);
    }

    @Test
    void visitSimpleBinaryExpr() {
        String expected = "1 + 1";

        Expr expression = new AddExpr(
                new NumExpr(new Token(TokenType.POSITIVE_NUMBER, "1", new Cursor())),
                new NumExpr(new Token(TokenType.POSITIVE_NUMBER, "1", new Cursor()))
        );

        AstVisitor visitor = new AstVisitor();
        String actual = expression.accept(visitor);

        assertEquals(expected, actual);
    }

    @Test
    void visitNestedBinaryExpr() {
        String expected = "1 + 1 * 3 + 2";

        Expr expression = new AddExpr(
                new NumExpr(new Token(TokenType.POSITIVE_NUMBER, "1", new Cursor())),
                new AddExpr(
                        new MulExpr(
                                new NumExpr(new Token(TokenType.POSITIVE_NUMBER, "1", new Cursor())),
                                new NumExpr(new Token(TokenType.POSITIVE_NUMBER, "3", new Cursor()))
                        ),
                        new NumExpr(new Token(TokenType.POSITIVE_NUMBER, "2", new Cursor()))
                )
        );

        AstVisitor visitor = new AstVisitor();
        String actual = expression.accept(visitor);

        assertEquals(expected, actual);
    }

    @Test
    void visitNestedBinaryExprWithPrecedence() {
        String expected = "(1 + 1) * (3 + 2)";

        Expr expression = new MulExpr(
                new AddExpr(
                        new NumExpr(new Token(TokenType.POSITIVE_NUMBER, "1", new Cursor())),
                        new NumExpr(new Token(TokenType.POSITIVE_NUMBER, "1", new Cursor()))
                ),
                new AddExpr(
                        new NumExpr(new Token(TokenType.POSITIVE_NUMBER, "3", new Cursor())),
                        new NumExpr(new Token(TokenType.POSITIVE_NUMBER, "2", new Cursor()))
                )
        );

        AstVisitor visitor = new AstVisitor();
        String actual = expression.accept(visitor);

        assertEquals(expected, actual);
    }
}
