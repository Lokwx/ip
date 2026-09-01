---
name: test-ui
description: Run Lokwx console UI test cases from test/ui-test-plan.md, compare each command's actual output with its expected output, and report a complete test-session transcript. Use after every code update and when asked to perform, record, or repeat UI tests for this project.
---

# Test UI

Use `test/ui-test-plan.md` as the source of truth for console UI tests.

## Prepare the plan

- Read the repository `AGENTS.md` and the whole test plan before testing.
- When invoked after a code update, review the changed behavior, setup, and coverage before testing. Update the plan if it is no longer accurate or complete; otherwise leave it unchanged.
- When the user supplies a list of commands and expected outputs, record it in the test plan before execution. Preserve unrelated existing cases unless the user asks to replace them.
- Give every test case a short unique ID, an aim, and one or more ordered command checks. Each check must pair one exact input command with one exact expected output block.
- Record relevant setup or state assumptions in the test case. Do not silently invent a missing expected output or reinterpret an ambiguous command.
- Validate that every command has exactly one expected-output block before launching the program.

## Run the tests

1. Verify that `java -version` and `javac -version` report Java 25. If Java 25 is unavailable, do not run under another version; report the prerequisite failure.
2. Build and launch the application using the commands in the plan, from the recorded working directory. Use a PTY or equivalent interactive process so commands can be sent one at a time.
3. Start with a fresh application process and execute test cases and command checks in their recorded order. Do not carry state over from a previous test run unless the plan explicitly requires it.
4. After each input command, capture all application output caused by that command. Wait for the response to finish before comparing it. Treat terminal input echo as input, not application output.
5. Normalize only line endings (`CRLF` or `CR` to `LF`). Otherwise compare exactly, including blank lines, indentation, punctuation, and case. Do not trim either value.
6. On a match, continue to the next command. On the first mismatch, unexpected process exit, timeout, or runtime error, send no further test commands and terminate the process safely.
7. If all checks pass and the application is still running, send the plan's shutdown command. Treat its response as transcript-only unless it is itself an explicit command check.

Startup, build, and shutdown output belongs in the session transcript but is not part of a command comparison unless the plan explicitly says it is.

## Report the session

Always show a chronological console-session record containing:

- the build and launch commands and their output;
- each input command, clearly marked as input;
- the application output observed after it, clearly marked as output; and
- shutdown or forced-termination output.

After a successful run, report how many test cases and command checks passed. After a failure, identify the test case and command, state that testing stopped immediately, and show the actual and expected outputs in separate fenced `text` blocks. Preserve whitespace in those blocks and include the transcript only through the failure.
