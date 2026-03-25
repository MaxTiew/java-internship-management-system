# Internship Recruitment and Matching System (Java)

A console-based Java application that simulates an internship recruitment workflow:

1. Manage applicants and job postings
2. Match applicants to jobs using weighted scoring
3. Schedule interviews for qualified candidates
4. Mark interview outcomes and hires
5. Generate multiple reports for decision making

This project is built with a layered architecture and a custom generic linked list ADT, making it a strong learning project for core Java, OOP, data structures, and basic algorithms.

## Project Highlights

1. Layered architecture (`boundary`, `control`, `entity`, `dao`, `adt`)
2. Custom generic `LinkedList<T>` implementation
3. Interface-driven design with `ListInterface<T>`
4. Weighted applicant-job matching algorithm
5. Interview scheduling with date validation
6. Search, filter, and sorting features
7. Reporting module for matches, jobs, interviews, and applicant analytics

## Tech Stack

1. Java (configured for Java 8 compatibility)
2. Maven project structure (`pom.xml`)
3. PowerShell helper script (`run.ps1`) for one-command run on Windows

## Project Structure

```text
src/main/java/
  adt/
    ListInterface.java
    LinkedList.java
    Sorter.java
  boundary/
    InternShipSystem.java
    ApplicantUI.java
    JobUI.java
    SearchUI.java
    ScheduleUI.java
    ReportUI.java
  control/
    ApplicantManagement.java
    JobManagement.java
    SearchControl.java
    MatchingControl.java
    ScheduleControl.java
    ReportControl.java
  entity/
    Applicant.java
    JobPosting.java
    Match.java
    Interview.java
    InterviewSlot.java
  dao/
    ApplicantInitializer.java
    JobInitializer.java
    ScheduleInitializer.java
```

## Main Features

### 1) Applicant Management

1. Add applicant with validation
2. Update applicant profile
3. Remove applicant
4. View all applicants
5. Search applicants/jobs by keyword

### 2) Job Management

1. Add job posting
2. Update job posting
3. Remove job posting
4. Filter jobs by title, location, skill, company, salary, and experience
5. Sort applicants for a job by experience, expected salary, or skill match

### 3) Matching Engine

Applicants are scored against jobs using weighted rules:

1. Required skill match (base score)
2. Priority skill bonus
3. Experience fit and bonus
4. Location fit (state and area)
5. Desired job type fit
6. Salary expectation fit

Matches are sorted descending by score before display.

### 4) Interview Scheduling

1. Select qualified candidates (score > 50)
2. Prevent scheduling already completed/hired candidates
3. Validate interview date format and past-date restriction
4. Assign online/physical slots
5. Confirm or reschedule before finalizing
6. Mark applicants as hired from completed interview list

### 5) Reporting

1. Match report (summary + detailed table)
2. Interview report (scheduled, completed, hired, success rate, top skill)
3. Job market summary report
4. Applicant reports:
   - General profile summary
   - Filtered report
   - Skills distribution
   - Job type preferences
   - Location distribution
   - Salary expectation distribution
   - Experience distribution

## Java Concepts Demonstrated

1. Object-Oriented Programming
   - Encapsulation in entity and control classes
   - Separation of concerns by package layer
2. Generics
   - `ListInterface<T>` and `LinkedList<T>`
3. Data Structures
   - Custom singly linked list with inner `Node<T>`
4. Algorithms
   - Insertion sort, selection sort, quicksort, bubble sort
   - Filtering and linear search
5. Input Validation and Error Handling
   - Numeric parsing and range checks
   - Exception handling for invalid input
6. Date/Time API
   - `LocalDate` and `DateTimeFormatter` for scheduling
7. Dependency Wiring
   - Main entry point creates controllers and injects dependencies into UIs

## Getting Started

### Prerequisites

1. Windows PowerShell (for `run.ps1`) or terminal
2. Java installed (`javac` and `java` available in `PATH`)
3. Recommended: matching runtime and compile versions

### Option A: One-command run (Windows PowerShell)

From project root:

```powershell
powershell -ExecutionPolicy Bypass -File .\run.ps1
```

If your local execution policy allows scripts:

```powershell
.\run.ps1
```

### Option B: Manual compile and run

From project root:

```powershell
$files = Get-ChildItem -Path src/main/java -Recurse -Filter *.java | ForEach-Object { $_.FullName }
New-Item -ItemType Directory -Force -Path target/classes-java8 | Out-Null
javac --release 8 -d target/classes-java8 $files
java -cp target/classes-java8 boundary.InternShipSystem
```

### Option C: Maven (if installed)

```powershell
mvn clean compile exec:java -Dexec.mainClass=boundary.InternShipSystem
```

## Main Menu Flow

1. Applicant Management
2. Job Management
3. View Matches
4. Interview Scheduling
5. Report
6. Exit

## Sample Data

The app starts with seeded data from:

1. `ApplicantInitializer`
2. `JobInitializer`
3. `ScheduleInitializer`

This allows immediate testing without manual setup.

## Known Notes and Improvement Ideas

1. Replace string-based skill/location parsing with normalized data models
2. Add persistent storage (database or file) instead of in-memory only
3. Add unit tests for matching, scheduling, and report logic
4. Use one shared `Scanner` and one shared `MatchingControl` instance across all UI modules
5. Improve slot-availability validation and conflict checks
6. Add logging and structured error messages

## Who This Project Is For

1. Students learning Java OOP and ADT implementation
2. Developers practicing console application architecture
3. Interview preparation for discussing real Java design and algorithm choices

## Author Notes

This project is suitable for portfolio demonstrations because it shows:

1. End-to-end feature workflow
2. Custom data structure implementation
3. Practical business logic and reporting
4. Clear separation between UI, logic, and data models
