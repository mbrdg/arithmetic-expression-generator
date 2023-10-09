package org.vut.ifje.project.ast.expr.binary;

import org.vut.ifje.project.ast.expr.Expr;
import org.vut.ifje.project.ast.Visitor;

/**
 * A subtraction expression represents the difference between two expressions.
 */
public class SubExpr extends BinaryExpr {
    /**
     * Constructs and initializes a subtraction expression with the left and right side expressions.
     *
     * @param left  the left side expression
     * @param right the right side expression
     */
    public SubExpr(Expr left, Expr right) {
        super(left, right);
    }

    @Override
    public PrecedenceLevel precedence() {
        return PrecedenceLevel.LOW;
    }

    @Override
    public char separator() {
        return '-';
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visitSubExpr(this);
    }
}
