# Note to Self

**Note to Self** is a lightweight Android notes app built exactly as described in the official **Android Programming for Beginners** book. It offers a simple, dialog-based UI for creating, viewing, and persisting notes locally, along with a minimal settings screen to toggle list dividers.

---

## Features

- **Add Note**  
  Tap the floating action button to open a dialog. Enter a **Title** and **Description**, then mark it as **Idea**, **To-Do**, and/or **Important** before saving.

- **Notes List**  
  All saved notes appear in a scrollable `RecyclerView`. You can toggle a divider between items via Settings.

- **View Note**  
  Tap any list item to open a dialog showing the full title, description, and any status flags.

- **Settings**  
  Toggle “Show divider between notes” on or off. Changes apply immediately and persist across restarts.

- **Persistence**  
  Notes are stored as JSON (`notes.json`) in internal storage and automatically loaded on startup.

---



## Built With

- **Language:** Java  
- **SDK:** Android API 35 (min API 24)  
- **UI:** AppCompat (`RecyclerView`, `DialogFragment`)  
- **Storage:** Internal JSON file for persistence  
