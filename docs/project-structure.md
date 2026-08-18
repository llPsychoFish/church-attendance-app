# Project Structure — Church Attendance App (Android, Java)

Reference for Phase 0. Matches the package layout mentioned in the roadmap (`data/`, `ui/`, `util/`) and the entities from the data field spec.

---

## Directory Tree

```
church-attendance-app/
├── app/
│   ├── build.gradle
│   ├── proguard-rules.pro
│   └── src/
│       ├── main/
│       │   ├── AndroidManifest.xml
│       │   ├── java/com/yourname/churchattendance/
│       │   │   ├── ChurchAttendanceApp.java          # Application class
│       │   │   │
│       │   │   ├── data/
│       │   │   │   ├── entity/
│       │   │   │   │   ├── Usher.java
│       │   │   │   │   ├── Member.java
│       │   │   │   │   ├── Attendance.java
│       │   │   │   │   ├── FirstTimer.java
│       │   │   │   │   └── FirstTimerDepartment.java
│       │   │   │   │
│       │   │   │   ├── dao/
│       │   │   │   │   ├── UsherDao.java
│       │   │   │   │   ├── MemberDao.java
│       │   │   │   │   ├── AttendanceDao.java
│       │   │   │   │   ├── FirstTimerDao.java
│       │   │   │   │   └── FirstTimerDepartmentDao.java
│       │   │   │   │
│       │   │   │   ├── AppDatabase.java               # Room database singleton
│       │   │   │   │
│       │   │   │   ├── repository/
│       │   │   │   │   ├── UsherRepository.java
│       │   │   │   │   ├── MemberRepository.java
│       │   │   │   │   ├── AttendanceRepository.java
│       │   │   │   │   └── FirstTimerRepository.java
│       │   │   │   │
│       │   │   │   └── remote/                        # empty until Phase 8 (Supabase)
│       │   │   │       └── .gitkeep
│       │   │   │
│       │   │   ├── ui/
│       │   │   │   ├── auth/
│       │   │   │   │   ├── LoginActivity.java
│       │   │   │   │   ├── RegisterUsherActivity.java
│       │   │   │   │   └── AuthViewModel.java
│       │   │   │   │
│       │   │   │   ├── home/
│       │   │   │   │   ├── HomeActivity.java           # two-button screen
│       │   │   │   │   ├── ServiceSelectionDialog.java
│       │   │   │   │   └── HomeViewModel.java
│       │   │   │   │
│       │   │   │   ├── member/
│       │   │   │   │   ├── RegisterMemberActivity.java # search + mark present
│       │   │   │   │   ├── MemberSearchAdapter.java
│       │   │   │   │   └── RegisterMemberViewModel.java
│       │   │   │   │
│       │   │   │   ├── firsttimer/
│       │   │   │   │   ├── RegisterFirstTimerActivity.java
│       │   │   │   │   ├── DepartmentCheckboxAdapter.java
│       │   │   │   │   └── RegisterFirstTimerViewModel.java
│       │   │   │   │
│       │   │   │   ├── review/
│       │   │   │   │   ├── ReviewDashboardActivity.java # Phase 6 local review screens — open to any logged-in usher
│       │   │   │   │   ├── AttendanceListFragment.java
│       │   │   │   │   ├── FirstTimerListFragment.java
│       │   │   │   │   └── ReviewViewModel.java
│       │   │   │   │
│       │   │   │   └── export/
│       │   │   │       ├── CsvExportActivity.java      # Phase 7
│       │   │   │       └── CsvExportViewModel.java
│       │   │   │
│       │   │   └── util/
│       │   │       ├── PasswordHasher.java             # PIN/password hashing helper
│       │   │       ├── DateUtils.java
│       │   │       ├── CsvWriter.java
│       │   │       ├── SessionManager.java             # SharedPreferences wrapper for logged-in usher
│       │   │       └── Constants.java                  # service type enum values, etc.
│       │   │
│       │   └── res/
│       │       ├── layout/
│       │       │   ├── activity_login.xml
│       │       │   ├── activity_register_usher.xml
│       │       │   ├── activity_home.xml
│       │       │   ├── dialog_service_selection.xml
│       │       │   ├── activity_register_member.xml
│       │       │   ├── item_member_search_result.xml
│       │       │   ├── activity_register_first_timer.xml
│       │       │   ├── item_department_checkbox.xml
│       │       │   ├── activity_review_dashboard.xml
│       │       │   ├── fragment_attendance_list.xml
│       │       │   ├── fragment_first_timer_list.xml
│       │       │   ├── item_attendance_row.xml
│       │       │   ├── item_first_timer_row.xml
│       │       │   └── activity_csv_export.xml
│       │       ├── values/
│       │       │   ├── strings.xml
│       │       │   ├── colors.xml
│       │       │   ├── themes.xml
│       │       │   └── dimens.xml
│       │       └── drawable/
│       │
│       └── test/
│           └── java/com/yourname/churchattendance/
│               └── data/
│                   ├── MemberDaoTest.java
│                   ├── AttendanceDaoTest.java
│                   └── FirstTimerDaoTest.java
│
├── .gitignore
├── build.gradle                # project-level
├── settings.gradle
├── README.md
├── ROADMAP.md                  # your existing roadmap doc
└── docs/
    ├── design.md               # the design document
    └── data-field-spec.md      # the field spec doc
```

---

## Notes on the Layout

- **`data/entity`, `data/dao`, `data/AppDatabase.java`** — this is everything from Phase 1. Build and test this layer completely before touching `ui/`.
- **`data/repository`** — a thin layer between DAOs and ViewModels. Not strictly required for a project this size, but worth having from day one since Phase 8 will swap local-only repositories for ones that also talk to Supabase — easier to do that behind a repository interface than by rewriting ViewModels later.
- **`data/remote`** — deliberately empty until Phase 8. Keeping the folder (with a placeholder file) now reserves the location so the eventual Supabase client/API classes have an obvious home without restructuring the project.
- **`ui/` is split by feature, not by screen type** — `auth/`, `home/`, `member/`, `firsttimer/`, `review/`, `export/` — which maps directly onto your roadmap phases (Phase 2 → `auth/`, Phase 3 → `home/`, Phase 4 → `member/`, Phase 5 → `firsttimer/`, Phase 6 → `review/`, Phase 7 → `export/`). You can build and test one folder per phase in isolation.
- **`ui/review/` is not a separate admin role** — any logged-in usher can open it. There's no `isAdmin` flag on `Usher`; access is the same as the rest of the app (just being logged in).
- **`util/Constants.java`** — put the `SUN/WED/FRI/CELL` service type values and department list here as constants (or enums) in one place, so the field-spec changes we made don't require hunting through multiple files if the church adds a service type later.

---

## build.gradle (app-level) — Core Dependencies

```gradle
dependencies {
    // Room
    implementation "androidx.room:room-runtime:2.6.1"
    annotationProcessor "androidx.room:room-compiler:2.6.1"

    // Lifecycle / ViewModel
    implementation "androidx.lifecycle:lifecycle-viewmodel:2.7.0"
    implementation "androidx.lifecycle:lifecycle-livedata:2.7.0"

    // UI
    implementation "com.google.android.material:material:1.11.0"
    implementation "androidx.recyclerview:recyclerview:1.3.2"
    implementation "androidx.constraintlayout:constraintlayout:2.1.4"

    // CSV export
    implementation "com.opencsv:opencsv:5.9"

    // Testing
    testImplementation "junit:junit:4.13.2"
    androidTestImplementation "androidx.room:room-testing:2.6.1"
    androidTestImplementation "androidx.test.ext:junit:1.1.5"
}
```

> Supabase's Android client (`supabase-kt` / Ktor-based) gets added in Phase 8 only — deliberately left out here.

---

## .gitignore additions (on top of the Android template)

```
# Local secrets (added ahead of Phase 8, so it's already in place)
local.properties
secrets.properties
```

---

## Phase 0 Checklist

- [ ] Create project in Android Studio (Java, min SDK 21+)
- [ ] Set up the package structure above (empty folders/placeholder files are fine)
- [ ] Add dependencies to `app/build.gradle`
- [ ] Confirm `.gitignore` is set to Android template + the additions above
- [ ] Initialize Room `AppDatabase.java` shell (no entities yet — just confirm it builds)
- [ ] Move `design.md`, `ROADMAP.md`, and `data-field-spec.md` into `docs/` (or repo root) so they live in version control alongside the code
- [ ] Confirm the app builds and launches to a blank activity
