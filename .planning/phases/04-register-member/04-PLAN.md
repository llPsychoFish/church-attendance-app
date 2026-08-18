# Phase 4 — Register Member Flow

**Objective:** Returning members found and marked present, fully offline.

**Requirements:** MEMB-01, MEMB-02, MEMB-03, MEMB-04

---

## Task 1: Create MemberRepository

**Files:** `data/repository/MemberRepository.java`

- Wraps `MemberDao` with search, insert, update, getById, getByPhone methods
- Follows existing `UsherRepository` pattern

**Acceptance Criteria:**
- `search(query)` returns matching members by name or phone
- `insert(member)` persists a new member

---

## Task 2: Create AttendanceRepository

**Files:** `data/repository/AttendanceRepository.java`

- Wraps `AttendanceDao` with insert, getById, getByMember, getByDate, countByDateAndService
- Follows existing `UsherRepository` pattern

**Acceptance Criteria:**
- `insert(attendance)` persists an attendance record
- `countByDateAndService(date, type)` returns count for review screens

---

## Task 3: Create MemberSearchAdapter

**Files:** `ui/member/MemberSearchAdapter.java`, `res/layout/item_member_search.xml`

- RecyclerView adapter showing member name, phone, and "Mark Present" button
- Callback interface `OnMarkPresentListener`

**Acceptance Criteria:**
- List items show member name and phone
- "Mark Present" button triggers callback with the member

---

## Task 4: Create MemberViewModel

**Files:** `ui/member/MemberViewModel.java`

- AndroidViewModel with search and markPresent methods
- Uses executor for background DB operations
- Exposes LiveData for search results, marked member, not-found state

**Acceptance Criteria:**
- `search(query)` populates `searchResults` LiveData
- `markPresent(member, usherId, serviceType)` creates Attendance record and populates `markedMember`

---

## Task 5: Create RegisterMemberActivity

**Files:** `ui/member/RegisterMemberActivity.java`, `res/layout/activity_register_member.xml`

- Search field + search button
- RecyclerView showing search results
- "Not found" state with redirect to First Timer flow
- Success state with auto-return to home after 1.5s
- Displays current service type and date in header

**Acceptance Criteria:**
- Search by name or phone finds matching members
- "Mark Present" creates attendance record and shows success
- "Member not found" shows redirect message with First Timer button
- Auto-returns to home after success

---

## Task 6: Wire navigation and manifest

**Files:** `MainActivity.java`, `AndroidManifest.xml`

- Register Member button navigates to `RegisterMemberActivity`
- Activity registered in manifest

**Acceptance Criteria:**
- Tapping "Register Member" on home screen opens the search screen
- Back navigation returns to home

---

## Verification

- [ ] MEMB-01: Search by name or phone against local Member table
- [ ] MEMB-02: On match, create Attendance record (member_id, service_date, service_type, registered_by, timestamp)
- [ ] MEMB-03: On no match, show redirect message with jump into First Timer flow
- [ ] MEMB-04: Return to home screen automatically after successful registration

## must_haves

- Member search works by name or phone
- Attendance record created with correct fields on "Mark Present"
- "Not found" state shows redirect to First Timer flow
- Auto-return to home after successful attendance marking
- Service type and date displayed in header
