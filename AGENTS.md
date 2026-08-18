# AGENTS.md — Church Attendance App

**Read this file first, every session.** It tells you (the AI coding assistant) which other documents in this repo to read, in what order, and how to use them before writing or modifying any code.

---

## 1. Required Reading, In Order

Before generating or editing any code, read these files in full:

1. **`docs/design-doc.md`** — the product design document. Explains *what* this app is: the usher-as-intermediary model, the two-flow structure (Register Member / Register First Timer), user roles, the sync/export model, and the overall system (mobile app + Supabase + web dashboard). Read this to understand *why* the app is built the way it is before touching any feature.
2. **`ROADMAP.md`** — the phased build plan. **This is the most important constraint in the whole project.** The app must be fully functional offline through Phase 7 (local data, local auth, both registration flows, local review screens, CSV export). Supabase sync (Phase 8) and the web dashboard (Phase 9) come strictly last. Never introduce a network dependency, a Supabase import, or any remote API call before Phase 8 is explicitly reached — check which phase is currently active before writing code, and if unclear, ask rather than assume.
3. **`docs/data-field-spec.md`** — the authoritative schema. Every entity field (Member, FirstTimer, FirstTimerDepartment, Attendance, Usher) must match this spec exactly, including field names and types. This spec was derived directly from the physical BLW KTU First Timer Card and Attendance Sheet — do not add, remove, or rename fields without checking this document first, since mismatches break the paper-to-app mapping the whole design relies on.
4. **`docs/project-structure.md`** — the package/folder layout. All new files go in the location this document specifies (`data/entity`, `data/dao`, `data/repository`, `ui/<feature>`, `util/`). Don't invent new top-level packages or reorganize existing ones without updating this document to match.
5. **`docs/design.md`** — the UI/visual design system. Colors, typography, spacing, and component rules. Every layout, color resource, and style must come from this document. Don't introduce new colors, use uppercase button text, icon-only primary buttons, or dark mode — these are explicitly ruled out. If a new color or component pattern is genuinely needed, add it to `design.md` first, then use it.

---

## 2. Build Discipline

- **Check the current phase before building anything.** If it's unclear which roadmap phase is active, ask rather than guess — building ahead of the current phase (especially anything Supabase-related before Phase 8) violates the core project constraint.
- **Local-first, always.** Every feature through Phase 7 must work with zero network connectivity. Do not add internet permission checks, network calls, or cloud SDK dependencies until Phase 8.
- **Match the physical forms.** Field order, labels, and structure in the First Timer and Attendance flows should mirror the paper cards described in `data-field-spec.md` — this isn't just a schema reference, it also dictates form UI layout (§5 of `design.md` reinforces this: form field order should match the physical card).
- **One phase, one feature set.** Don't implement Phase 5 (First Timer flow) while Phase 4 (Register Member flow) is incomplete, and don't scaffold Phase 8/9 folders with real logic ahead of time — `data/remote/` exists as a placeholder only until Phase 8.

---

## 3. Coding Conventions

- **Language:** Java (not Kotlin) — this is a deliberate project choice, keep all new code in Java.
- **Persistence:** Room for all local storage. No other local database library.
- **Architecture:** Repository pattern between DAOs and ViewModels (`data/repository/`), even though the app is small — this is intentional so Phase 8 can swap local-only repositories for Supabase-backed ones without rewriting UI/ViewModel code.
- **Naming:** Match the exact entity/field names in `data-field-spec.md` (e.g. `courseOfStudy`, `hallHostel`, `roomNo`) — don't rename for style preferences.
- **Constants:** Service types (`SUN`, `WED`, `FRI`, `CELL`) and department list live in `util/Constants.java` — reference them from there, don't hardcode string literals for these values in UI code.

---

## 4. What Not to Do

- Don't add a Supabase dependency, API client, or network permission before Phase 8.
- Don't introduce a separate "admin" role — the `review/` screens (Phase 6) are accessible to any logged-in usher, there is no `isAdmin` flag on `Usher`.
- Don't change the service type enum, department list, or any Member/FirstTimer field without first updating `docs/data-field-spec.md` to match — code and spec must never drift apart.
- Don't add colors, fonts, or UI patterns not defined in `docs/design.md`.
- Don't skip ahead in the roadmap because a later feature seems easy to add early — the phase order exists specifically so the app has a usable offline release point (end of Phase 7) before any cloud dependency is introduced.

---

## 5. Quick Reference — Document Purpose Summary

| File | Answers |
|---|---|
| `docs/design-doc.md` | What is this app, and why does it work this way? |
| `ROADMAP.md` | What phase are we in, and what's allowed right now? |
| `docs/data-field-spec.md` | What fields exist, and what do they map to on the paper forms? |
| `docs/project-structure.md` | Where does this code go? |
| `docs/design.md` | What should this look like? |
| `AGENTS.md` (this file) | How do all of the above fit together, and what are the hard rules? |
