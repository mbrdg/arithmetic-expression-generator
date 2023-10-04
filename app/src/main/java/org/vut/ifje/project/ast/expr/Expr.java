package org.vut.ifje.project.ast.expr;

import org.vut.ifje.project.ast.Visitor;

abstract public class Expr {
    public abstract <R> R accept(Visitor<R> visitor);
}
