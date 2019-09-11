/*
 * Copyright 2019 Web3 Labs LTD.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.web3j.console.project;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import picocli.CommandLine;

import org.web3j.console.WalletTester;

import static org.junit.Assert.assertTrue;

public class ProjectCreatorTest extends WalletTester {
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {

        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void reset() {
        System.setOut(System.out);
        System.setIn(System.in);
    }

    @Test
    public void testWhenCorrectArgsArePassedProjectStructureCreated() {
        final String[] args = {"-p=org.com", "-n=Test", "-o=" + tempDirPath};
        final ProjectCreatorCLIRunner projectCreatorCLIRunner = new ProjectCreatorCLIRunner();
        new CommandLine(projectCreatorCLIRunner).parseArgs(args);
        assert projectCreatorCLIRunner.packageName.equals("org.com");
        assert projectCreatorCLIRunner.projectName.equals("Test");
    }

    @Test
    public void runTestWhenArgumentsAreEmpty() throws IOException {
        final String[] args = {"", ""};
        ProjectCreator.main(args);
        assertTrue(
                outContent
                        .toString()
                        .contains(
                                "Missing required options [--package=<packageName>, --project name=<projectName>]"));
    }

    @Test
    public void runTestWhenArgumentsAreNotEmpty() throws IOException {
        final String[] args = {"new", "-p=org.com", "-n=Test", "-o=" + tempDirPath};
        ProjectCreator.main(args);
        assertTrue(outContent.toString().contains("Project created"));
    }
}