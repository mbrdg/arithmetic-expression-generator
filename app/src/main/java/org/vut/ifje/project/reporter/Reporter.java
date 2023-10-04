package org.vut.ifje.project.reporter;

import java.util.ArrayList;
import java.util.List;

public class Reporter {
    private List<Error> errors = new ArrayList<>();

    public void add(Error error) {
        errors.add(error);
    }

    public boolean isEmpty() {
        return errors.isEmpty();
    }
}
