package org.vut.ifje.project.ast.expr.binary;

import org.vut.ifje.project.ast.expr.Expr;
import org.vut.ifje.project.ast.Visitor;

/**
 * A division expression represents the quotient between two expressions.
 */
public class DivExpr extends BinaryExpr {
    /**
     * Constructs and initializes a division expression with the left and right side expressions.
     *
     * @param left  the left side expression
     * @param right the right side expression
     */
    public DivExpr(Expr left, Expr right) {
        super(left, right);
    }

    @Override
    public PrecedenceLevel precedence() {
        return PrecedenceLevel.MEDIUM;
    }

    @Override
    public char separator() {
        return '/';
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visitDivExpr(this);
    }
}
