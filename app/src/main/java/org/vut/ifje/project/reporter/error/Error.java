package org.vut.ifje.project.reporter.error;

import org.vut.ifje.project.reporter.Cursor;

/**
 * An error is an abstract class that holds data about any type of error.
 */
public abstract class Error {
    protected final String explanation;
    protected final Cursor cursor;

    /**
     * Constructs and initializes new Error object containing its explanation and cursor.
     *
     * @param explanation the explanation of the newly constructed error
     * @param cursor      the cursor that points to the beginning of the token that generated the error
     */
    public Error(String explanation, Cursor cursor) {
        this.explanation = explanation;
        this.cursor = cursor;
    }

    /**
     * Prints the error representation to a <code>String</code>
     *
     * @return the string representation of the error
     */
    public abstract String print();
}
