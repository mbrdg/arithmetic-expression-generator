package org.vut.ifje.project.parser.arg;

import org.vut.ifje.project.ast.expr.Expr;
import org.vut.ifje.project.scanner.Token;

import java.util.Optional;

/**
 * An argument represents a concrete value for a parameter of a function.
 * In this context arguments are inserted in binary operations as operands.
 */
public class Arg {
    private final Token token;
    private final ArgPosition position;
    private Expr expression = null;

    /**
     * Constructs and initializes an argument with a token and position (a.k.a. order) in the argument list.
     *
     * @param token    the token that represents the argument
     * @param position the position in which the argument is part of argument list
     */
    public Arg(Token token, ArgPosition position) {
        this.token = token;
        this.position = position;
    }

    /**
     * Returns the token that represents the argument.
     *
     * @return the token that represents the argument
     */
    public Token token() {
        return token;
    }

    /**
     * Returns the position (a.k.a. order) in the argument list.
     *
     * @return the position (a.k.a. order) in the argument list
     */
    public ArgPosition position() {
        return position;
    }

    /**
     * Returns the expression object that represents this argument.
     *
     * @return the expression object that represents this argument
     */
    public Optional<? extends Expr> expr() {
        return Optional.ofNullable(expression);
    }

    /**
     * Sets the <code>Expr</code> object of this argument
     *
     * @param expression the <code>Expr</code> object of this argument
     */
    public void setExpression(Expr expression) {
        this.expression = expression;
    }
}
