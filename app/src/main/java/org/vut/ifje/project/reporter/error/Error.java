package org.vut.ifje.project.reporter.error;

import org.vut.ifje.project.reporter.Cursor;

public abstract class Error {
    protected final String explanation;
    protected final Cursor cursor;

    public Error(String explanation, Cursor cursor) {
        this.explanation = explanation;
        this.cursor = cursor;
    }
}
