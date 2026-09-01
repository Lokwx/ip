# UI Test Plan

## Application setup

- Working directory: repository root
- Required Java version: 25
- Build command: `javac -d bin src/main/java/lokwx/*.java`
- Launch command: `java -cp bin lokwx.Lokwx`
- Shutdown command: `bye`
- Comparison rule: Normalize line endings to LF, then compare exactly without trimming.
- Session rule: Use a fresh Lokwx process for each complete test run unless a test case explicitly requires a different setup.

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
