package org.vut.ifje.project.reporter.error;

import org.vut.ifje.project.reporter.Cursor;

public class SyntaxError extends Error {
    public SyntaxError(String explanation, Cursor cursor) {
        super(explanation, cursor);
    }
}
