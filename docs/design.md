# Design System — Church Attendance App

**Purpose:** This document tells an AI coding assistant (or any contributor) exactly how to style this app — colors, typography, spacing, and component rules — so every screen built across different sessions stays visually consistent. Reference this file whenever generating or modifying UI code (layouts, `colors.xml`, `themes.xml`, custom views).

If new colors or design decisions are added later, append them to the relevant section below rather than overriding it silently — keep a single source of truth.

---

## 1. Brand Grounding

This app is for a Christ Embassy / BLW (Believers' LoveWorld) campus ministry. The palette is anchored to the church's brand identity — deep blue and gold — rather than a generic Material default, so the app feels visually consistent with existing BLW/Christ Embassy materials (first-timer cards, banners, etc.).

---

## 2. Color Palette

| Token | Hex | Usage |
|---|---|---|
| `color_primary` | `#1A237E` | App bar, primary buttons, headers — toned-down version of brand blue (`#0016BE`) for better on-screen contrast |
| `color_primary_dark` | `#0016BE` | Status bar, pressed states — closer to true brand blue |
| `color_accent_gold` | `#D4AF37` | Register First Timer button, highlights, launcher icon accent — **use sparingly, never as a large background** |
| `color_background` | `#FAFAFA` | Screen backgrounds |
| `color_surface` | `#FFFFFF` | Cards, form fields, list rows |
| `color_text_primary` | `#1A1A1A` | Body text, labels |
| `color_text_secondary` | `#5F5F5F` | Hints, secondary labels, timestamps |
| `color_error` | `#B00020` | Validation errors, "member not found" redirect message |
| `color_success` | `#2E7D32` | Confirmation states (e.g. "Attendance marked") |
| `color_divider` | `#E0E0E0` | List dividers, form section separators |

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
- Primary actions: filled, rounded corners (8dp radius), `color_primary` or `color_accent_gold` per §2
- Secondary/cancel actions: outlined or text-only, never filled — avoid competing with the primary action visually

### Forms
- Use `TextInputLayout` with floating labels (Material), not placeholder-only fields — floating labels stay visible after typing starts, reducing errors for first-time users
- Group related fields visually (e.g., Hall/Hostel + Room No. on the same row) rather than a single flat list, mirroring the physical card's layout

### Lists (review screens, search results)
- Simple `RecyclerView` rows: name + one or two secondary details (e.g., phone, service type), `color_divider` between rows
- No unnecessary card elevation/shadows — flat list rows keep the review screens fast to scan

### Checkboxes (Department Interest)
- Standard Material checkboxes, listed vertically, matching the order on the physical card (Ushering, Choir, Technical, Creative Art, Media & New Media, Innovations) — don't reorder or alphabetize; consistency with the paper form matters more than any other ordering logic here

---

## 6. Iconography

- Use standard Material Icons (`androidx.vectordrawable`) — no custom icon set needed for v1
- Icon color follows context: `color_primary` on light backgrounds, white on filled primary-color buttons
- Keep icon usage minimal — this app prioritizes clarity and speed for non-technical users over visual flourish

---

## 7. Accessibility & Practical Constraints

- High contrast, light-mode-only for v1 (no dark mode) — the app is used in bright church halls and handed to people who may not be tech-savvy; a bright, obvious UI beats a "sleek" dark one here
- Text should never drop below 13sp (Caption level)
- Every interactive element needs a visible label — no icon-only buttons for primary actions (Register Member / Register First Timer must always show text, not just an icon)

---

## 8. Do / Don't Summary

**Do:**
- Keep the two home-screen buttons large, distinct in color, and text-labeled
- Match form field order to the physical paper cards (First Timer Card, Attendance Sheet)
- Use gold as a small accent, blue as the dominant tone

**Don't:**
- Introduce new colors without adding them to §2 first
- Use uppercase button text
- Use icon-only buttons for core actions
- Add dark mode, animations, or visual flourishes that aren't in this document — if it's not specified here, default to the simplest Material implementation rather than improvising a new style

---

## 9. Extending This Document

When new colors or components are introduced (e.g., a color for the "follow-up status" badges in Phase 6, or styling for the future web dashboard), add them as new rows/sections here rather than making ad hoc choices in code. Keep this file as the single reference an AI assistant (or Arnold) checks before writing any UI code.
