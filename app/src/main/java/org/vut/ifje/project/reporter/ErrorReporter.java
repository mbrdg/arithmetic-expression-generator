package org.vut.ifje.project.reporter;

import org.vut.ifje.project.reporter.error.Error;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ErrorReporter implements Reporter<String> {
    private final List<Error> errors = new ArrayList<>();

    @Override
    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    @Override
    public void add(Error error) {
        errors.add(error);
    }

    @Override
    public String dump() {
        return errors.stream()
                .map(Error::print)
                .collect(Collectors.joining("\n"));
    }
}
