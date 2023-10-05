package org.vut.ifje.project.reporter;

public class Cursor {
    private int line = 1;
    private int column = 1;

    public Cursor() {
    }

    public Cursor(int line, int column) {
        this.line = line;
        this.column = column;
    }

    public int line() {
        return line;
    }

    public int column() {
        return column;
    }

    public void incrementLine() {
        ++line;
    }

    public void incrementColumn() {
        ++column;
    }
}
