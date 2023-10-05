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
import org.vut.ifje.project.scanner.Token;
import org.vut.ifje.project.scanner.TokenType;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {
    @Test void parseNumber() {
        List<Token> tokens = List.of(
                new Token(TokenType.NEGATIVE_NUMBER, "123")
        );

        Expr expected = new NumExpr(new Token(TokenType.NEGATIVE_NUMBER, "123"));

        Visitor<String> visitor = new AstVisitor();

        Parser parser = new Parser(tokens);
        Expr actual = parser.parse();

        assertEquals(expected.accept(visitor), actual.accept(visitor));
    }

    @Test void parseOperation() {
        List<Token> tokens = List.of(
                new Token(TokenType.SUB, "sub"),
                new Token(TokenType.LEFT_PARENTHESIS, "("),
                new Token(TokenType.NEGATIVE_NUMBER, "123"),
                new Token(TokenType.COMMA, ","),
                new Token(TokenType.POSITIVE_NUMBER, "10.42"),
                new Token(TokenType.RIGHT_PARENTHESIS, ")"),
                new Token(TokenType.EOF, "")
        );

        Expr expected = new SubExpr(
                new NumExpr(new Token(TokenType.NEGATIVE_NUMBER, "123")),
                new NumExpr(new Token(TokenType.POSITIVE_NUMBER, "10.42"))
        );

        Visitor<String> visitor = new AstVisitor();

        Parser parser = new Parser(tokens);
        Expr actual = parser.parse();

        assertEquals(expected.accept(visitor), actual.accept(visitor));
    }

    @Test void parseNestedOperations() {
        List<Token> tokens = Arrays.asList(
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

        Expr expected = new AddExpr(
                new NumExpr(new Token(TokenType.POSITIVE_NUMBER, "5")),
                new MulExpr(
                        new NumExpr(new Token(TokenType.POSITIVE_NUMBER, "3")),
                        new SubExpr(
                                new NumExpr(new Token(TokenType.POSITIVE_NUMBER, "10")),
                                new PowExpr(
                                        new NumExpr(new Token(TokenType.POSITIVE_NUMBER, "6")),
                                        new NumExpr(new Token(TokenType.POSITIVE_NUMBER, "4"))
                                )
                        )
                )
        );

        Visitor<String> visitor = new AstVisitor();

        Parser parser = new Parser(tokens);
        Expr actual = parser.parse();

        assertEquals(expected.accept(visitor), actual.accept(visitor));
    }
}
