package org.vut.ifje.project.ast.expr;

import org.vut.ifje.project.ast.Visitor;
import org.vut.ifje.project.ast.expr.binary.PrecedenceLevel;

abstract public class Expr {
    public abstract PrecedenceLevel precedence();
    public abstract <R> R accept(Visitor<R> visitor);
}
