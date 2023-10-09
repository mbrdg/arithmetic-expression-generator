package org.vut.ifje.project.ast;

import org.vut.ifje.project.ast.expr.binary.*;
import org.vut.ifje.project.ast.expr.literal.NumExpr;

/**
 * A visitor is a common design pattern found in software engineering.
 * A visitor is a way to implement similar versions of the same algorithm to different types.
 *
 * @param <R> the return type
 */
public interface Visitor<R> {
    R visitNumExpr(NumExpr expression);

    R visitAddExpr(AddExpr expression);

    R visitSubExpr(SubExpr expression);

    R visitMulExpr(MulExpr expression);

    R visitDivExpr(DivExpr expression);

    R visitModExpr(ModExpr expression);

    R visitPowExpr(PowExpr expression);
}
