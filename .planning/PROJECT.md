# Church Attendance & First-Timer App

## What This Is

A native Android app that lets church ushers register attendance and first-time visitors during a service, on a single shared device, with no internet required. An usher logs in, selects the service, then hands the phone to a congregant who self-registers as a returning member or a first-time visitor. Data lives locally on the device and can be exported as CSV; cloud sync and a pastor dashboard are explicitly deferred.

## Core Value

The app must be a complete, usable, **offline-first** attendance system through Phase 7 — recording members and first-timers and exporting CSV with zero internet dependency.

## Requirements

### Validated

(None yet — ship to validate)

### Active

- [ ] Ushers can register a local account and log in/out without internet
- [ ] Usher session persists identity so every record is tagged `registered_by`
- [ ] Usher selects the current service type for the session
- [ ] Two-button home screen: Register Member / Register First Timer
- [ ] Returning members can be found (name/phone) and marked present offline
- [ ] Unmatched member search redirects into the First Timer flow
- [ ] First-timers register in one step → creates Member + FirstTimer + Attendance records
- [ ] Local review screens show attendance counts, first-timer lists, and follow-up status
- [ ] CSV export (attendance + first-timers) saved to device and shareable

### Out of Scope

- **Supabase sync (Phase 8)** — deferred; `synced` flags designed into schema now to avoid a later migration, but no network calls until then. *(Per AGENTS.md hard rule: no cloud dependency before Phase 8.)*
- **Pastor web dashboard (Phase 9)** — deferred; depends on Supabase as source of truth.
- **Admin role** — excluded; review screens are accessible to any logged-in usher, no `isAdmin` flag. *(Per AGENTS.md.)*
- **React Native / Firebase stack** — excluded; the original design doc proposed this but the active roadmap/AGENTS.md chose native Android + Java + Room. The design doc is superseded.
- **Member-facing login** — excluded (v1): congregants use the usher's phone momentarily, no personal accounts.

## Context

- **Project type:** Christ Embassy / BLW KTU member attendance & first-timer registration.
- **Model:** Usher-as-intermediary — one logged-in device handed between congregants.
- **Stack (authoritative):** Native Android, **Java** (not Kotlin), Room for local persistence, repository pattern between DAOs and ViewModels.
- **Forms mirror physical cards:** field order/labels in the Member and First Timer flows follow the paper BLW KTU First Timer Card / Attendance Sheet (see `docs/data-field-spec.md`).
- **Repo state:** Currently an Android scaffold only (template `MainActivity`); no feature code yet. Design docs present: `AGENTS.md`, `docs/original-roadmap.md`, `docs/data-field-spec.md`, `docs/design.md`, `docs/project-structure.md`.
- **Known divergence:** `docs/attendance-app-design.md` describes a different stack (React Native + Firebase + admin role) and is superseded by the roadmap/AGENTS.md.

## Constraints

- **Local-first:** No network dependency, internet permission, or cloud SDK through Phase 7. — Hard rule from AGENTS.md / roadmap core principle.
- **Language:** Java only — project choice, keep all new code in Java.
- **Persistence:** Room exclusively — no other local DB library.
- **Naming:** Entity/field names must match `docs/data-field-spec.md` exactly (e.g. `courseOfStudy`, `hallHostel`, `roomNo`). Don't rename for style.
- **Design system:** All UI colors/typography/spacing from `docs/design.md`; no new colors, no dark mode, uppercase button text, icon-only primary buttons.
- **Target devices:** Low-end Android phones common in Ghana — consider API 21+ minimum SDK.
- **Constants:** Service types (`SUN`, `WED`, `FRI`, `CELL`) and department list live in `util/Constants.java`.

## Key Decisions

| Decision | Rationale | Outcome |
|----------|-----------|---------|
| Native Android + Java + Room, not React Native/Firebase | Active roadmap + AGENTS.md supersede original design doc; offline-first is simpler with a local DB and no backend | — Pending |
| No admin role | Review screens open to any logged-in usher; avoids a permission model the paper flow doesn't need | — Pending |
| Scope cut at Phase 7 (offline v1) | App is a legitimate standalone release at Phase 7; cloud is enhancement | — Pending |
| `synced` flags designed into schema at Phase 1 | Avoid a Room migration when Supabase sync lands in Phase 8 | — Pending |

## Evolution

This document evolves at phase transitions and milestone boundaries.

**After each phase transition** (via `/gsd-transition`):
1. Requirements invalidated? → Move to Out of Scope with reason
2. Requirements validated? → Move to Validated with phase reference
3. New requirements emerged? → Add to Active
4. Decisions to log? → Add to Key Decisions
5. "What This Is" still accurate? → Update if drifted

**After each milestone** (via `/gsd-complete-milestone`):
1. Full review of all sections
2. Core Value check — still the right priority?
3. Audit Out of Scope — reasons still valid?
4. Update Context with current state

---
*Last updated: 2026-08-18 after initialization*
