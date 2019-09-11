package org.web3j.console.project;

import picocli.CommandLine;

import java.io.IOException;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import static org.web3j.codegen.Console.exitError;
import static org.web3j.console.project.ProjectCreator.COMMAND_NEW;
import static picocli.CommandLine.Help.Visibility.ALWAYS;


@Command(
        name = COMMAND_NEW,
        mixinStandardHelpOptions = true,
        version = "4.0",
        sortOptions = false
)
public class ProjectCreatorCLIRunner implements Runnable {
    @Option(
            names = {"-o", "--outputDir"},
            description = "destination base directory.",
            required = false,
            showDefaultValue = ALWAYS
    )
    String root = System.getProperty("user.dir");
    @Option(
            names = {"-p", "--package"},
            description = "base package name.",
            required = true
    )
    String packageName;
    @Option(
            names = {"-n", "--project name"},
            description = "project name.",
            required = true
    )
    String projectName;


    @Override
    public void run() {
        if (requiredArgsAreEmpty(projectName, packageName)) {
            exitError("Project name and Package name cannot be empty");
        }
        try {
            new ProjectCreator(root, packageName, projectName).generate();
        } catch (final IOException e) {
            exitError(e);
        }

    }

    boolean requiredArgsAreEmpty(final String... args) {
        for (final String argument : args) {
            if (argument.isEmpty()) {
                return true;
            }
        }
        return false;
    }
}
