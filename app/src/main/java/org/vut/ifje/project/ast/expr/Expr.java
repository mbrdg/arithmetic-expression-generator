package org.vut.ifje.project.ast.expr;

import org.vut.ifje.project.ast.Visitor;
import org.vut.ifje.project.ast.expr.binary.PrecedenceLevel;

/**
 * An expression is the representation of an AST.
 * This class is only the abstract superclass for all objects that store an expression.
 * The actual storage representation of the expression is left to the subclass.
 */
abstract public class Expr {
    /**
     * Returns the precedence level of an expression
     *
     * @return the precedence level of an expression
     */
    public abstract PrecedenceLevel precedence();

    /**
     * @param visitor the visitor that will visit this expression
     * @param <R>     the return type of the visitor
     * @return the object of type <code>R</code> returned by the visitor
     */
    public abstract <R> R accept(Visitor<R> visitor);
}
