package org.vut.ifje.project.ast.expr.literal;

import org.vut.ifje.project.ast.expr.Expr;
import org.vut.ifje.project.ast.Visitor;
import org.vut.ifje.project.ast.expr.binary.PrecedenceLevel;
import org.vut.ifje.project.scanner.Token;

/**
 * A numeric expression holds a single token that represents a numeric literal.
 */
public class NumExpr extends Expr {
    private final Token token;

    /**
     * Constructs and initializes a numeric expression with a numeric literal token.
     *
     * @param token the numeric literal token
     */
    public NumExpr(Token token) {
        this.token = token;
    }

    /**
     * Returns the numeric literal token.
     *
     * @return the numeric literal token
     */
    public Token token() {
        return token;
    }

    @Override
    public PrecedenceLevel precedence() {
        return PrecedenceLevel.NONE;
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visitNumExpr(this);
    }
}
