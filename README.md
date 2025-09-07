# CronParser

A simple command-line application that parses a standard 5-field cron expression and expands each field into the concrete values at which it will run.  
The parser supports:
- `*` (wildcard, all values in range)
- `,` (lists, e.g. `1,15`)
- `-` (ranges, e.g. `1-5`)
- `/` (steps, e.g. `*/15`)

It prints the expanded schedule in a table format where each field name is left-aligned to 14 characters.

Requirements
Java 11+, Maven

From project root on Mac (~/IdeaProjects/CronParser)
# Compile into target/classes
javac -d target/classes src/main/java/com/cronparser/*.java

# Run with your cron expression
java -cp target/classes com.cronparser.CronParser "*/15 0 1,15 * 1-5 /usr/bin/find"
