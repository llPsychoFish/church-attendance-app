# Phase 5 — Register First Timer Flow

**Objective:** New visitors register themselves and are counted present in one step.

**Requirements:** FTMR-01, FTMR-02, FTMR-03

---

## Task 1: Create FirstTimerRepository

**Files:** `data/repository/FirstTimerRepository.java`

- Wraps FirstTimerDao and FirstTimerDepartmentDao
- insert, update, getById, getByMember, updateFollowUpStatus, getAll
- insertDepartment for department join records

---

## Task 2: Create FirstTimerViewModel

**Files:** `ui/firsttimer/FirstTimerViewModel.java`

- `registerFirstTimer()` creates Member (isFirstTimerOrigin=true) + FirstTimer + Attendance + departments in background executor
- Exposes LiveData<Boolean> success

---

## Task 3: Create First Timer form layout

**Files:** `res/layout/activity_register_first_timer.xml`

- Scrollable form matching data-field-spec §1 field-for-field
- Sections: Personal Info, Academic Details, Spiritual Journey, Department Interest
- Required fields: surname, first name, phone
- Radio groups for born again, speaks in tongues, wants membership
- 6 department checkboxes matching Constants.DEPARTMENTS order

---

## Task 4: Create RegisterFirstTimerActivity

**Files:** `ui/firsttimer/RegisterFirstTimerActivity.java`

- Collects all form fields, validates required fields
- Calls ViewModel to create records transactionally
- On success, returns to Home automatically

---

## Task 5: Wire navigation

**Files:** `MainActivity.java`, `RegisterMemberActivity.java`, `AndroidManifest.xml`

- Home "Register First Timer" button opens RegisterFirstTimerActivity
- Member not-found "Register as First Timer" button opens RegisterFirstTimerActivity
- Activity registered in manifest

---

## Verification

- [ ] FTMR-01: Form matches data-field-spec §1 field-for-field
- [ ] FTMR-02: Submit creates Member + FirstTimer + Attendance atomically
- [ ] FTMR-03: Auto-return to home after success

## must_haves

- All fields from data-field-spec §1 present in correct order
- Required field validation (surname, first name, phone)
- Transaction creates all 3+ records atomically
- Department checkboxes store via join table
- Auto-return to home on success
