package org.vut.ifje.project.ast;

import org.vut.ifje.project.ast.expr.Expr;
import org.vut.ifje.project.ast.expr.binary.*;
import org.vut.ifje.project.ast.expr.literal.NumExpr;
import org.vut.ifje.project.scanner.Token;
import org.vut.ifje.project.scanner.TokenType;

public class AstVisitor implements Visitor<String> {
    @Override
    public String visitNumExpr(NumExpr expression) {
        Token token = expression.getToken();
        return token.type() == TokenType.POSITIVE_NUMBER ? token.lexeme() : '-' + token.lexeme();
    }

    @Override
    public String visitAddExpr(AddExpr expression) {
        return visitBinaryExpr(expression);
    }

    @Override
    public String visitSubExpr(SubExpr expression) {
        return visitBinaryExpr(expression);
    }

    @Override
    public String visitMulExpr(MulExpr expression) {
        return visitBinaryExpr(expression);
    }

    @Override
    public String visitDivExpr(DivExpr expression) {
        return visitBinaryExpr(expression);
    }

    @Override
    public String visitModExpr(ModExpr expression) {
        return visitBinaryExpr(expression);
    }

    @Override
    public String visitPowExpr(PowExpr expression) {
        return visitBinaryExpr(expression);
    }

    private String visitBinaryExpr(BinaryExpr expression) {
        return parenthesizeIfNeeded(expression, expression.left()) +
                ' ' + expression.separator() + ' ' +
                parenthesizeIfNeeded(expression, expression.right());
    }

    private boolean needsParenthesis(Expr outer, Expr inner) {
        return inner.precedence().compareTo(outer.precedence()) < 0;
    }

    private String parenthesize(Expr expression) {
        return '(' + expression.accept(this) + ')';
    }

    private String parenthesizeIfNeeded(Expr outer, Expr inner) {
        return needsParenthesis(outer, inner) ? parenthesize(inner) : inner.accept(this);
    }
}
