# Church Attendance & First-Timer App — Development Roadmap

**Stack:** Native Android, Java, Room (local DB), Supabase (added last)
**Core principle:** The app must be fully usable offline from Phase 1 through Phase 7. CSV export is the primary output until Supabase sync is introduced as the final feature.

---

## Phase 0 — Project Setup

**Goal:** A running, empty Android project with the right foundations.

- Create Android Studio project (Java, minimum SDK per target device range — likely low-end Android phones common in Ghana, so consider API 21+)
- Set up Git repo (private, `.gitignore` set to Android template)
- Add core dependencies: Room, RecyclerView, Material Components, OpenCSV (or manual CSV writer)
- Define package structure: `data/` (Room entities, DAOs, repository), `ui/` (activities/fragments), `util/`
- Confirm app builds and runs on an emulator/device with a blank home screen

**Deliverable:** Empty app that builds and launches.

---

## Phase 1 — Local Data Layer (Room)

**Goal:** All entities from the design doc exist locally, with no cloud dependency.

- Define Room entities: `Usher`, `Member`, `Attendance`, `FirstTimer`
- Define DAOs with insert/query/update methods for each
- Add a `synced: Boolean` field on `Attendance` and `Member` (unused until Phase 8, but designed in now to avoid a schema migration later)
- Write a `AppDatabase` singleton (Room database class)
- Basic unit tests: insert/query round-trip for each entity

**Deliverable:** Local database layer that can store and retrieve all core records, verified with tests — no UI yet.

---

## Phase 2 — Usher Authentication (Local Only)

**Goal:** Ushers can both log in and register themselves, without needing internet.

- **Register screen**: new usher creates an account locally — name, username, and PIN/password, stored (hashed, e.g. with BCrypt or Android's `MessageDigest`) in the `Usher` Room table
  - Validate username uniqueness locally before saving
  - Consider whether registration should be open (any usher can self-register) or gated (e.g. requires an admin-set setup code) — worth a quick decision before building, since it affects who can create accounts on a shared device
- **Login screen**: existing usher enters username/PIN → checked against local `Usher` table
- On successful login, store the logged-in usher's ID in session (e.g. SharedPreferences) so all subsequent entries can be tagged `registered_by`
- Logout flow
- Basic "forgot PIN" isn't realistic fully offline — simplest v1 approach is an admin/reset screen that clears a specific usher's credentials locally, to be revisited once Supabase Auth exists in Phase 8

**Deliverable:** Ushers can register a new local account, log in and out, and the session persists their identity for tagging records — all without internet.

---

## Phase 3 — Home Screen & Service Selection

**Goal:** The two-button home screen, with service type context.

- On login, prompt usher to select the current **service type** (Sunday 1st/2nd, Midweek, Cell Meeting, Special Program) — stored for the session
- Home screen: two large buttons — **Register Member** / **Register First Timer**
- Basic navigation scaffolding between screens

**Deliverable:** Usher can log in, pick a service, and see the two-button home screen.

---

## Phase 4 — Register Member Flow

**Goal:** Returning members can be found and marked present, fully offline.

- Search screen: search by name or phone against local `Member` table
- On match: confirm and create an `Attendance` record (member_id, service_date, service_type, registered_by, timestamp)
- On no match: show the redirect message ("We couldn't find you — please use Register First Timer instead") with a button that jumps straight into the First Timer flow
- Return to home screen automatically after successful registration

**Deliverable:** Fully working attendance marking for existing members, offline.

---

## Phase 5 — Register First Timer Flow

**Goal:** New visitors can register themselves and be added to the database in one step.

- First Timer form: name, phone, how they heard about the church, prayer request, follow-up consent
- On submit: create a new `Member` record (`is_first_timer_origin = true`) + a `FirstTimer` record + an `Attendance` record, all in one local transaction
- Return to home screen automatically after successful registration

**Deliverable:** First-timers can be registered and are simultaneously counted as present — fully offline.

---

## Phase 6 — Local Admin/Review Screens

**Goal:** Basic visibility into the data without needing the (future) web dashboard.

- A simple in-app screen (admin/usher-accessible) showing: today's attendance count by service, list of first-timers registered today, list of all first-timers with follow-up status
- Basic follow-up status update (manual dropdown: New / Contacted / Attended again / Integrated) — kept simple until Supabase-backed dashboard exists
- Search/filter by date

**Deliverable:** The app is usable end-to-end as a standalone offline tool, with someone able to review the day's data directly on the device.

---

## Phase 7 — CSV Export (Local)

**Goal:** Data leaves the device without needing any backend.

- "Export CSV" action: generates attendance CSV and first-timers CSV for a selected date/service (or full export)
- Save to device storage, and trigger Android's Share intent so it can be sent directly via email/WhatsApp/Bluetooth
- Handle Android scoped storage requirements for the target SDK version

**Deliverable:** At this point, the app is a **complete, standalone, offline attendance system** — usable in real church services with zero internet dependency, exportable and shareable via CSV.

> **Milestone: this is a legitimate v1 release point.** Everything past this phase is enhancement, not a requirement for the app to be useful.

---

## Phase 8 — Supabase Integration (Final Phase)

**Goal:** Add cloud sync without breaking the offline-first behavior already built.

1. **Schema design in Supabase (Postgres)** — mirror the Room schema (`ushers`, `members`, `attendance`, `first_timers`), with proper foreign keys
2. **Supabase Auth** — migrate usher login from local-only to Supabase Auth (keep local fallback for offline login — cache last successful auth locally so ushers can still log in without connectivity)
3. **Sync engine**:
   - Background/manual "Sync Now" trigger (per your original request, ideally end-of-service)
   - Push any local records where `synced = false` to Supabase
   - Mark records `synced = true` on success
   - Basic retry/conflict handling (e.g. last-write-wins is fine for v1 given the usher-controlled entry model)
4. **Secrets handling** — Supabase URL/anon key in `local.properties` or `secrets.gradle`, excluded via `.gitignore`
5. **Testing**: airplane-mode test (full offline flow still works), then reconnect and confirm sync catches up correctly

**Deliverable:** The app now syncs to Supabase, without ever having required it to function.

---

## Phase 9 — Pastor Web Dashboard (Post-Sync)

**Goal:** Build the web-facing view now that Supabase is the source of truth for synced data.

- Simple web app (React or plain HTML/JS) reading from Supabase via its auto-generated REST API or JS client
- Pastor login via Supabase Auth (separate role from ushers)
- Views: attendance totals by service/date, first-timer list with follow-up status, filters
- CSV export button pulling from Supabase (mirrors the local export, now for the full synced dataset)

**Deliverable:** Full system as originally scoped — offline-capable mobile app + cloud sync + pastor dashboard.

---

## Summary Timeline

| Phase | Focus | Requires internet? |
|---|---|---|
| 0 | Project setup | No |
| 1 | Local data layer (Room) | No |
| 2 | Usher login (local) | No |
| 3 | Home screen & service selection | No |
| 4 | Register Member flow | No |
| 5 | Register First Timer flow | No |
| 6 | Local admin/review screens | No |
| 7 | CSV export | No — **v1 release point** |
| 8 | Supabase sync | Yes (added last, as required) |
| 9 | Pastor web dashboard | Yes (depends on Phase 8) |
