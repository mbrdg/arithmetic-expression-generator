package org.vut.ifje.project.parser;

import org.vut.ifje.project.ast.expr.Expr;
import org.vut.ifje.project.ast.expr.binary.*;
import org.vut.ifje.project.ast.expr.literal.NumExpr;
import org.vut.ifje.project.reporter.Error;
import org.vut.ifje.project.reporter.ErrorType;
import org.vut.ifje.project.reporter.Reporter;
import org.vut.ifje.project.scanner.Token;
import org.vut.ifje.project.scanner.TokenType;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Parser {
    private final ListIterator<Token> iterator;
    private final Reporter reporter = new Reporter();

    public Parser(List<Token> tokens) {
        this.iterator = tokens.listIterator();
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
                    // TODO: improve error api
                    String explanation = String.format("unexpected token of type %s", token.type());
                    reporter.add(new Error(ErrorType.SYNTACTICAL, explanation, -1));
                }
            }

        }

        return tree;
    }

    private NumExpr number(Token token) {
        return new NumExpr(token);
    }

    private BinaryExpr function(Token token) {
        if (!iterator.hasNext()) {
            reporter.add(new Error(ErrorType.SYNTACTICAL, "", -1));
        }

        if (iterator.next().type() != TokenType.LEFT_PARENTHESIS) {
            reporter.add(new Error(ErrorType.SYNTACTICAL, "", -1));
        }

        if (!iterator.hasNext()) {
            reporter.add(new Error(ErrorType.SYNTACTICAL, "", -1));
        }

        List<Expr> arguments = new ArrayList<>();

        Token firstArgument = iterator.next();
        switch (firstArgument.type()) {
            case ADD, SUB, MUL, DIV, MOD, POW -> arguments.add(function(firstArgument));
            case POSITIVE_NUMBER, NEGATIVE_NUMBER -> arguments.add(number(firstArgument));
            default -> reporter.add(new Error(ErrorType.SYNTACTICAL, "", -1));
        }

        if (!iterator.hasNext()) {
            reporter.add(new Error(ErrorType.SYNTACTICAL, "", -1));
        }

        if (iterator.next().type() != TokenType.COMMA) {
            reporter.add(new Error(ErrorType.SYNTACTICAL, "", -1));
        }

        Token secondArgument = iterator.next();
        switch (secondArgument.type()) {
            case ADD, SUB, MUL, DIV, MOD, POW -> arguments.add(function(secondArgument));
            case POSITIVE_NUMBER, NEGATIVE_NUMBER -> arguments.add(number(secondArgument));
            default -> reporter.add(new Error(ErrorType.SYNTACTICAL, "", -1));
        }

        if (!iterator.hasNext()) {
            reporter.add(new Error(ErrorType.SYNTACTICAL, "", -1));
        }

        if (iterator.next().type() != TokenType.RIGHT_PARENTHESIS) {
            reporter.add(new Error(ErrorType.SYNTACTICAL, "", -1));
        }

        BinaryExpr expression = null;
        switch (token.type()) {
            case ADD -> expression = new AddExpr(arguments.get(0), arguments.get(1));
            case SUB -> expression = new SubExpr(arguments.get(0), arguments.get(1));
            case MUL -> expression = new MulExpr(arguments.get(0), arguments.get(1));
            case DIV -> expression = new DivExpr(arguments.get(0), arguments.get(1));
            case MOD -> expression = new ModExpr(arguments.get(0), arguments.get(1));
            case POW -> expression = new PowExpr(arguments.get(0), arguments.get(1));
            default -> reporter.add(new Error(ErrorType.SYNTACTICAL, "", -1));
        }

        return expression;
    }
}
