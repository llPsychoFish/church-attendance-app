# Design System — Church Attendance App

**Purpose:** This document tells an AI coding assistant (or any contributor) exactly how to style this app — colors, typography, spacing, and component rules — so every screen built across different sessions stays visually consistent. Reference this file whenever generating or modifying UI code (layouts, `colors.xml`, `themes.xml`, custom views).

If new colors or design decisions are added later, append them to the relevant section below rather than overriding it silently — keep a single source of truth.

---

## 1. Brand Grounding

This app is for a Christ Embassy / BLW (Believers' LoveWorld) campus ministry. The palette is anchored to the church's brand identity — deep blue and gold — rather than a generic Material default, so the app feels visually consistent with existing BLW/Christ Embassy materials (first-timer cards, banners, etc.).

---

## 2. Color Palette

| Token | Light Hex | Dark Hex | Usage |
|---|---|---|---|
| `color_primary` | `#1A237E` | `#3D5AFE` | App bar, primary buttons, headers |
| `color_primary_dark` | `#0016BE` | `#1A237E` | Status bar, pressed states |
| `color_accent_gold` | `#D4AF37` | `#FFC107` | Register First Timer button, highlights — accent tone |
| `color_background` | `#FAFAFA` | `#0F1426` | Screen backgrounds |
| `color_surface` | `#FFFFFF` | `#1B2238` | Cards, form fields, list rows |
| `color_surface_variant` | `#F0F4F8` | `#252D4A` | Secondary surface cards, input backgrounds |
| `color_text_primary` | `#1A1A1A` | `#F0F2F8` | Body text, labels |
| `color_text_secondary` | `#5F5F5F` | `#94A3B8` | Hints, secondary labels, timestamps |
| `color_error` | `#B00020` | `#FF5252` | Validation errors, alert messages |
| `color_success` | `#2E7D32` | `#4CAF50` | Confirmation states |
| `color_divider` | `#E0E0E0` | `#2E3856` | List dividers, form section separators |

**Rules for AI agents generating UI:**

- Never introduce a new color without adding it to this table first.
- Gold (`color_accent_gold`) is an accent, not a surface. Don't use it for full-screen backgrounds, large cards, or body text.
- The two home-screen buttons must be visually distinct: **Register Member → `color_primary`**, **Register First Timer → `color_accent_gold`**. This is a functional distinction (routine vs. welcome action), not just decoration — don't swap or unify these.
- `color_error` is reserved for genuine error/redirect states (e.g., the "couldn't find you" message). Don't reuse it for neutral warnings.

---

## 3. Typography

- Use the system default (Roboto) — no custom font files. This keeps the APK lean and avoids licensing overhead for a project this size.
- Type scale (Material-based):

| Style | Size | Weight | Usage |
|---|---|---|---|
| `Headline` | 24sp | Bold | Screen titles (e.g. "Register First Timer") |
| `Subtitle` | 18sp | Medium | Section headers within forms |
| `Body` | 16sp | Regular | Form labels, list text |
| `Caption` | 13sp | Regular | Hints, timestamps, helper text |
| `Button` | 16sp | Medium, uppercase off (avoid ALL CAPS — reads as shouting for a church app) | All button labels |

**Rule:** Do not set button text to uppercase (Android's Material default does this automatically — override it). This app is handed to a wide range of people including older congregants and first-timers; calmer, sentence-case labels read as more welcoming.

---

## 4. Spacing & Layout

- Base unit: **8dp**. All margins/padding should be multiples of 8 (8, 16, 24, 32).
- Minimum touch target: **48dp x 48dp** (Material minimum) — non-negotiable given the phone gets handed directly to congregants of all ages, including first-time users unfamiliar with the app.
- Home screen's two buttons should each take up roughly **40–45% of screen height**, stacked vertically, with generous padding — the interaction needs to be obvious to someone who has never seen the app before, with zero instructions.
- Forms: single-column, one field per row, minimum 16dp vertical spacing between fields — matches the linear top-to-bottom flow of the physical First Timer Card, so it feels familiar rather than like a redesigned form.

---

## 5. Component Guidance

### Buttons
- Primary actions: filled, rounded corners (12dp radius), `color_primary` or `color_accent_gold` per §2
- Secondary/cancel actions: outlined or text-only, never filled — avoid competing with the primary action visually

### Forms
- Use `TextInputLayout` with floating labels (Material) and start icons (`ic_person`, `ic_phone`, etc.) to provide visual cues for users.
- Group related fields visually (e.g., Hall/Hostel + Room No. on the same row) rather than a single flat list, mirroring the physical card's layout

### Lists (review screens, search results)
- Simple `RecyclerView` rows: name + one or two secondary details (e.g., phone, service type), `color_divider` between rows
- Flat card elevation (2dp) with theme surface background (`?attr/colorSurface`) ensures high contrast in both Light & Dark modes.

### Checkboxes (Department Interest)
- Standard Material checkboxes, listed vertically, matching the order on the physical card (Ushering, Choir, Technical, Creative Art, Media & New Media, Innovations).

---

## 6. Iconography & Custom Drawables

- Use custom vector drawables (`res/drawable/`) tailored for church branding and form entry:
  - `ic_church_logo`: App title bar & header logo badge
  - `ic_member_checkin`: Register Member quick action button
  - `ic_first_timer_welcome`: Register First Timer quick action button
  - `ic_dashboard`, `ic_export_csv`, `ic_logout`: Admin / action buttons
  - `ic_person`, `ic_phone`, `ic_email`, `ic_location`, `ic_school`, `ic_calendar`, `ic_lock`, `ic_search`: Text field start icons
- Icon color follows context: `colorPrimary` / `colorOnSurfaceVariant` in form fields, white / dark text on filled primary buttons.

---

## 7. Accessibility & Dual Theme Support

- Both Light and Dark themes strictly maintain contrast ratios conforming to WCAG AA guidelines.
- Layouts must reference theme attributes (`?attr/colorBackground`, `?attr/colorSurface`, `?attr/colorOnSurface`, `?attr/colorPrimary`) rather than static hex codes.
- Text should never drop below 13sp.
- Every interactive element needs a visible label — no icon-only buttons for primary actions.

---

## 8. Do / Don't Summary

**Do:**
- Keep the two home-screen buttons large, distinct in color, and text-labeled
- Match form field order to the physical paper cards (First Timer Card, Attendance Sheet)
- Use theme attributes (`?attr/...`) in XML layouts so Light & Dark modes switch dynamically
- Use clean vector start icons in text input fields for immediate visual context

**Don't:**
- Introduce new colors without adding them to §2 first
- Use uppercase button text
- Use icon-only buttons for core actions
- Hardcode `@color/bg_off_white` or `@color/white` on layout containers that need dark mode contrast

---

## 9. Extending This Document

When new colors or components are introduced (e.g., a color for the "follow-up status" badges in Phase 6, or styling for the future web dashboard), add them as new rows/sections here rather than making ad hoc choices in code. Keep this file as the single reference an AI assistant (or Arnold) checks before writing any UI code.
