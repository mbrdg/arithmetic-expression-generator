package org.vut.ifje.project.reporter.error;

import org.vut.ifje.project.reporter.Cursor;

public class SyntaxError extends Error {
    private String hint = null;

    public SyntaxError(String explanation, Cursor cursor) {
        super(explanation, cursor);
    }

    public SyntaxError(String explanation, String hint, Cursor cursor) {
        super(explanation, cursor);
        this.hint = hint;
    }

    @Override
    public String print() {
        StringBuilder description = new StringBuilder().append("Syntax error at ")
                .append(cursor)
                .append(": ")
                .append(explanation);

        if (hint != null) {
            description.append("\n\thelp: ")
                    .append(hint);
        }

        return description.toString();
    }
}
