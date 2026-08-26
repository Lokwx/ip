---
name: seedu-git-standard
description: Apply and review the SE-EDU Git conventions when naming branches or proposing, writing, amending, squashing, or creating commits in this repository.
---

# SE-EDU Git Standard

Apply the SE-EDU Git conventions whenever working with commit messages or branch names in this repository. Before
creating, amending, or squashing a commit, review the complete message against this standard. Do not create, amend,
rebase, squash, or push commits unless the user has authorized the relevant Git operation.

Source: [SE-EDU Git conventions](https://se-education.org/guides/conventions/git.html)

## Commit subject

- Give every commit a clear, well-written subject.
- Aim for no more than 50 characters and never exceed 72 characters, including any optional prefix.
- Use the imperative mood, as in `Add README.md`, rather than `Added README.md` or `Adding README.md`.
- Capitalize the first word of the imperative subject and do not end the subject with a period.
- Add an optional `<scope>:` or `<category>:` prefix only when it improves clarity. Keep the text after the prefix
  imperative and capitalized, as in `Parser: Handle empty input` or `chore: Update release date`.

## Commit body

- Include a body for every non-trivial commit when the subject alone cannot explain the change's context and
  rationale.
- Separate the subject and body with one blank line. Wrap every body line at 72 characters and separate paragraphs
  with blank lines.
- Use bullet points when they communicate multiple related points more clearly than prose.
- Explain what the commit changes and why the change is needed or designed that way. Leave implementation details to
  the diff unless they are necessary to understand the decision.
- Give enough context for a reviewer to judge the purpose and rationale without reading the diff. Avoid repeating
  information already captured in code comments.
- Describe the existing situation in the present tense, explain why it needs to change, describe the change in the
  imperative mood, and include the design rationale or other relevant context.
- Avoid redundant time qualifiers such as `currently` and `originally` when describing the existing situation.
- If the body becomes excessively long or covers unrelated purposes, propose splitting the work into finer-grained
  commits. Do not alter the user's staging or commit history without authorization.

## Branch names

- Use a meaningful name containing relevant keywords in kebab-case, such as `refactor-ui-tests`.
- For a branch associated with an issue, use `issueNumber-keywords-from-issue-title`, such as
  `1234-ui-freeze-error`.

## Final check

Before using or recommending a commit message or branch name:

1. Confirm that the commit represents one coherent purpose; propose a split if it does not.
2. Check the subject's mood, capitalization, punctuation, clarity, and character count.
3. For a non-trivial commit, check the blank separator, 72-character wrapping, and explanation of what and why.
4. Check branch names for meaningful kebab-case wording and the issue-number prefix when applicable.
