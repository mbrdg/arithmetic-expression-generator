package org.vut.ifje.project.reporter;

import org.vut.ifje.project.reporter.error.Error;

public interface Reporter<R> {
    public boolean hasErrors();
    public void add(Error error);
    public R dump();
}
