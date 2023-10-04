package org.vut.ifje.project.ast.expr.binary;

import org.vut.ifje.project.ast.expr.Expr;
import org.vut.ifje.project.ast.visitor.Visitor;

public class AddExpr extends BinaryExpr {
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
