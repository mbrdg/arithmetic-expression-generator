package org.vut.ifje.project.reporter;

import java.util.Objects;

/**
 * A cursor represents a position in a given text stream
 * It is composed of two offsets: one for lines and another for columns.
 */
public class Cursor {
    private int line;
    private int column;

    /**
     * Constructs and initializes a cursor to the beginning of the stream.
     */
    public Cursor() {
        this.line = 1;
        this.column = 1;
    }

    /**
     * Constructs and initializes a cursor to a specific position of the stream
     *
     * @param line   the line of the newly constructed cursor
     * @param column the column of th newly constructed cursor
     */
    public Cursor(int line, int column) {
        this.line = line;
        this.column = column;
    }

    /**
     * Constructs and initializes a cursor with the same location as the specified <code>Cursor</code> object
     *
     * @param cursor a cursor
     */
    public Cursor(Cursor cursor) {
        this.line = cursor.line;
        this.column = cursor.column;
    }

    /**
     * Returns the line of this cursor
     *
     * @return the line of this cursor
     */
    public int line() {
        return line;
    }

    /**
     * Returns the column of this cursor
     *
     * @return the column of this cursor
     */
    public int column() {
        return column;
    }

    /**
     * Increments the line counter of this cursor
     */
    public void advance() {
        ++column;
    }

    /**
     * Decrements the column counter of this cursor
     */
    public void rewind() {
        --column;
    }

    /**
     * Increments the line counter of this cursor.
     */
    public void newline() {
        ++line;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cursor cursor = (Cursor) o;
        return line == cursor.line && column == cursor.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(line, column);
    }

    @Override
    public String toString() {
        return "line=" + line + ',' + " column=" + column;
    }
}
