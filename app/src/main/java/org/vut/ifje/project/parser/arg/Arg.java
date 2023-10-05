package org.vut.ifje.project.parser.arg;

import org.vut.ifje.project.ast.expr.Expr;
import org.vut.ifje.project.scanner.Token;

import java.util.Optional;


public class Arg {
    private final Token token;
    private final ArgPosition position;
    private Expr expression = null;

    public Arg(Token token, ArgPosition position) {
        this.token = token;
        this.position = position;
    }

    public Token token() {
        return token;
    }

    public ArgPosition position() {
        return position;
    }

    public Optional<? extends Expr> expr() {
        return Optional.ofNullable(expression);
    }

    public void setExpression(Expr expression) {
        this.expression = expression;
    }
}
