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
 \\.[^_^]./
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
   / \\
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
  /|o|\\
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
   / \\
 .[^_^]./
  /|o|
__________________________________________________
```
