# Lokwx User Guide

Lokwx is a command-line chatbot for keeping track of todos, deadlines, and events. It helps you record work quickly,
check what remains, find tasks, and update their completion status without leaving the terminal.

- [Quick start](#quick-start)
- [Features](#features)
    - [Adding a todo: `todo`](#adding-a-todo-todo)
    - [Adding a deadline: `deadline`](#adding-a-deadline-deadline)
    - [Adding an event: `event`](#adding-an-event-event)
    - [Listing all tasks: `list`](#listing-all-tasks-list)
    - [Finding tasks: `find`](#finding-tasks-find)
    - [Marking a task as complete: `mark`](#marking-a-task-as-complete-mark)
    - [Marking a task as incomplete: `unmark`](#marking-a-task-as-incomplete-unmark)
    - [Deleting a task: `delete`](#deleting-a-task-delete)
    - [Exiting Lokwx: `bye`](#exiting-lokwx-bye)
- [Saving data](#saving-data)
- [FAQ](#faq)
- [Known limitations](#known-limitations)
- [Command summary](#command-summary)

---

## Quick start

1. Ensure that [Java 25](https://www.oracle.com/java/technologies/downloads/#java25) or later is installed.
2. Download `Lokwx.jar` from the [latest release](https://github.com/Lokwx/ip/releases/latest).
3. Place the JAR file in the folder that you want to use as Lokwx's home folder. Lokwx will keep its saved data in this
   folder.
4. Open a terminal in that folder and run:

   ```text
   java -jar Lokwx.jar
   ```

5. Lokwx will greet you:

   ```text
   Hello! I'm Lokwx.
   What can I do for you?
   ```

6. Type a command and press **Enter**. Here are a few commands to try:

    - `todo read chapter 1` adds a todo.
    - `deadline submit report /by 2/10/2026 1800` adds a deadline.
    - `event project meeting /from Monday 2pm /to Monday 3pm` adds an event.
    - `list` shows all saved tasks.
    - `bye` exits Lokwx.

Refer to [Features](#features) for every supported command.

---

## Features

> **Notes about command formats**
>
> - Command words are not case-sensitive. For example, `list`, `LIST`, and `List` run the same command.
> - Words in `UPPER_CASE` are values that you supply. For example, replace `DESCRIPTION` with text such as
    `read chapter 1`.
> - A `TASK_NUMBER` must be a positive integer shown by the `list` command, such as `1` or `2`.
> - Text after the command is required unless the format says otherwise. Lokwx displays an error instead of adding a
    task with an empty description.
> - Enter each command on one line.

### Adding a todo: `todo`

Adds a task without a date or time.

Format: `todo DESCRIPTION`

Example:

```text
todo read chapter 1
```

Lokwx adds an incomplete todo and confirms the new number of tasks:

```text
Got it. I've added this Todo:
[T][ ] read chapter 1
Now you have 1 task in this list.
```

`[T]` identifies a todo, while `[ ]` means that it is incomplete.

### Adding a deadline: `deadline`

Adds a task that must be completed by a specific date or date and time.

Format: `deadline DESCRIPTION /by DATE_OR_TIME`

The description and `/by` value must not be empty. Lokwx accepts these strict date formats:

| Format            | Example input     | Displayed as           |
|-------------------|-------------------|------------------------|
| `d/M/yyyy HHmm`   | `2/10/2026 1800`  | `Oct 02 2026, 6:00 PM` |
| `yyyy-MM-dd HHmm` | `2026-10-02 1800` | `Oct 02 2026, 6:00 PM` |
| `yyyy-MM-dd`      | `2026-10-02`      | `Oct 02 2026`          |

Times use the 24-hour `HHmm` format. For example, enter `0905` for 9:05 AM and `1800` for 6:00 PM. A date without a time
is stored at the start of that day.

Example:

```text
deadline submit report /by 2/10/2026 1800
```

Lokwx displays the new deadline as:

```text
[D][ ] submit report (by: Oct 02 2026, 6:00 PM)
```

`[D]` identifies a deadline.

### Adding an event: `event`

Adds an event with a start and end.

Format: `event DESCRIPTION /from START /to END`

The description, start, and end must not be empty. `/from` must appear before `/to`. Start and end values are saved as
the text that you enter, so you can use a style such as `Monday 2pm` or `2026-10-02 1400`.

Example:

```text
event project meeting /from Monday 2pm /to Monday 3pm
```

Lokwx displays the new event as:

```text
[E][ ] project meeting (from: Monday 2pm to: Monday 3pm)
```

`[E]` identifies an event.

### Listing all tasks: `list`

Shows every saved task and its task number.

Format: `list`

Example output:

```text
1. [T][ ] read chapter 1
2. [D][X] submit report (by: Oct 02 2026, 6:00 PM)
3. [E][ ] project meeting (from: Monday 2pm to: Monday 3pm)
```

The symbols have the following meanings:

- `[T]`, `[D]`, and `[E]` identify todos, deadlines, and events respectively.
- `[X]` means completed.
- `[ ]` means incomplete.

If there are no tasks, Lokwx asks you to add one before listing.

### Finding tasks: `find`

Shows tasks whose descriptions contain the search text.

Format: `find KEYWORD`

The search:

- is not case-sensitive, so `report` matches `Report`;
- considers task descriptions only;
- can match part of a word, so `read` matches `reading`; and
- treats everything after `find` as one search phrase, so `find project meeting` looks for the phrase `project meeting`.

Example:

```text
find report
```

Example output:

```text
Here are the matching tasks in your list:
1. [D][X] submit report (by: Oct 02 2026, 6:00 PM)
```

Lokwx displays `No matching tasks found.` when nothing matches.

> **Task-number tip:** Search results are numbered from 1 for display. Run `list` before `mark`, `unmark`, or `delete`,
> because those commands use the task's number in the full list.

### Marking a task as complete: `mark`

Marks one task as completed and changes its checkbox to `[X]`.

Format: `mark TASK_NUMBER`

Example:

```text
mark 1
```

Example output:

```text
Nice! I've marked this task as done:
[X] read chapter 1
```

The task number must refer to an existing task in the full list.

### Marking a task as incomplete: `unmark`

Marks one task as incomplete and changes its checkbox to `[ ]`.

Format: `unmark TASK_NUMBER`

Example:

```text
unmark 1
```

Example output:

```text
Okay, I've marked this task as not done yet:
[ ] read chapter 1
```

The task number must refer to an existing task in the full list.

### Deleting a task: `delete`

Permanently removes one task from the list.

Format: `delete TASK_NUMBER`

Example:

```text
delete 1
```

Example output:

```text
Got it. I've removed this Todo:
[T][ ] read chapter 1
Now you have 2 tasks in this list.
```

Run `list` first and check the task number before deleting. The remaining tasks are renumbered after a deletion.

### Exiting Lokwx: `bye`

Ends the current Lokwx session.

Format: `bye`

Example output:

```text
Bye. Hope to see you again soon!
```

---

## Saving data

Lokwx automatically saves after every `todo`, `deadline`, `event`, `mark`, `unmark`, and `delete` command. You do not
need to save manually.

Data is stored in `data/lokwx.txt`, relative to the folder from which you run Lokwx. The file is created automatically
and saved tasks are loaded the next time Lokwx starts from the same folder.

> **Caution:** Avoid editing `data/lokwx.txt` manually. An invalid task type, completion status, deadline, or field
> layout can prevent the saved tasks from loading. If you must edit it, make a backup first.

## FAQ

**Q: How do I transfer my tasks to another computer?**

Install Lokwx on the other computer, run it once, and close it. Copy `data/lokwx.txt` from the old Lokwx home folder
into the new home folder, replacing the newly created file.

**Q: Why did Lokwx say that my task number is not on the list?**

Task numbers must be positive integers from the full `list` output. Run `list` again because numbers can change after
deletion.

**Q: Why did my deadline get rejected?**

Use one of the three supported formats exactly: `d/M/yyyy HHmm`, `yyyy-MM-dd HHmm`, or `yyyy-MM-dd`. Dates are checked
strictly, so impossible dates such as `31/2/2026` are rejected.

**Q: Can I use words such as `tomorrow` for an event time?**

Yes. Event start and end values are free-form text. Deadline dates, however, must use one of the supported date formats.

## Known limitations

1. `find` result numbers are local to the displayed search results. `mark`, `unmark`, and `delete` still use numbers
   from the full `list` output.
2. Event start and end values are stored as text and are not checked as dates or compared to ensure that the end occurs
   after the start.
3. Avoid using the `|` character in task descriptions or event times because it is used internally to separate fields in
   the data file.
4. If `data/lokwx.txt` is corrupted, Lokwx reports the affected line and may be unable to load saved tasks until the
   file is repaired or replaced with a backup.

---

## Command summary

| Action          | Format                                  | Example                                      |
|-----------------|-----------------------------------------|----------------------------------------------|
| Add a todo      | `todo DESCRIPTION`                      | `todo read chapter 1`                        |
| Add a deadline  | `deadline DESCRIPTION /by DATE_OR_TIME` | `deadline submit report /by 2026-10-02 1800` |
| Add an event    | `event DESCRIPTION /from START /to END` | `event meeting /from 2pm /to 3pm`            |
| List all tasks  | `list`                                  | `list`                                       |
| Find tasks      | `find KEYWORD`                          | `find report`                                |
| Mark complete   | `mark TASK_NUMBER`                      | `mark 2`                                     |
| Mark incomplete | `unmark TASK_NUMBER`                    | `unmark 2`                                   |
| Delete a task   | `delete TASK_NUMBER`                    | `delete 1`                                   |
| Exit Lokwx      | `bye`                                   | `bye`                                        |
