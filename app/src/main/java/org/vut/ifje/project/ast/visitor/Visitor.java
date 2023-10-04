package org.vut.ifje.project.ast.visitor;

import org.vut.ifje.project.ast.expr.binary.*;
import org.vut.ifje.project.ast.expr.literal.NumExpr;

public interface Visitor<R> {
    R visitNumExpr(NumExpr expression);
    R visitAddExpr(AddExpr expression);
    R visitSubExpr(SubExpr expression);
    R visitMulExpr(MulExpr expression);
    R visitDivExpr(DivExpr expression);
    R visitModExpr(ModExpr expression);
    R visitPowExpr(PowExpr expression);
}
