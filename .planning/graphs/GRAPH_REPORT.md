# Graph Report - church-attendance-app  (2026-08-18)

## Corpus Check
- 53 files · ~92,493 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 458 nodes · 816 edges · 29 communities (27 shown, 2 thin omitted)
- Extraction: 89% EXTRACTED · 11% INFERRED · 0% AMBIGUOUS · INFERRED: 88 edges (avg confidence: 0.8)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `7987baa7`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- AppDatabase.java
- Member
- MemberViewModel.java
- FirstTimer
- Attendance
- Church Attendance & First-Timer App — Design Document
- FirstTimerDepartmentDao
- ExampleInstrumentedTest.java
- ExampleUnitTest.java
- gradlew
- Communities (27 total, 2 thin omitted)
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
- MemberSearchAdapter
- Phase 4 — Register Member Flow

## God Nodes (most connected - your core abstractions)
1. `Member` - 37 edges
2. `Communities (27 total, 2 thin omitted)` - 23 edges
3. `SessionManager` - 22 edges
4. `Attendance` - 21 edges
5. `MemberDao` - 17 edges
6. `AttendanceDao` - 16 edges
7. `AuthViewModel` - 15 edges
8. `RegisterMemberActivity` - 15 edges
9. `MainActivity` - 14 edges
10. `AppDatabase` - 14 edges

## Surprising Connections (you probably didn't know these)
- `AttendanceDaoTest` --references--> `AppDatabase`  [EXTRACTED]
  app/src/androidTest/java/com/example/myfirsttimer/data/AttendanceDaoTest.java → app/src/main/java/com/example/myfirsttimer/data/AppDatabase.java
- `FirstTimerDaoTest` --references--> `AppDatabase`  [EXTRACTED]
  app/src/androidTest/java/com/example/myfirsttimer/data/FirstTimerDaoTest.java → app/src/main/java/com/example/myfirsttimer/data/AppDatabase.java
- `MemberDaoTest` --references--> `AppDatabase`  [EXTRACTED]
  app/src/androidTest/java/com/example/myfirsttimer/data/MemberDaoTest.java → app/src/main/java/com/example/myfirsttimer/data/AppDatabase.java
- `MemberSearchAdapter` --references--> `Member`  [EXTRACTED]
  app/src/main/java/com/example/myfirsttimer/ui/member/MemberSearchAdapter.java → app/src/main/java/com/example/myfirsttimer/data/entity/Member.java
- `MemberViewModel` --references--> `Member`  [EXTRACTED]
  app/src/main/java/com/example/myfirsttimer/ui/member/MemberViewModel.java → app/src/main/java/com/example/myfirsttimer/data/entity/Member.java

## Import Cycles
- None detected.

## Communities (29 total, 2 thin omitted)

### Community 0 - "AppDatabase.java"
Cohesion: 0.07
Nodes (22): AppDatabase, Context, Dao, Insert, Query, Update, UsherDao, Entity (+14 more)

### Community 1 - "Member"
Cohesion: 0.12
Nodes (14): Before, After, Before, Test, MemberDaoTest, Dao, Insert, Query (+6 more)

### Community 2 - "MemberViewModel.java"
Cohesion: 0.29
Nodes (5): AndroidViewModel, LiveData, MutableLiveData, MemberViewModel, Bundle

### Community 3 - "FirstTimer"
Cohesion: 0.17
Nodes (11): FirstTimerDaoTest, After, Before, Test, FirstTimerDao, Dao, Insert, Query (+3 more)

### Community 4 - "Attendance"
Cohesion: 0.14
Nodes (10): AttendanceDaoTest, After, Test, AttendanceDao, Dao, Insert, Query, Attendance (+2 more)

### Community 5 - "Church Attendance & First-Timer App — Design Document"
Cohesion: 0.11
Nodes (17): 10. Next Steps, 1. Overview, 2. User Roles, 3. Core User Flow, 4. Data Model, 5. Service Types, 6. Sync & Export, 7. Pastor Web Dashboard (+9 more)

### Community 6 - "FirstTimerDepartmentDao"
Cohesion: 0.29
Nodes (6): FirstTimerDepartmentDao, Dao, Insert, Query, FirstTimerDepartment, Entity

### Community 7 - "ExampleInstrumentedTest.java"
Cohesion: 0.60
Nodes (3): ExampleInstrumentedTest, Test, RunWith

### Community 9 - "gradlew"
Cohesion: 0.83
Nodes (3): gradlew script, die(), warn()

### Community 10 - "Communities (27 total, 2 thin omitted)"
Cohesion: 0.06
Nodes (33): Communities (27 total, 2 thin omitted), Community 0 - "UsherDao", Community 10 - "Communities (26 total, 3 thin omitted)", Community 12 - "v1 Requirements", Community 13 - "Design System — Church Attendance App", Community 14 - "Church Attendance & First-Timer App — Development Roadmap", Community 15 - "GSD Roadmap — Church Attendance & First-Timer App", Community 17 - "Data Field Specification — BLW KTU First Timer Card & Attendance Sheet" (+25 more)

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
Cohesion: 0.07
Nodes (34): Bundle, MaterialButton, Override, TextView, MainActivity, Bundle, MaterialButton, Override (+26 more)

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

### Community 27 - "MemberSearchAdapter"
Cohesion: 0.18
Nodes (11): Adapter, MaterialButton, Override, TextView, View, ViewGroup, MemberSearchAdapter, OnMarkPresentListener (+3 more)

### Community 28 - "Phase 4 — Register Member Flow"
Cohesion: 0.20
Nodes (9): must_haves, Phase 4 — Register Member Flow, Task 1: Create MemberRepository, Task 2: Create AttendanceRepository, Task 3: Create MemberSearchAdapter, Task 4: Create MemberViewModel, Task 5: Create RegisterMemberActivity, Task 6: Wire navigation and manifest (+1 more)

## Knowledge Gaps
- **138 isolated node(s):** `What This Is`, `Core Value`, `Validated`, `Active`, `Out of Scope` (+133 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **2 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `Member` connect `Member` to `AppDatabase.java`, `MemberViewModel.java`, `FirstTimer`, `Attendance`, `SessionManager`, `MemberSearchAdapter`?**
  _High betweenness centrality (0.087) - this node is a cross-community bridge._
- **Why does `SessionManager` connect `SessionManager` to `AppDatabase.java`, `MemberViewModel.java`?**
  _High betweenness centrality (0.065) - this node is a cross-community bridge._
- **Why does `AuthViewModel` connect `AppDatabase.java` to `MemberViewModel.java`, `SessionManager`?**
  _High betweenness centrality (0.037) - this node is a cross-community bridge._
- **Are the 7 inferred relationships involving `Member` (e.g. with `.setUp()` and `.setUp()`) actually correct?**
  _`Member` has 7 INFERRED edges - model-reasoned connections that need verification._
- **Are the 3 inferred relationships involving `Attendance` (e.g. with `.countByDateAndService_countsCorrectly()` and `.insertAndQuery_roundTrips()`) actually correct?**
  _`Attendance` has 3 INFERRED edges - model-reasoned connections that need verification._
- **What connects `What This Is`, `Core Value`, `Validated` to the rest of the system?**
  _138 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `AppDatabase.java` be split into smaller, more focused modules?**
  _Cohesion score 0.07337662337662337 - nodes in this community are weakly interconnected._