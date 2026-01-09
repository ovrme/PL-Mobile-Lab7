
# ExpenseTracker (Android – Java, Firebase, Retrofit)

Generated: 2025-12-26T15:26:12.839080

## Setup
1. Open **Android Studio** and select *Open an existing project*. Choose the `ExpenseTracker` folder.
2. In **Firebase Console**, create a project and add your Android app. Download **google-services.json** and **REPLACE** `app/google-services.json` with the real one from Firebase. (The placeholder will NOT let you sign in.)
3. In `app/build.gradle`, set:
   - `BuildConfig.API_BASE_URL` to your REST API base (e.g., `https://api.example.com/`).
   - `BuildConfig.X_DB_NAME` to your **personal GUID** required by the server.
4. Enable **Email/Password** sign-in in Firebase Authentication.
5. Sync Gradle and run.

## Features
- Firebase Authentication (Signup, Login, Logout).
- BottomNavigationView with Home, Add Expense, Expense List fragments.
- Retrofit + Gson with ISO8601 date adapter (UTC).
- RecyclerView + endless pagination.
- Detail screen shows Amount, Currency, Category, Remark, Created Date.
- Activity & Fragment communication: startActivityForResult, Intent extras, ID-only handoff.

## API Contract
- `GET /expenses?page=1&limit=20` → list of expenses.
- `POST /expenses` → create a new expense.
- `GET /expenses/{id}` → single expense.
- Header: `X-DB-NAME: <your-guid>` is added automatically by OkHttp.

## Notes
- The `assets/mock/expenses_page1.json` is **for reference only**, not wired; calls are real Retrofit.
- Replace the placeholder Firebase file before running auth flows.

