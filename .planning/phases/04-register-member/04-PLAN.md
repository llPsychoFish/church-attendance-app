# Phase 4 — Register Member Flow

**Objective:** Members fill the Attendance Sheet form and are marked present, fully offline — matching how the physical sheet is actually used (filled fresh each service, not looked up). Autocomplete is a convenience, never a gate; there is no forced redirect to the First Timer flow.

**Requirements:** MEMB-01, MEMB-02, MEMB-03, MEMB-04

---

## Task 1: Add autocomplete query to Member layer

**Files:** `data/dao/MemberDao.java`, `data/repository/MemberRepository.java`

- Add substring autocomplete query (`searchAutocomplete`) matching surname, first name, or phone
- Repository wraps it with trim + empty-query guard

**Acceptance Criteria:**
- Typing a partial name or phone returns matching members
- Empty/blank query returns an empty list

---

## Task 2: Create/Update + attendance in MemberViewModel

**Files:** `ui/member/MemberViewModel.java`

- Exposes `suggestions`, `selectedMember`, and `success` LiveData
- `searchSuggestions(query)` populates suggestions as the member types
- `selectMember(member)` remembers a tapped suggestion (survives rotation)
- `submitRegistration(...)` on the executor: find existing member by selected id or phone → update it; otherwise insert a new Member (`is_first_timer_origin = false`, `joinDate` set); then insert an `Attendance` record (member_id, service_date, service_type, registered_by, timestamp)

**Acceptance Criteria:**
- Submit creates an Attendance row for a new or updated member
- Submitting with a matching phone updates the existing record instead of duplicating

---

## Task 3: Suggestion adapter (tap to autofill)

**Files:** `ui/member/MemberSearchAdapter.java`, `res/layout/item_member_search.xml`

- RecyclerView adapter showing member name + phone as tappable suggestion rows
- Callback interface `OnMemberSelectedListener`; tapping a row fills the form, no "Mark Present" button

**Acceptance Criteria:**
- Suggestions show name and phone
- Tapping a suggestion triggers the autofill callback

---

## Task 4: RegisterMemberActivity — data entry form

**Files:** `ui/member/RegisterMemberActivity.java`, `res/layout/activity_register_member.xml`

- Form with all Attendance Sheet fields per `docs/data-field-spec.md` §2: surname + first name (required), phone (required), email, hall/hostel + room no, course of study, level, date of birth
- Lookup field with live autocomplete suggestions (min 2 chars); selecting one autofills the form
- Submit button saves/updates the Member and marks attendance; no match is never a block
- Optional "First time here? Register as a first timer instead" link (text button) — never an automatic redirect
- Success state with auto-return to home after ~1.5s
- Displays current service type and date in header

**Acceptance Criteria:**
- Form can be filled and submitted with zero lookup
- Tapping an autocomplete match autofills all fields
- First Timer flow is reachable only via the optional link, never automatically
- Auto-returns to home after success

---

## Task 5: Wire navigation and manifest

**Files:** `MainActivity.java`, `AndroidManifest.xml`

- Register Member button navigates to `RegisterMemberActivity` (unchanged)
- Activity registered in manifest (already present)

**Acceptance Criteria:**
- Tapping "Register Member" on home screen opens the form screen
- Back navigation returns to home

---

## Verification

- [ ] MEMB-01: Register Member is a data entry form with Attendance Sheet fields + autocomplete convenience
- [ ] MEMB-02: Submit creates or updates the Member record and creates an Attendance record (member_id, service_date, service_type, registered_by, timestamp)
- [ ] MEMB-03: No forced redirect on no match; First Timer is an optional suggestion only
- [ ] MEMB-04: Return to home screen automatically after successful registration

## must_haves

- Member form matches the physical Attendance Sheet field order
- Autocomplete autofills on match but never blocks manual entry or submit
- Attendance record created with correct fields on submit
- First Timer flow reachable only via optional link, not automatic navigation
- Auto-return to home after successful attendance marking
- Service type and date displayed in header