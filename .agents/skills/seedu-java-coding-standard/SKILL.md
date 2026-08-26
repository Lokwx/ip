---
name: seedu-java-coding-standard
description: Apply and review the SE-EDU Java coding standard for every Java code change in this repository. Use whenever creating, editing, refactoring, or reviewing Java source or Java tests here.
---

# Seedu Java Coding Standard

Apply the SE-EDU basic and intermediate Java rules to all Java code in this repository. Before finishing a Java
task, review every Java file changed in the task against this standard and correct any violations. Preserve the
requested behavior while making compliance changes.

Source: [SE-EDU Java coding standard (basic + intermediate)](https://se-education.org/guides/conventions/java/intermediate.html)

For a topic not covered below, follow the [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html).

## Naming

- Use lowercase package names. Start school-project packages with the project or group name, followed by logical
  subpackages; do not use misleading institutional namespaces such as `edu.nus.comp`.
- Name classes and enums with English nouns in PascalCase.
- Name variables in camelCase and methods with English verbs in camelCase.
- Use SCREAMING_SNAKE_CASE for actual constants as defined by Google Java Style. Give related constants a common
  prefix.
- In test names only, underscores may express `featureUnderTest_testScenario_expectedBehavior`; either the scenario
  or both the scenario and expected behavior may be omitted.
- Treat abbreviations and acronyms as words inside identifiers, such as `exportHtmlSource`, not `exportHTMLSource`.
- Give wide-scope variables descriptive names. Short scratch names such as `i`, `j`, and `k` are acceptable only in
  small scopes; reserve `j` and later letters for nested loops.
- Name booleans so they read as booleans, normally with `is`, `has`, `was`, `can`, or `should`. Name a boolean setter
  `setFound(boolean isFound)`, not `setIsFound(boolean value)`.
- Use plural names for collections and arrays.

## Layout

- Indent with 4 spaces and never tabs.
- Keep lines below 110 characters where practical and never exceed 120 characters.
- Indent wrapped lines 8 spaces beyond their parent. Break after commas and before operators, including `.`, `&`,
  and `|`. Keep a method or constructor name attached to its opening parenthesis.
- Prefer high-level expression breaks and use line breaks to improve readability instead of accepting IDE wrapping
  mechanically.
- Use K&R braces: place an opening brace at the end of its statement and the matching closing brace on its own
  aligned line. Put `else`, `catch`, and `finally` on the same line as the preceding closing brace.
- Put spaces around operators, after Java keywords, after commas, around ternary colons, and after semicolons in a
  `for` header.
- Separate logical units within a block with one blank line.

## Statements and declarations

- Put every class in a package.
- Keep import ordering consistent, group imports clearly, list every imported class explicitly, and remove unused
  imports. Never use wildcard imports.
- Attach array brackets to the type, as in `int[] values`.
- Declare each variable in the smallest practical scope and initialize it at declaration when a valid initial value
  is available. Do not use a fake initializer merely to satisfy this rule.
- Keep class variables non-public unless the class is a behavior-free data class; constants may be public when
  appropriate.
- Always use braces around loop and conditional bodies, including single-line bodies. Put the condition and body on
  separate lines.
- Format `for`, `while`, `do-while`, `switch`, `try-catch`, and `try-catch-finally` consistently with K&R braces.
- In a traditional `switch`, include `// Fallthrough` before every intentional fallthrough. Arrow-style switch cases
  do not need fallthrough comments.

## Comments and Javadocs

- Write comments in clear English using American spelling and no local slang. Indent comments with the code they
  describe.
- Write descriptive Javadocs for every class and public method. Javadocs may be omitted for simple getters/setters,
  test code, and overrides whose inherited documentation applies exactly.
- Start each Javadoc with a short summary sentence beginning with a third-person verb such as `Returns`, `Adds`, or
  `Sends`.
- Put `/**` and `*/` on their own lines, align each `*`, leave one space after it, and place no blank line between the
  Javadoc and its declaration.
- Leave one blank Javadoc line between the description and tags. End parameter descriptions with punctuation. Either
  document all parameters with `@param` tags or omit all parameter tags when every parameter is already clear.
- Omit `@return` for `void` methods or when the return value is already obvious from the description. Use
  `{@inheritDoc}` when inherited documentation needs an additional clarification.
