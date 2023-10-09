package org.vut.ifje.project.ast.expr.binary;

import org.vut.ifje.project.ast.expr.Expr;
import org.vut.ifje.project.ast.Visitor;

/**
 * A power expression represents the base to the power of the exponent.
 */
public class PowExpr extends BinaryExpr {
    /**
     * Constructs and initializes a power expression with the base and exponent expressions.
     *
     * @param base     the base expression
     * @param exponent the exponent expression
     */
    public PowExpr(Expr base, Expr exponent) {
        super(base, exponent);
    }

    @Override
    public PrecedenceLevel precedence() {
        return PrecedenceLevel.HIGH;
    }

    @Override
    public char separator() {
        return '^';
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visitPowExpr(this);
    }
}
