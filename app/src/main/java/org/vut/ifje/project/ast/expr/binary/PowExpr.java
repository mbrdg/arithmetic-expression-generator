package org.vut.ifje.project.ast.expr.binary;

import org.vut.ifje.project.ast.expr.Expr;

public class PowExpr extends BinaryExpr {
    public PowExpr(Expr left, Expr right) {
        super(left, right);
    }

    @Override
    public PrecedenceLevel precedence() {
        return PrecedenceLevel.HIGH;
    }

    @Override
    public char separator() {
        return '^';
    }
}
