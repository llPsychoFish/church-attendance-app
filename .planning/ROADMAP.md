# GSD Roadmap — Church Attendance & First-Timer App

**Created:** 2026-08-18
**Scope:** Offline v1 only — roadmap Phases 0–7. Supabase sync (Phase 8) and the pastor web dashboard (Phase 9) are **explicitly deferred** to v2 (see REQUIREMENTS.md).
**Source of truth:** `docs/original-roadmap.md` + `AGENTS.md` (native Android, Java, Room, local-first). The older `docs/attendance-app-design.md` (React Native + Firebase) is superseded.

**Hard constraints (from AGENTS.md / roadmap core principle):**
- No network dependency, internet permission, or cloud SDK through Phase 7.
- Java only; Room only for persistence; repository pattern between DAOs and ViewModels.
- Entity/field names must match `docs/data-field-spec.md` exactly.
- UI from `docs/design.md` only — no new colors, no dark mode, uppercase button text, icon-only primary buttons.
- Service types `SUN` / `WED` / `FRI` / `CELL` and the department list live in `util/Constants.java`.

**Granularity:** Standard (8 phases). Each GSD phase maps 1:1 to a roadmap phase and contains 3–5 plans.

---

## Phase 1 — Project Setup (roadmap Phase 0)
**Objective:** A running, empty Android project with the right foundations.
**Plans:**
1. Create Android Studio project (Java, min SDK 21+) and confirm it builds/launches to a blank activity.
2. Add core dependencies (Room, ViewModel/LiveData, Material, RecyclerView, OpenCSV) to `app/build.gradle`.
3. Create package structure per `docs/project-structure.md` (empty folders/placeholders; `data/remote/` with `.gitkeep`).
4. Add `AppDatabase` Room shell (no entities) and confirm it builds.
5. Set `.gitignore` to Android template + `local.properties` / `secrets.properties`.
**Requirements:** SETUP-01..05
**Verification:** App installs and opens to a blank screen; `./gradlew build` passes; package tree matches project-structure.md.
**Dependencies:** none

## Phase 2 — Local Data Layer (roadmap Phase 1)
**Objective:** All entities exist locally with no cloud dependency; verified with tests.
**Plans:**
1. Define Room entities matching `docs/data-field-spec.md`: `Usher`, `Member`, `Attendance`, `FirstTimer`, `FirstTimerDepartment` (with FKs).
2. Add `synced` boolean to `Attendance` and `Member` (designed in now, unused until Phase 8).
3. Write DAOs with insert/query/update for each entity.
4. Wire `AppDatabase` singleton with all entities.
5. Unit tests: insert/query round-trip for each entity.
**Requirements:** DATA-01..05
**Verification:** All round-trip tests pass; schema matches field spec exactly (no renamed fields).
**Dependencies:** Phase 1

## Phase 3 — Usher Authentication (roadmap Phase 2)
**Objective:** Ushers register and log in locally, session persists identity for tagging.
**Plans:**
1. Register screen: name, username, PIN/password hashed (BCrypt or `MessageDigest`) into `Usher` table.
2. Enforce username uniqueness locally before save.
3. Login screen: verify username + PIN against local `Usher` table.
4. `SessionManager` stores logged-in usher ID (SharedPreferences) → tags `registered_by`.
5. Logout flow + local credential reset/clear screen (offline-friendly).
**Requirements:** AUTH-01..06
**Verification:** New usher can register, log in/out; duplicate username rejected; subsequent records carry `registered_by`.
**Dependencies:** Phase 2

## Phase 4 — Home Screen & Service Selection (roadmap Phase 3)
**Objective:** Two-button home screen with service-type context.
**Plans:**
1. On login, prompt service type (SUN/WED/FRI/CELL) via `ServiceSelectionDialog`, stored for session.
2. `HomeActivity` two large buttons: Register Member / Register First Timer.
3. Navigation scaffolding between screens (no feature logic yet).
**Requirements:** HOME-01..03
**Verification:** Logged-in usher selects a service, sees two buttons, navigates between shells.
**Dependencies:** Phase 3

## Phase 5 — Register Member Flow (roadmap Phase 4)
**Objective:** Returning members found and marked present, fully offline.
**Plans:**
1. `RegisterMemberActivity` search by name or phone against local `Member` table.
2. On match: create `Attendance` record (`member_id`, `service_date`, `service_type`, `registered_by`, `timestamp`).
3. On no match: redirect message + button jumping into the First Timer flow.
4. Auto-return to home after success.
**Requirements:** MEMB-01..04
**Verification:** Known member marked present; unknown search shows redirect and opens First Timer; attendance row persisted.
**Dependencies:** Phase 4

## Phase 6 — Register First Timer Flow (roadmap Phase 5)
**Objective:** New visitors register themselves and are counted present in one step.
**Plans:**
1. `RegisterFirstTimerActivity` form matching field spec §1 (surname, first name, course of study, level, hall/hostel, room no, DOB, email, phone, invited by, born again, speaks in tongues, wants membership, prayer request, department checkboxes).
2. Department multi-select stored via `FirstTimerDepartment` join rows.
3. On submit: single transaction creates `Member` (`is_first_timer_origin = true`) + `FirstTimer` + `Attendance`.
4. Auto-return to home after success.
**Requirements:** FTMR-01..03
**Verification:** Submitting creates the three linked records atomically; fields match spec; home returns.
**Dependencies:** Phase 4

## Phase 7 — Local Review Screens (roadmap Phase 6)
**Objective:** Usable end-to-end as a standalone offline tool with on-device review.
**Plans:**
1. `ReviewDashboardActivity` (any logged-in usher): today's attendance count by service.
2. List of first-timers registered today.
3. List of all first-timers with follow-up status.
4. Follow-up status update (New / Contacted / Attended again / Integrated).
5. Search/filter by date.
**Requirements:** REVW-01..05
**Verification:** Counts and lists render from local data; status updates persist; date filter works.
**Dependencies:** Phase 5, Phase 6

## Phase 8 — CSV Export (roadmap Phase 7)  ◀ v1 release point
**Objective:** Data leaves the device with no backend.
**Plans:**
1. "Export CSV" generates attendance + first-timers CSV for selected date/service or full export (`CsvWriter`).
2. Save to device storage handling scoped storage for target SDK.
3. Trigger Android Share intent (email / WhatsApp / Bluetooth).
**Requirements:** EXPR-01..03
**Verification:** Export produces correct CSV; share sheet opens; works in airplane mode.
**Dependencies:** Phase 7

---

## Milestone: Offline v1 Complete
At the end of Phase 8 the app is a **complete, standalone, offline attendance system** — usable in real services with zero internet, exportable and shareable via CSV. Everything after this (Supabase sync, web dashboard) is v2 enhancement.

## Deferred (v2 — not in this roadmap)
- **Phase 9** Supabase Integration (sync engine, Supabase Auth, secrets handling)
- **Phase 10** Pastor Web Dashboard (React/plain JS reading Supabase)
- Admin role, member-facing login, auto follow-up assignment

## Phase Dependencies (visual)
```
P1 Setup → P2 Data → P3 Auth → P4 Home → P5 Member ┐
                                                    ├→ P7 Review → P8 CSV
                                  P4 Home → P6 FirstTimer ┘
```
