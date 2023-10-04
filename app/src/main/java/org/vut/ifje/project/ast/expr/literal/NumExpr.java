package org.vut.ifje.project.ast.expr.literal;

import org.vut.ifje.project.ast.expr.Expr;
import org.vut.ifje.project.scanner.Token;

public class NumExpr extends Expr {
    private final Token token;

    public NumExpr(Token token) {
        this.token = token;
    }

    public Token getToken() {
        return token;
    }
}
