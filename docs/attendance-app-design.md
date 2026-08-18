# Church Attendance & First-Timer App — Design Document

**Project type:** Christ Embassy / BLW member attendance & first-timer registration system
**Platform:** Mobile app (Android/iOS) — React Native (Expo) + Web dashboard
**Backend:** Firebase (Auth, Firestore, Cloud Storage)
**Author:** Arnold
**Status:** Draft v1

---

## 1. Overview

A mobile-first attendance system for church services, designed around an **usher-as-intermediary** model. Ushers carry the app on their own logged-in device and hand it to congregants during service to self-register — either as a returning member or as a first-time visitor. Data syncs to a Firebase backend and is viewable by pastors through a separate secured web dashboard, with CSV export available as a fallback.

### Goals

- Replace paper attendance registers with a fast, structured digital flow
- Distinguish clearly between returning members and first-time visitors at the point of entry
- Give pastors real-time visibility into attendance and first-timer follow-up without needing app access themselves
- Work reliably in low-connectivity church environments (offline-first)

### Non-goals (v1)

- No member-facing login or personal accounts
- No pastor access within the mobile app itself (web dashboard only)
- No payment/giving tracking

---

## 2. User Roles

| Role | Access | Notes |
|---|---|---|
| **Usher / Registrar** | Mobile app, individual login | Logs in once, hands phone to congregants during service. Entries are traceable to the usher who registered them. |
| **Congregant (member or first-timer)** | No login — uses the usher's phone momentarily | Self-selects "Register Member" or "Register First Timer" and fills their own form |
| **Pastor / Admin** | Web dashboard, separate login | Read access to attendance data, first-timer list, and follow-up status. Not part of the mobile app. |

---

## 3. Core User Flow

1. Usher opens the app and logs in.
2. Usher selects the current **service type** (see §5) for the session.
3. Home screen shows two large buttons:
   - **Register Member**
   - **Register First Timer**
4. Usher hands the phone to a congregant.
5. Congregant taps the button matching their status and fills the form themselves.
6. On submit, the app returns automatically to the two-button home screen, ready for the next person.
7. **Register Member flow**: congregant searches their name/phone → matched against the existing member database → marked present.
   - If no match is found, the app shows a friendly message: *"We couldn't find you in our records — please use Register First Timer instead."* This is a safety net, not a hard block — the two sections stay conceptually separate, but mistakes get caught.
8. **Register First Timer flow**: congregant fills a short form (name, phone, how they heard about the church, prayer request, follow-up consent) → this creates a new member record **and** logs them present for the current service in one action.

---

## 4. Data Model

### `ushers`
| Field | Type | Notes |
|---|---|---|
| id | string | Firebase Auth UID |
| name | string | |
| role | enum | usher / admin |
| created_at | timestamp | |

### `members`
| Field | Type | Notes |
|---|---|---|
| id | string | |
| name | string | |
| phone | string | Primary identifier where available |
| email | string | Optional |
| join_date | timestamp | Set on first registration |
| is_first_timer_origin | boolean | True if they entered via the First Timer flow |
| cell_zone | string | Optional, for follow-up assignment (future) |

### `attendance`
| Field | Type | Notes |
|---|---|---|
| id | string | |
| member_id | string | FK → members |
| service_date | date | |
| service_type | enum | See §5 |
| registered_by | string | FK → ushers |
| timestamp | timestamp | |
| synced | boolean | For offline-first tracking |

### `first_timers`
| Field | Type | Notes |
|---|---|---|
| id | string | |
| member_id | string | FK → members |
| source | enum | invited / social media / walk-in / other |
| prayer_request | string | Optional |
| follow_up_consent | boolean | |
| follow_up_status | enum | New / Contacted / Attended again / Integrated |
| assigned_to | string | Optional, FK → cell leader (future) |

---

## 5. Service Types

Attendance records are tagged by service type so the dashboard can break down numbers accordingly:

- Sunday 1st Service
- Sunday 2nd Service
- Sunday 3rd Service (if applicable)
- Midweek Service
- Cell / House Fellowship Meeting
- Special Program (Communion, Global Event, Crusade, etc.)

---

## 6. Sync & Export

- **Offline-first**: all entries are captured to local storage first, regardless of connectivity.
- **Sync**: entries push to Firestore automatically when online; a manual **"Sync Now"** action is available, intended to be used at the end of each service.
- **CSV export**: available from the web dashboard (and optionally from the app) as a backup or for sharing outside the dashboard — e.g., via email or WhatsApp.

---

## 7. Pastor Web Dashboard

- Separate web app, own secured login (not shared with usher accounts)
- Views:
  - Attendance totals by service/date
  - First-timer list with follow-up status
  - Search/filter by name, date, or service type
- CSV export button for any filtered view

---

## 8. Open Questions (to resolve before/during build)

1. **First-timer follow-up assignment** — auto-assign to nearest cell/zone leader, manual assignment by pastor, or skip structured follow-up tracking for v1?
2. **Member identity edge cases** — members without phone numbers (children, elderly): fall back to name + date of birth, or introduce a printed member ID/QR code for repeat scanning?
3. **Duplicate handling** — how to merge or flag potential duplicate member records (e.g., same person registered twice with slightly different name spelling)?

---

## 9. Tech Stack Summary

| Layer | Choice |
|---|---|
| Mobile app | React Native (Expo) |
| Backend | Firebase (Firestore, Auth, Cloud Storage) |
| Web dashboard | React (web), Firebase Auth for pastor login |
| Offline storage | Local device storage (e.g. SQLite or Expo's async storage), synced to Firestore |
| Export | CSV generation from Firestore data |

---

## 10. Next Steps

- Resolve open questions in §8
- Define wireframes for the two-button home screen and both registration forms
- Set up Firebase project structure (collections per §4)
- Build usher auth flow
- Build web dashboard shell with pastor auth
