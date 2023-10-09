package org.vut.ifje.project.ast.expr.binary;

import org.vut.ifje.project.ast.expr.Expr;
import org.vut.ifje.project.ast.Visitor;

/**
 * An addition expression represents the sum between two expressions.
 */
public class AddExpr extends BinaryExpr {
    /**
     * Constructs and initializes an addition expression with the left and right side expressions.
     *
     * @param left  the left side expression
     * @param right the right side expression
     */
    public AddExpr(Expr left, Expr right) {
        super(left, right);
    }

    @Override
    public PrecedenceLevel precedence() {
        return PrecedenceLevel.LOW;
    }

    @Override
    public char separator() {
        return '+';
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visitAddExpr(this);
    }
}
