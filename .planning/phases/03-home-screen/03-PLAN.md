# Phase 3 — Home Screen & Service Selection

**Objective:** After login, usher selects a service type; home screen shows two large buttons for Register Member / Register First Timer with navigation scaffolding.

**Requirements:** HOME-01, HOME-02, HOME-03

---

## Task 1: Extend SessionManager with service type

**Files:** `util/SessionManager.java`

- Add `setServiceType(String)` and `getServiceType()` methods using SharedPreferences key `service_type`
- Add `clearServiceType()` for logout/reset
- Update `logout()` to also clear service type

**Acceptance Criteria:**
- `setServiceType("SUN")` then `getServiceType()` returns `"SUN"`
- `logout()` clears both usher ID and service type

---

## Task 2: Create ServiceSelectionDialog

**Files:** `ui/home/ServiceSelectionDialog.java`, `res/layout/dialog_service_selection.xml`

- DialogFragment with 4 large buttons: SUN, WED, FRI, CELL
- On selection: store via `SessionManager.setServiceType()`, dismiss dialog
- Style per design.md: brand_blue primary buttons, generous touch targets (48dp min)
- Callback interface `OnServiceSelectedListener` for the hosting activity

**Acceptance Criteria:**
- Dialog shows 4 service type options
- Selecting one stores it in SessionManager and dismisses
- Back press or outside tap does NOT dismiss (must select a service)

---

## Task 3: Update MainActivity as HomeActivity

**Files:** `MainActivity.java`, `res/layout/activity_main.xml`

- After login check, if no service type stored → show `ServiceSelectionDialog`
- Display current service type in the header area (e.g. "Sunday Service")
- Wire `btnRegisterMember` → Toast stub (Phase 4 will replace)
- Wire `btnRegisterFirstTimer` → Toast stub (Phase 5 will replace)
- Keep logout button functional (clears session + service type, returns to login)

**Acceptance Criteria:**
- Usher sees service selection dialog if no service type is set
- Home screen displays the selected service type
- Register Member / Register First Timer buttons show placeholder toasts
- Logout returns to LoginActivity and clears service type

---

## Task 4: Wire manifest and verify build

**Files:** `AndroidManifest.xml` (no changes needed — MainActivity already registered)

- Run `./gradlew assembleDebug` to verify build passes
- Verify navigation flow: Login → Service Selection → Home → Logout → Login

**Acceptance Criteria:**
- `./gradlew assembleDebug` succeeds
- Full navigation loop works end-to-end

---

## Verification

- [ ] HOME-01: On login, usher selects service type (SUN/WED/FRI/CELL) stored for session
- [ ] HOME-02: Two large buttons — Register Member / Register First Timer
- [ ] HOME-03: Navigation scaffolding between screens

## must_haves

- ServiceSelectionDialog appears when no service type is stored
- Service type persists in SharedPreferences across activity recreation
- Two home buttons are visually distinct (blue for Member, gold for First Timer per design.md)
- Logout clears both usher session and service type
- App compiles and navigates correctly
