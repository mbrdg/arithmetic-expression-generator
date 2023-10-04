package org.vut.ifje.project.ast.expr.literal;

import org.vut.ifje.project.ast.expr.Expr;
import org.vut.ifje.project.ast.Visitor;
import org.vut.ifje.project.ast.expr.binary.PrecedenceLevel;
import org.vut.ifje.project.scanner.Token;

public class NumExpr extends Expr {
    private final Token token;

    public NumExpr(Token token) {
        this.token = token;
    }

    public Token getToken() {
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
