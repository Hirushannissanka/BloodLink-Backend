# BloodLink Backend

Spring Boot microservice backend for the BloodLink frontend.

## Services

| Service | Port | Responsibility |
| --- | ---: | --- |
| `api-gateway` | `8080` | Single frontend entry point. Routes `/api/**` to services. |
| `auth-service` | `8081` | Register/login, JWT, donor profiles, patient profile, rewards, donor matching. |
| `request-service` | `8082` | Blood requests, donor accept/reject, matching notification trigger. |
| `camp-service` | `8083` | Donation camp listing and hospital camp creation. |
| `notification-service` | `8084` | Queues email/SMS notification records. |

The frontend already uses `VITE_API_URL=http://localhost:8080`, so keep the gateway on `8080`.

## Supabase Setup

1. Open Supabase SQL Editor.
2. Run [supabase-schema.sql](database/supabase-schema.sql).
3. Copy the Supabase connection string from Project Settings > Database.
4. Set these environment variables before running services:

```powershell
$env:SUPABASE_DB_URL="jdbc:postgresql://YOUR-SUPABASE-HOST:5432/postgres?sslmode=require"
$env:SUPABASE_DB_USER="postgres"
$env:SUPABASE_DB_PASSWORD="YOUR-SUPABASE-DB-PASSWORD"
$env:JWT_SECRET="change-this-to-a-long-random-secret-at-least-32-chars"
```

## Run

Build all modules:

```powershell
mvn clean package
```

Start each service in a separate terminal:

```powershell
mvn -pl auth-service spring-boot:run
mvn -pl request-service spring-boot:run
mvn -pl camp-service spring-boot:run
mvn -pl notification-service spring-boot:run
mvn -pl api-gateway spring-boot:run
```

Then run the frontend from `BloodLink-Frontend-main`:

```powershell
npm run dev
```

## Current API Shape

The backend supports the frontend's existing calls:

- `POST /api/auth/register`
- `POST /api/auth/login`
- `GET /api/donors/me`
- `PUT /api/donors/me`
- `GET /api/donors/me/donations`
- `POST /patient/profile`
- `GET /api/requests`
- `POST /api/requests`
- `GET /api/requests/active`
- `GET /api/requests/history`
- `PUT /api/requests/{id}/cancel`
- `POST /api/requests/{id}/respond`
- `GET /api/camps`
- `POST /api/camps`

## Design Notes

- Request matching currently uses blood type plus district.
- `request-service` calls `auth-service` internally for matching donors and reward points.
- `notification-service` currently stores queued notification rows. Email/SMS providers can be added behind that adapter without changing request creation.
- Short comments are included where design patterns are used, as requested.
