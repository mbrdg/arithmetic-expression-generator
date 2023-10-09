package org.vut.ifje.project.ast.expr.binary;

import org.vut.ifje.project.ast.expr.Expr;

/**
 * A binary expression is a subclass of expression that holds two other expressions, a left and a right sides.
 * This abstract superclass is the representation of any binary operation.
 */
abstract public class BinaryExpr extends Expr {
    protected final Expr left;
    protected final Expr right;

    /**
     * Constructs and initializes a binary expression with the left and right expressions.
     *
     * @param left  the left side expression
     * @param right the right side expression
     */
    protected BinaryExpr(Expr left, Expr right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Returns the left side expression.
     *
     * @return the left side expression.
     */
    public Expr left() {
        return left;
    }

    /**
     * Returns the right side expression.
     *
     * @return the right side expression.
     */
    public Expr right() {
        return right;
    }

    /**
     * Returns the character that represents the operator
     *
     * @return the character that represents the operator
     */
    public abstract char separator();
}
