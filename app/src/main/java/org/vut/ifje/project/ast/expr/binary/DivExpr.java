package org.vut.ifje.project.ast.expr.binary;

import org.vut.ifje.project.ast.expr.Expr;

public class DivExpr extends BinaryExpr {
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
}
