# Requirements: Church Attendance & First-Timer App

**Defined:** 2026-08-18
**Core Value:** A complete, usable, offline-first attendance system through Phase 7 — recording members and first-timers and exporting CSV with zero internet dependency.

> Scope: v1 = offline system (roadmap Phases 0–7). Supabase sync (Phase 8) and the pastor web dashboard (Phase 9) are deferred to v2 and tracked below.

## v1 Requirements

### Project Setup (Phase 0)

- [ ] **SETUP-01**: Android project (Java, min SDK 21+) builds and launches to a blank activity
- [ ] **SETUP-02**: Core dependencies added — Room, ViewModel/LiveData, Material Components, RecyclerView, OpenCSV
- [ ] **SETUP-03**: Package structure created per `docs/project-structure.md` (`data/entity`, `data/dao`, `data/repository`, `data/remote` placeholder, `ui/<feature>`, `util/`)
- [ ] **SETUP-04**: `AppDatabase` Room singleton shell builds (no entities yet)
- [ ] **SETUP-05**: `.gitignore` set to Android template + `local.properties` / `secrets.properties` exclusions

### Local Data Layer (Phase 1)

- [ ] **DATA-01**: Room entities defined matching `docs/data-field-spec.md` exactly — `Usher`, `Member`, `Attendance`, `FirstTimer`, `FirstTimerDepartment`
- [ ] **DATA-02**: DAOs with insert/query/update for each entity
- [ ] **DATA-03**: `synced` boolean present on `Attendance` and `Member` (designed in now, unused until Phase 8)
- [ ] **DATA-04**: `AppDatabase` singleton configured with all entities + migrations off
- [ ] **DATA-05**: Unit tests — insert/query round-trip for each entity

### Usher Authentication (Phase 2)

- [ ] **AUTH-01**: Ushers can register a local account (name, username, PIN/password) stored hashed in `Usher` table
- [ ] **AUTH-02**: Username uniqueness validated locally before saving
- [ ] **AUTH-03**: Ushers can log in (username + PIN) checked against local `Usher` table
- [ ] **AUTH-04**: Logged-in usher ID stored in session (SharedPreferences) and tags every record as `registered_by`
- [ ] **AUTH-05**: Ushers can log out
- [ ] **AUTH-06**: Local credential reset/clear screen (offline-friendly; full recovery deferred to Phase 8)

### Home Screen & Service Selection (Phase 3)

- [ ] **HOME-01**: On login, usher selects current service type (SUN / WED / FRI / CELL) stored for the session
- [ ] **HOME-02**: Two large buttons — Register Member / Register First Timer
- [ ] **HOME-03**: Navigation scaffolding between screens

### Register Member Flow (Phase 4)

- [ ] **MEMB-01**: Search by name or phone against local `Member` table
- [ ] **MEMB-02**: On match, create `Attendance` record (`member_id`, `service_date`, `service_type`, `registered_by`, `timestamp`)
- [ ] **MEMB-03**: On no match, show redirect message ("We couldn't find you — please use Register First Timer instead") with a jump into the First Timer flow
- [ ] **MEMB-04**: Return to home screen automatically after successful registration

### Register First Timer Flow (Phase 5)

- [ ] **FTMR-01**: First Timer form matches `docs/data-field-spec.md` §1 field-for-field — surname, first name, course of study, level, hall/hostel, room no, date of birth, email, phone, invited by, born again, speaks in tongues, wants membership, prayer request, department multi-select (checkboxes)
- [ ] **FTMR-02**: On submit, in one local transaction create `Member` (`is_first_timer_origin = true`) + `FirstTimer` + `Attendance` record
- [ ] **FTMR-03**: Return to home screen automatically after successful registration

### Local Review Screens (Phase 6)

- [ ] **REVW-01**: In-app screen (any logged-in usher, no admin role) shows today's attendance count by service
- [ ] **REVW-02**: List of first-timers registered today
- [ ] **REVW-03**: List of all first-timers with follow-up status
- [ ] **REVW-04**: Follow-up status update (New / Contacted / Attended again / Integrated)
- [ ] **REVW-05**: Search/filter by date

### CSV Export (Phase 7)

- [ ] **EXPR-01**: "Export CSV" generates attendance + first-timers CSV for a selected date/service or full export
- [ ] **EXPR-02**: Save to device storage handling Android scoped storage for target SDK
- [ ] **EXPR-03**: Trigger Android Share intent (email / WhatsApp / Bluetooth)

## v2 Requirements (Deferred)

### Cloud Sync

- **SYNC-01**: Supabase schema mirroring Room entities with foreign keys
- **SYNC-02**: Supabase Auth with local offline fallback
- **SYNC-03**: Sync engine pushing `synced = false` records, marking `synced = true` on success

### Pastor Web Dashboard

- **DASH-01**: Web app reading from Supabase with pastor login
- **DASH-02**: Views — attendance totals by service/date, first-timer list with follow-up, filters
- **DASH-03**: CSV export from synced dataset

## Out of Scope

| Feature | Reason |
|---------|--------|
| Supabase sync (Phase 8) | Deferred to v2; `synced` flags designed in now to avoid migration |
| Pastor web dashboard (Phase 9) | Deferred to v2; depends on Supabase as source of truth |
| Admin role | Excluded per AGENTS.md; review screens open to any logged-in usher |
| Member-facing login | Excluded (v1); congregants use usher's phone momentarily |
| React Native / Firebase stack | Superseded by native Android + Java + Room per roadmap/AGENTS.md |
| Auto follow-up assignment | Future; manual status update only in v1 |

## Traceability

| Requirement | Roadmap Phase | GSD Phase | Status |
|-------------|---------------|-----------|--------|
| SETUP-01..05 | 0 | — | Pending |
| DATA-01..05 | 1 | — | Pending |
| AUTH-01..06 | 2 | — | Pending |
| HOME-01..03 | 3 | — | Pending |
| MEMB-01..04 | 4 | — | Pending |
| FTMR-01..03 | 5 | — | Pending |
| REVW-01..05 | 6 | — | Pending |
| EXPR-01..03 | 7 | — | Pending |

**Coverage:**
- v1 requirements: 35 total
- Mapped to roadmap phases: 35
- Unmapped: 0 ✓

---
*Requirements defined: 2026-08-18*
*Last updated: 2026-08-18 after initialization*
