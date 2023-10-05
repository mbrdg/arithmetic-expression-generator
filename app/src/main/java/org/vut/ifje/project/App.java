/*
 * This is the driver code of the expression transformer.
 */
package org.vut.ifje.project;

import org.vut.ifje.project.ast.AstVisitor;
import org.vut.ifje.project.ast.expr.Expr;
import org.vut.ifje.project.parser.Parser;
import org.vut.ifje.project.reporter.ErrorReporter;
import org.vut.ifje.project.scanner.Scanner;
import org.vut.ifje.project.scanner.Token;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class App {
    public static void usage() {
        System.err.println("usage: echo <program> | ./generator");
        System.exit(0);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        if (!reader.ready()) {
            usage();
        }

        ErrorReporter reporter = new ErrorReporter();

        String source = reader.lines().collect(Collectors.joining("\n"));
        Scanner scanner = new Scanner(source, reporter);
        List<Token> tokens = scanner.scan();

        Parser parser = new Parser(tokens, reporter);
        Optional<? extends Expr> tree = parser.parse();

        if (tree.isPresent() && !reporter.hasErrors()) {
            AstVisitor visitor = new AstVisitor();
            System.out.print(tree.get().accept(visitor));
        }

        System.err.println(reporter.dump());
    }
}
