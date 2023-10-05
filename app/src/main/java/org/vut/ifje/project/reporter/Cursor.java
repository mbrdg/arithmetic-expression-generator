package org.vut.ifje.project.reporter;

import java.util.Objects;

public class Cursor {
    private int line;
    private int column;

    public Cursor() {
        this.line = 1;
        this.column = 1;
    }

    public Cursor(int line, int column) {
        this.line = line;
        this.column = column;
    }

    public Cursor(Cursor cursor) {
        this.line = cursor.line;
        this.column = cursor.column;
    }

    public int line() {
        return line;
    }

    public int column() {
        return column;
    }

    public void advance() {
        ++column;
    }

    public void rewind() {
        --column;
    }

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
}
