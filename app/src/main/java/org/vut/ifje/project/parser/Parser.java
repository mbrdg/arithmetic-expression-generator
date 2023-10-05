package org.vut.ifje.project.parser;

import org.vut.ifje.project.ast.expr.Expr;
import org.vut.ifje.project.ast.expr.binary.*;
import org.vut.ifje.project.ast.expr.literal.NumExpr;
import org.vut.ifje.project.parser.arg.Arg;
import org.vut.ifje.project.parser.arg.ArgPosition;
import org.vut.ifje.project.reporter.Cursor;
import org.vut.ifje.project.reporter.ErrorReporter;
import org.vut.ifje.project.reporter.error.SyntaxError;
import org.vut.ifje.project.scanner.Token;
import org.vut.ifje.project.scanner.TokenType;

import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

public class Parser {
    private final ListIterator<Token> iterator;
    private final ErrorReporter reporter = new ErrorReporter();

    public Parser(List<Token> tokens) {
        this.iterator = tokens.listIterator();
    }

    public Optional<? extends Expr> parse() {
        if (!iterator.hasNext()) {
            reporter.add(new SyntaxError("no token found", new Cursor()));
            return Optional.empty();
        }

        Optional<? extends Expr> root = Optional.empty();
        Token token = iterator.next();

        switch (token.type()) {
            case ADD, SUB, MUL, DIV, MOD, POW -> root = function(token);
            case POSITIVE_NUMBER, NEGATIVE_NUMBER -> root = Optional.of(number(token));
            default -> {
                String explanation = String.format("unexpected token of type %s", token.type());
                reporter.add(new SyntaxError(explanation, token.cursor()));
            }
        }

        if (!iterator.hasNext()) {
            reporter.add(new SyntaxError("no token found", new Cursor()));
            return Optional.empty();
        }

        Token eof = iterator.next();
        if (eof.type() != TokenType.EOF) {
            reporter.add(new SyntaxError(
                    String.format("expected token of type EOF but found %s", eof.type()),
                    "concatenation of expressions is not supported, consider removing all but the first expression",
                    eof.cursor()
            ));
            return Optional.empty();
        }

        return root;
    }

    private NumExpr number(Token token) {
        return new NumExpr(token);
    }

    private Optional<BinaryExpr> function(Token token) {
        if (!iterator.hasNext() || iterator.next().type() != TokenType.LEFT_PARENTHESIS) {
            reporter.add(new SyntaxError(
                    "missing '(' after function identifier",
                    new Cursor(token.cursor().line(), token.cursor().column() + token.lexeme().length())
            ));
        }

        Arg first = new Arg(iterator.next(), ArgPosition.FIRST);
        argument(first);

        if (!iterator.hasNext() || iterator.next().type() != TokenType.COMMA) {
            Token previous = iterator.previous();
            reporter.add(new SyntaxError(
                    "missing ',' between function arguments",
                    new Cursor(previous.cursor().line(), previous.cursor().column() + previous.lexeme().length())
            ));

            // Restore back the correct position after the rewind above
            iterator.next();
        }

        Arg second = new Arg(iterator.next(), ArgPosition.SECOND);
        argument(second);

        if (!iterator.hasNext() || iterator.next().type() != TokenType.RIGHT_PARENTHESIS) {
            Token previous = iterator.previous();
            reporter.add(new SyntaxError(
                    "missing ')' after argument list",
                    new Cursor(previous.cursor().line(), previous.cursor().column() + previous.lexeme().length())
            ));

            // Restore back the correct position after the rewind above
            iterator.next();
        }

        // Abort if something went wrong with deeper expressions
        if (first.expr().isEmpty() || second.expr().isEmpty()) {
            return Optional.empty();
        }

        switch (token.type()) {
            case ADD -> { return Optional.of(new AddExpr(first.expr().get(), second.expr().get())); }
            case SUB -> { return Optional.of(new SubExpr(first.expr().get(), second.expr().get())); }
            case MUL -> { return Optional.of(new MulExpr(first.expr().get(), second.expr().get())); }
            case DIV -> { return Optional.of(new DivExpr(first.expr().get(), second.expr().get())); }
            case MOD -> { return Optional.of(new ModExpr(first.expr().get(), second.expr().get())); }
            case POW -> { return Optional.of(new PowExpr(first.expr().get(), second.expr().get())); }
            default -> reporter.add(new SyntaxError(
                    String.format("invalid token of type %s as a function name", token.type()),
                    "did you mean one of the following: 'add', 'sub', 'mul', 'div', 'mod' or 'pow'?",
                    token.cursor()
            ));
        }

        return Optional.empty();
    }

    private void argument(Arg argument) {
        switch (argument.token().type()) {
            case ADD, SUB, MUL, DIV, MOD, POW -> argument.setExpression(function(argument.token()).orElse(null));
            case POSITIVE_NUMBER, NEGATIVE_NUMBER -> argument.setExpression(number(argument.token()));
            default -> reporter.add(new SyntaxError(
                    String.format(
                            "invalid token of type %s as the %s function argument",
                            argument.token().type(),
                            argument.position()
                    ),
                    argument.token().cursor()
            ));
        }
    }
}
