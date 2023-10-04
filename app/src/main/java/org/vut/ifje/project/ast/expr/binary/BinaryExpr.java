package org.vut.ifje.project.ast.expr.binary;

import org.vut.ifje.project.ast.expr.Expr;

abstract public class BinaryExpr extends Expr {
    protected final Expr left;
    protected final Expr right;

    protected BinaryExpr(Expr left, Expr right) {
        this.left = left;
        this.right = right;
    }

    public abstract PrecedenceLevel precedence();
    public abstract char separator();
}