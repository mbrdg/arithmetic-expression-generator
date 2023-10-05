package org.vut.ifje.project.parser;

import org.vut.ifje.project.ast.expr.Expr;
import org.vut.ifje.project.ast.expr.binary.*;
import org.vut.ifje.project.ast.expr.literal.NumExpr;
import org.vut.ifje.project.reporter.Cursor;
import org.vut.ifje.project.reporter.ErrorReporter;
import org.vut.ifje.project.reporter.error.SyntaxError;
import org.vut.ifje.project.scanner.Token;
import org.vut.ifje.project.scanner.TokenType;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Parser {
    private final ListIterator<Token> iterator;
    private final ErrorReporter reporter;

    public Parser(List<Token> tokens, ErrorReporter reporter) {
        this.iterator = tokens.listIterator();
        this.reporter = reporter;
    }

    public Expr parse() {
        // TODO: use optional types instead of nulls
        Expr tree = null;

        while (iterator.hasNext()) {
            Token token = iterator.next();
            switch (token.type()) {
                case ADD, SUB, MUL, DIV, MOD, POW -> tree = function(token);
                case POSITIVE_NUMBER, NEGATIVE_NUMBER -> tree = number(token);
                case EOF -> {}
                default -> {
                    String explanation = String.format("unexpected token of type %s", token.type());
                    reporter.add(new SyntaxError(explanation, token.cursor()));
                }
            }

        }

        return tree;
    }

    private NumExpr number(Token token) {
        return new NumExpr(token);
    }

    private BinaryExpr function(Token token) {
        if (!iterator.hasNext() || iterator.next().type() != TokenType.LEFT_PARENTHESIS) {
            reporter.add(new SyntaxError(
                    "missing '(' after function identifier",
                    new Cursor(token.cursor().line(), token.cursor().column() + token.lexeme().length())
            ));
        }

        List<Expr> arguments = new ArrayList<>();

        Token firstArgument = iterator.next();
        switch (firstArgument.type()) {
            case ADD, SUB, MUL, DIV, MOD, POW -> arguments.add(function(firstArgument));
            case POSITIVE_NUMBER, NEGATIVE_NUMBER -> arguments.add(number(firstArgument));
            default -> reporter.add(new SyntaxError(
                    String.format("invalid token of type %s as 1st function argument", firstArgument.type()),
                    firstArgument.cursor()
            ));
        }

        if (!iterator.hasNext() || iterator.next().type() != TokenType.COMMA) {
            Token previous = iterator.previous();
            reporter.add(new SyntaxError(
                    "missing ',' between function arguments",
                    new Cursor(previous.cursor().line(), previous.cursor().column() + previous.lexeme().length())
            ));

            // Restore back the correct position after the rewind above
            iterator.next();
        }

        Token secondArgument = iterator.next();
        switch (secondArgument.type()) {
            case ADD, SUB, MUL, DIV, MOD, POW -> arguments.add(function(secondArgument));
            case POSITIVE_NUMBER, NEGATIVE_NUMBER -> arguments.add(number(secondArgument));
            default -> reporter.add(new SyntaxError(
                    String.format("invalid token of type %s as 2nd function argument", secondArgument.type()),
                    firstArgument.cursor()
            ));
        }

        if (!iterator.hasNext() || iterator.next().type() != TokenType.RIGHT_PARENTHESIS) {
            Token previous = iterator.previous();
            reporter.add(new SyntaxError(
                    "missing ')' after argument list",
                    new Cursor(previous.cursor().line(), previous.cursor().column() + previous.lexeme().length())
            ));

            // Restore back the correct position after the rewind above
            iterator.next();
        }


        BinaryExpr expression = null;
        switch (token.type()) {
            case ADD -> expression = new AddExpr(arguments.get(0), arguments.get(1));
            case SUB -> expression = new SubExpr(arguments.get(0), arguments.get(1));
            case MUL -> expression = new MulExpr(arguments.get(0), arguments.get(1));
            case DIV -> expression = new DivExpr(arguments.get(0), arguments.get(1));
            case MOD -> expression = new ModExpr(arguments.get(0), arguments.get(1));
            case POW -> expression = new PowExpr(arguments.get(0), arguments.get(1));
            default -> reporter.add(new SyntaxError(
                    String.format("invalid token of type %s as a function name", token.type()),
                    "did you mean one of the following: 'add', 'sub', 'mul', 'div', 'mod' or 'pow'?",
                    token.cursor()
            ));
        }

        return expression;
    }
}
