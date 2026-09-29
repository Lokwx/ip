# UI Test Plan

## Application setup

- Working directory: repository root
- Required Java version: 25
- Build command: `javac -d bin $(find src/main/java -name "*.java")`
- Windows PowerShell build command: `javac -d bin (Get-ChildItem -Path src/main/java -Recurse -Filter *.java).FullName`
- Launch command: `java -cp bin lokwx.Lokwx`
- Shutdown command: `bye`
- Comparison rule: Normalize line endings to LF, then compare exactly without trimming.
- Session rule: Use a fresh Lokwx process for each complete test run unless a test case explicitly requires a different setup.
- Storage rule: Before each test case, remove `data/lokwx.txt` if it exists. UI-04 removes it only before session A.

## Test cases

### UI-01: Add and list multiple tasks

**Aim:** Verify that a session counts added tasks correctly and lists each task in insertion order.

**Setup:** Fresh application session.

#### Check 1

**Input command**

```text
todo read book
```

**Expected output**

```text

Got it. I've added this Todo:
[T][ ] read book
Now you have 1 task in this list.

   ^^^^^
 \.[^_^]./
    |o|
__________________________________________________
```

#### Check 2

**Input command**

```text
todo submit assignment
```

**Expected output**

```text

Got it. I've added this Todo:
[T][ ] submit assignment
Now you have 2 tasks in this list.

   ^^^^^
 \.[^_^]./
    |o|
__________________________________________________
```

#### Check 3

**Input command**

```text
list
```

**Expected output**

```text

1. [T][ ] read book
2. [T][ ] submit assignment

  ?????
 .[o_o].
  /|o|\
__________________________________________________
```

#### Check 4

**Input command**

```text
bye
```

**Expected output**

```text

Bye. Hope to see you again soon!

    o
   / \
 .[^_^]./
  /|o|
__________________________________________________
```

### UI-06: Find tasks by description keyword

**Aim:** Verify that finding tasks searches descriptions across task types without regard to letter case, reports when
no tasks match, and rejects an empty keyword.

**Setup:** Fresh application session.

#### Check 1

**Input command**

```text
todo read book
```

**Expected output**

```text

Got it. I've added this Todo:
[T][ ] read book
Now you have 1 task in this list.

   ^^^^^
 \.[^_^]./
    |o|
__________________________________________________
```

#### Check 2

**Input command**

```text
deadline return book /by 2/12/2019 1800
```

**Expected output**

```text

Got it. I've added this deadline:
[D][ ] return book (by: Dec 02 2019, 6:00 PM)
Now you have 2 tasks in this list.

   ^^^^^
 \.[^_^]./
    |o|
__________________________________________________
```

#### Check 3

**Input command**

```text
todo write essay
```

**Expected output**

```text

Got it. I've added this Todo:
[T][ ] write essay
Now you have 3 tasks in this list.

   ^^^^^
 \.[^_^]./
    |o|
__________________________________________________
```

#### Check 4

**Input command**

```text
find BOOK
```

**Expected output**

```text

Here are the matching tasks in your list:
1. [T][ ] read book
2. [D][ ] return book (by: Dec 02 2019, 6:00 PM)

  ?????
 .[o_o].
  /|o|\
__________________________________________________
```

#### Check 5

**Input command**

```text
find textbook
```

**Expected output**

```text

No matching tasks found.

  ?????
 .[o_o].
  /|o|\
__________________________________________________
```

#### Check 6

**Input command**

```text
find
```

**Expected output**

```text
Oops! Please enter a keyword to find.

  _____
 .[T_T].
  /|o|\
__________________________________________________
```

#### Check 7

**Input command**

```text
bye
```

**Expected output**

```text

Bye. Hope to see you again soon!

    o
   / \
 .[^_^]./
  /|o|
__________________________________________________
```

### UI-02: Delete a task from the list

**Aim:** Verify that deleting a task removes it and decrements the remaining task count.

**Setup:** Fresh application session.

#### Check 1

**Input command**

```text
todo read book
```

**Expected output**

```text

Got it. I've added this Todo:
[T][ ] read book
Now you have 1 task in this list.

   ^^^^^
 \.[^_^]./
    |o|
__________________________________________________
```

#### Check 2

**Input command**

```text
todo submit assignment
```

**Expected output**

```text

Got it. I've added this Todo:
[T][ ] submit assignment
Now you have 2 tasks in this list.

   ^^^^^
 \.[^_^]./
    |o|
__________________________________________________
```

#### Check 3

**Input command**

```text
delete 1
```

**Expected output**

```text

Got it. I've removed this Todo:
[T][ ] read book
Now you have 1 task in this list.

   ^^^^^
 \.[^_^]./
    |o|
__________________________________________________
```

#### Check 4

**Input command**

```text
list
```

**Expected output**

```text

1. [T][ ] submit assignment

  ?????
 .[o_o].
  /|o|\
__________________________________________________
```

#### Check 5

**Input command**

```text
bye
```

**Expected output**

```text

Bye. Hope to see you again soon!

    o
   / \
 .[^_^]./
  /|o|
__________________________________________________
```

### UI-03: Reject invalid task numbers when deleting

**Aim:** Verify that deleting with zero or a nonnumeric task number displays an error and preserves the task.

**Setup:** Fresh application session with one task.

#### Check 1

**Input command**

```text
todo read book
```

**Expected output**

```text

Got it. I've added this Todo:
[T][ ] read book
Now you have 1 task in this list.

   ^^^^^
 \.[^_^]./
    |o|
__________________________________________________
```

#### Check 2

**Input command**

```text
delete 0
```

**Expected output**

```text
Oops! you need to enter a valid number!

  _____
 .[T_T].
  /|o|\
__________________________________________________
```

#### Check 3

**Input command**

```text
delete abc
```

**Expected output**

```text
Oops! you need to enter a valid number!

  _____
 .[T_T].
  /|o|\
__________________________________________________
```

#### Check 4

**Input command**

```text
list
```

**Expected output**

```text

1. [T][ ] read book

  ?????
 .[o_o].
  /|o|\
__________________________________________________
```

#### Check 5

**Input command**

```text
bye
```

**Expected output**

```text

Bye. Hope to see you again soon!

    o
   / \
 .[^_^]./
  /|o|
__________________________________________________
```

### UI-04: Save and reload a task

**Aim:** Verify that adding a task saves it to disk and a new chatbot session loads it.

**Setup:** Remove `data/lokwx.txt`. Launch fresh session A for checks 1-2, then launch fresh session B for checks 3-4.

#### Check 1

**Input command**

```text
todo read book
```

**Expected output**

```text

Got it. I've added this Todo:
[T][ ] read book
Now you have 1 task in this list.

   ^^^^^
 \.[^_^]./
    |o|
__________________________________________________
```

#### Check 2

**Input command**

```text
bye
```

**Expected output**

```text

Bye. Hope to see you again soon!

    o
   / \
 .[^_^]./
  /|o|
__________________________________________________
```

#### Check 3

**Input command**

```text
list
```

**Expected output**

```text

1. [T][ ] read book

  ?????
 .[o_o].
  /|o|\
__________________________________________________
```

#### Check 4

**Input command**

```text
bye
```

**Expected output**

```text

Bye. Hope to see you again soon!

    o
   / \
 .[^_^]./
  /|o|
__________________________________________________
```

### UI-05: Parse, format, validate, and reload deadlines

**Aim:** Verify that deadline dates become date-time values, display in a friendly format, reject impossible dates,
and retain their value after reloading from disk.

**Setup:** Remove `data/lokwx.txt`. Launch fresh session A for checks 1-4, then launch fresh session B for checks 5-6.

#### Check 1

**Input command**

```text
deadline return book /by 2/12/2019 1800
```

**Expected output**

```text

Got it. I've added this deadline:
[D][ ] return book (by: Dec 02 2019, 6:00 PM)
Now you have 1 task in this list.

   ^^^^^
 \.[^_^]./
    |o|
__________________________________________________
```

#### Check 2

**Input command**

```text
deadline submit report /by 2019-10-15
```

**Expected output**

```text

Got it. I've added this deadline:
[D][ ] submit report (by: Oct 15 2019)
Now you have 2 tasks in this list.

   ^^^^^
 \.[^_^]./
    |o|
__________________________________________________
```

#### Check 3

**Input command**

```text
deadline impossible date /by 31/2/2019 1800
```

**Expected output**

```text
Oops! Enter the deadline as d/M/yyyy HHmm, yyyy-MM-dd HHmm, or yyyy-MM-dd.

  _____
 .[T_T].
  /|o|\
__________________________________________________
```

#### Check 4

**Input command**

```text
bye
```

**Expected output**

```text

Bye. Hope to see you again soon!

    o
   / \
 .[^_^]./
  /|o|
__________________________________________________
```

#### Check 5

**Input command**

```text
list
```

**Expected output**

```text

1. [D][ ] return book (by: Dec 02 2019, 6:00 PM)
2. [D][ ] submit report (by: Oct 15 2019)

  ?????
 .[o_o].
  /|o|\
__________________________________________________
```

#### Check 6

**Input command**

```text
bye
```

**Expected output**

```text

Bye. Hope to see you again soon!

    o
   / \
 .[^_^]./
  /|o|
__________________________________________________
```
