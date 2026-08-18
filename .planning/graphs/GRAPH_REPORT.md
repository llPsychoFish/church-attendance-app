# Graph Report - church-attendance-app  (2026-08-18)

## Corpus Check
- 46 files · ~59,968 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 387 nodes · 630 edges · 27 communities (25 shown, 2 thin omitted)
- Extraction: 92% EXTRACTED · 8% INFERRED · 0% AMBIGUOUS · INFERRED: 51 edges (avg confidence: 0.8)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `19a24df8`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- UsherDao
- Member
- AuthViewModel
- FirstTimer
- AttendanceDao
- Church Attendance & First-Timer App — Design Document
- AppDatabase.java
- ExampleInstrumentedTest.java
- ExampleUnitTest.java
- gradlew
- Communities (26 total, 3 thin omitted)
- v1 Requirements
- Design System — Church Attendance App
- Church Attendance & First-Timer App — Development Roadmap
- GSD Roadmap — Church Attendance & First-Timer App
- Data Field Specification — BLW KTU First Timer Card & Attendance Sheet
- Church Attendance & First-Timer App
- SessionManager
- AGENTS.md — Church Attendance App
- Project Structure — Church Attendance App (Android, Java)
- ChurchAttendanceApp
- Project State
- README.md
- Phase 3 — Home Screen & Service Selection

## God Nodes (most connected - your core abstractions)
1. `Communities (26 total, 3 thin omitted)` - 21 edges
2. `Member` - 20 edges
3. `SessionManager` - 19 edges
4. `AuthViewModel` - 15 edges
5. `MemberDao` - 14 edges
6. `UsherDao` - 14 edges
7. `Usher` - 14 edges
8. `MainActivity` - 13 edges
9. `AppDatabase` - 13 edges
10. `AttendanceDao` - 13 edges

## Surprising Connections (you probably didn't know these)
- `AttendanceDaoTest` --references--> `AppDatabase`  [EXTRACTED]
  app/src/androidTest/java/com/example/myfirsttimer/data/AttendanceDaoTest.java → app/src/main/java/com/example/myfirsttimer/data/AppDatabase.java
- `FirstTimerDaoTest` --references--> `AppDatabase`  [EXTRACTED]
  app/src/androidTest/java/com/example/myfirsttimer/data/FirstTimerDaoTest.java → app/src/main/java/com/example/myfirsttimer/data/AppDatabase.java
- `MemberDaoTest` --references--> `AppDatabase`  [EXTRACTED]
  app/src/androidTest/java/com/example/myfirsttimer/data/MemberDaoTest.java → app/src/main/java/com/example/myfirsttimer/data/AppDatabase.java
- `AuthViewModel` --references--> `UsherRepository`  [EXTRACTED]
  app/src/main/java/com/example/myfirsttimer/ui/auth/AuthViewModel.java → app/src/main/java/com/example/myfirsttimer/data/repository/UsherRepository.java
- `AuthViewModel` --references--> `SessionManager`  [EXTRACTED]
  app/src/main/java/com/example/myfirsttimer/ui/auth/AuthViewModel.java → app/src/main/java/com/example/myfirsttimer/util/SessionManager.java

## Import Cycles
- None detected.

## Communities (27 total, 2 thin omitted)

### Community 0 - "UsherDao"
Cohesion: 0.17
Nodes (8): Dao, Insert, Query, Update, UsherDao, Entity, Usher, UsherRepository

### Community 1 - "Member"
Cohesion: 0.15
Nodes (12): Before, After, Before, Test, MemberDaoTest, Dao, Insert, Query (+4 more)

### Community 2 - "AuthViewModel"
Cohesion: 0.09
Nodes (21): AndroidViewModel, AuthResult, AuthViewModel, Bundle, MaterialButton, Override, TextInputEditText, TextView (+13 more)

### Community 3 - "FirstTimer"
Cohesion: 0.20
Nodes (10): FirstTimerDaoTest, After, Test, FirstTimerDao, Dao, Insert, Query, Update (+2 more)

### Community 4 - "AttendanceDao"
Cohesion: 0.17
Nodes (10): AttendanceDaoTest, After, Before, Test, AttendanceDao, Dao, Insert, Query (+2 more)

### Community 5 - "Church Attendance & First-Timer App — Design Document"
Cohesion: 0.11
Nodes (17): 10. Next Steps, 1. Overview, 2. User Roles, 3. Core User Flow, 4. Data Model, 5. Service Types, 6. Sync & Export, 7. Pastor Web Dashboard (+9 more)

### Community 6 - "AppDatabase.java"
Cohesion: 0.16
Nodes (11): AppDatabase, Context, FirstTimerDepartmentDao, Dao, Insert, Query, FirstTimerDepartment, Entity (+3 more)

### Community 7 - "ExampleInstrumentedTest.java"
Cohesion: 0.60
Nodes (3): ExampleInstrumentedTest, Test, RunWith

### Community 9 - "gradlew"
Cohesion: 0.83
Nodes (3): gradlew script, die(), warn()

### Community 10 - "Communities (26 total, 3 thin omitted)"
Cohesion: 0.06
Nodes (31): Communities (26 total, 3 thin omitted), Community 0 - "AuthViewModel", Community 12 - "v1 Requirements", Community 13 - "Design System — Church Attendance App", Community 14 - "Church Attendance & First-Timer App — Development Roadmap", Community 15 - "GSD Roadmap — Church Attendance & First-Timer App", Community 17 - "Data Field Specification — BLW KTU First Timer Card & Attendance Sheet", Community 19 - "Church Attendance & First-Timer App" (+23 more)

### Community 12 - "v1 Requirements"
Cohesion: 0.12
Nodes (15): Cloud Sync, CSV Export (Phase 7), Home Screen & Service Selection (Phase 3), Local Data Layer (Phase 1), Local Review Screens (Phase 6), Out of Scope, Pastor Web Dashboard, Project Setup (Phase 0) (+7 more)

### Community 13 - "Design System — Church Attendance App"
Cohesion: 0.13
Nodes (14): 1. Brand Grounding, 2. Color Palette, 3. Typography, 4. Spacing & Layout, 5. Component Guidance, 6. Iconography, 7. Accessibility & Practical Constraints, 8. Do / Don't Summary (+6 more)

### Community 14 - "Church Attendance & First-Timer App — Development Roadmap"
Cohesion: 0.15
Nodes (12): Church Attendance & First-Timer App — Development Roadmap, Phase 0 — Project Setup, Phase 1 — Local Data Layer (Room), Phase 2 — Usher Authentication (Local Only), Phase 3 — Home Screen & Service Selection, Phase 4 — Register Member Flow, Phase 5 — Register First Timer Flow, Phase 6 — Local Admin/Review Screens (+4 more)

### Community 15 - "GSD Roadmap — Church Attendance & First-Timer App"
Cohesion: 0.15
Nodes (12): Deferred (v2 — not in this roadmap), GSD Roadmap — Church Attendance & First-Timer App, Milestone: Offline v1 Complete, Phase 0 — Project Setup (original-roadmap Phase 0), Phase 1 — Local Data Layer (original-roadmap Phase 1), Phase 2 — Usher Authentication (original-roadmap Phase 2), Phase 3 — Home Screen & Service Selection (original-roadmap Phase 3), Phase 4 — Register Member Flow (original-roadmap Phase 4) (+4 more)

### Community 17 - "Data Field Specification — BLW KTU First Timer Card & Attendance Sheet"
Cohesion: 0.17
Nodes (11): 1. First Timer Card — Fields, 2. Attendance Sheet — Fields, 3. Service Type — Updated Enum, 4. Consolidated Data Model (updates existing entities), 5. Frontend Form Implications, 6. Backend/Sync Implications (Phase 8, Supabase), `Attendance` (Room entity — unchanged from original design), Data Field Specification — BLW KTU First Timer Card & Attendance Sheet (+3 more)

### Community 19 - "Church Attendance & First-Timer App"
Cohesion: 0.17
Nodes (11): Active, Church Attendance & First-Timer App, Constraints, Context, Core Value, Evolution, Key Decisions, Out of Scope (+3 more)

### Community 20 - "SessionManager"
Cohesion: 0.10
Nodes (18): Bundle, MaterialButton, Override, TextView, MainActivity, Bundle, Override, OnServiceSelectedListener (+10 more)

### Community 21 - "AGENTS.md — Church Attendance App"
Cohesion: 0.29
Nodes (6): 1. Required Reading, In Order, 2. Build Discipline, 3. Coding Conventions, 4. What Not to Do, 5. Quick Reference — Document Purpose Summary, AGENTS.md — Church Attendance App

### Community 22 - "Project Structure — Church Attendance App (Android, Java)"
Cohesion: 0.29
Nodes (6): build.gradle (app-level) — Core Dependencies, Directory Tree, .gitignore additions (on top of the Android template), Notes on the Layout, Phase 0 Checklist, Project Structure — Church Attendance App (Android, Java)

### Community 23 - "ChurchAttendanceApp"
Cohesion: 0.50
Nodes (3): ChurchAttendanceApp, Override, Application

### Community 24 - "Project State"
Cohesion: 0.33
Nodes (5): Completed Phases, Next Action, Open Decisions / Notes, Project Reference, Project State

### Community 26 - "Phase 3 — Home Screen & Service Selection"
Cohesion: 0.25
Nodes (7): must_haves, Phase 3 — Home Screen & Service Selection, Task 1: Extend SessionManager with service type, Task 2: Create ServiceSelectionDialog, Task 3: Update MainActivity as HomeActivity, Task 4: Wire manifest and verify build, Verification

## Knowledge Gaps
- **128 isolated node(s):** `What This Is`, `Core Value`, `Validated`, `Active`, `Out of Scope` (+123 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **2 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `SessionManager` connect `SessionManager` to `AuthViewModel`, `AppDatabase.java`?**
  _High betweenness centrality (0.088) - this node is a cross-community bridge._
- **Why does `AuthViewModel` connect `AuthViewModel` to `UsherDao`, `SessionManager`, `AppDatabase.java`?**
  _High betweenness centrality (0.047) - this node is a cross-community bridge._
- **Why does `AppDatabase` connect `AppDatabase.java` to `Member`, `AuthViewModel`, `FirstTimer`, `AttendanceDao`?**
  _High betweenness centrality (0.036) - this node is a cross-community bridge._
- **What connects `What This Is`, `Core Value`, `Validated` to the rest of the system?**
  _128 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `AuthViewModel` be split into smaller, more focused modules?**
  _Cohesion score 0.09024390243902439 - nodes in this community are weakly interconnected._
- **Should `Church Attendance & First-Timer App — Design Document` be split into smaller, more focused modules?**
  _Cohesion score 0.1111111111111111 - nodes in this community are weakly interconnected._
- **Should `Communities (26 total, 3 thin omitted)` be split into smaller, more focused modules?**
  _Cohesion score 0.0625 - nodes in this community are weakly interconnected._