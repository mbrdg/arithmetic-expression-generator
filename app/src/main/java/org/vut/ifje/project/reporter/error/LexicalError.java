package org.vut.ifje.project.reporter.error;

import org.vut.ifje.project.reporter.Cursor;

public class LexicalError extends Error {
    public LexicalError(String explanation, Cursor cursor) {
        super(explanation, cursor);
    }
}
