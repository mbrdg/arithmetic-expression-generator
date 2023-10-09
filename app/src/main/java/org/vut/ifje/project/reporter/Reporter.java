package org.vut.ifje.project.reporter;

public interface Reporter<E, R> {
    /**
     * Returns true if this Reporter contains any errors, otherwise false.
     *
     * @return true if this Reporter contains any errors, otherwise false.
     */
    boolean hasErrors();

    /**
     * Adds an error to this Reporter.
     *
     * @param error the errors that is being added to the reporter
     */
    void add(E error);

    /**
     * Prints all the errors in a custom return type decided by the implementer.
     *
     * @return representation of all errors after being printed
     */
    R dump();
}
