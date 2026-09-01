# Project context

This repository is a starter template for a greenfield Java project used in an introductory software engineering course in an undergraduate computer science program. Students use it as the starting point for their own projects.

# Default user context

Unless the user says otherwise, assume that you are assisting a student working on a project in this repository. If the user identifies themselves as an instructor or another project stakeholder, adapt your response to that role.

# Student profile

* Prior knowledge: Basic Java and OOP concepts.
* Level of programming experience: Basic 
* IDE and level of expertise: IntelliJ IDEA (Basic)

# Guidance for interacting with users

* Explain the rationale for significant actions: what you did and why.
* Keep explanations brief but instructive, supporting learning through responsible use of AI. For example:

  * When suggesting a Git command, briefly explain what it does.
  * Add explanatory Javadoc comments to all classes and to nontrivial methods and fields when their purpose or behavior is not obvious.
  * Make generated code as self-explanatory as possible, and include explanatory comments where they improve understanding.
  * When faced with a design choice, choose the simplest option that is sufficient for the requirements, while briefly explaining relevant more advanced alternatives.

# Project-specific requirements

## Java version:

Ensure that Java 25 is used when running the application or build tasks. On macOS, use `sdk use java 25.0.3.fx-zulu` to switch to Java 25 if needed.

## Java coding standard

For every Java code change or review in this project, you MUST use and follow the
`seedu-java-coding-standard` skill at `.agents/skills/seedu-java-coding-standard/SKILL.md`.
All Java code in the project must comply with that standard.

## UI testing

After every code update in this project, you MUST:

1. Review `test/ui-test-plan.md` and update it when the changed behavior, setup, or required coverage is not represented accurately. Leave it unchanged when the existing plan remains accurate.
2. Invoke and follow the `test-ui` skill at `.agents/skills/test-ui/SKILL.md` after the test plan is current.
3. Do not report the code update as complete until the skill has finished. If testing cannot run or stops at a failure, report that outcome and do not claim that the UI tests passed.

## Git

Before proposing, creating, amending, or squashing any commit in this project, you MUST use and follow the
`seedu-git-standard` skill at `.agents/skills/seedu-git-standard/SKILL.md`. All future commits must comply with that
standard. Follow the same skill whenever naming or creating a branch.

Use lightweight tags unless the user requests an annotated tag.
When proposing or creating a commit message, include enough detail to explain the rationale for the change.
Do not commit or push unless explicitly asked.
