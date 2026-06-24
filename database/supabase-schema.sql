create table if not exists app_users (
    id bigserial primary key,
    full_name varchar(160) not null,
    email varchar(180) not null unique,
    phone varchar(40) not null,
    age integer,
    district varchar(80),
    nearest_hospital varchar(180),
    emergency_contact varchar(80),
    blood_type varchar(20),
    role varchar(30) not null,
    password_hash varchar(255) not null,
    reward_points integer not null default 0,
    created_at timestamptz not null default now()
);

create table if not exists patient_profiles (
    id bigserial primary key,
    user_id bigint not null unique references app_users(id) on delete cascade,
    blood_type varchar(20),
    city varchar(80),
    district varchar(80),
    emergency_contact varchar(80),
    medical_notes text,
    created_at timestamptz not null default now()
);

create table if not exists blood_requests (
    id bigserial primary key,
    requester_id bigint references app_users(id) on delete set null,
    patient_name varchar(160),
    contact_phone varchar(40),
    district varchar(80),
    hospital_name varchar(180),
    blood_type_needed varchar(20) not null,
    units_needed integer not null,
    urgency_level varchar(30) not null,
    status varchar(30) not null default 'PENDING',
    notes text,
    created_at timestamptz not null default now()
);

create table if not exists request_donor_responses (
    id bigserial primary key,
    request_id bigint not null references blood_requests(id) on delete cascade,
    donor_id bigint not null references app_users(id) on delete cascade,
    status varchar(20) not null,
    responded_at timestamptz not null default now(),
    unique (request_id, donor_id)
);

create table if not exists donation_camps (
    id bigserial primary key,
    organizer_id bigint references app_users(id) on delete set null,
    name varchar(180) not null,
    organizer varchar(180),
    district varchar(80) not null,
    location varchar(240) not null,
    date date not null,
    start_time varchar(20),
    end_time varchar(20),
    contact_phone varchar(40),
    contact_email varchar(180),
    notes text,
    target_units integer,
    registered_donors integer not null default 0,
    blood_types_needed text,
    created_at timestamptz not null default now()
);

create table if not exists notifications (
    id bigserial primary key,
    donor_id bigint references app_users(id) on delete cascade,
    request_id bigint references blood_requests(id) on delete cascade,
    email varchar(180),
    phone varchar(40),
    message text not null,
    status varchar(30) not null default 'QUEUED',
    created_at timestamptz not null default now()
);

create index if not exists idx_users_donor_match on app_users(role, blood_type, district);
create index if not exists idx_requests_active on blood_requests(status, blood_type_needed, district);
create index if not exists idx_camps_date_district on donation_camps(date, district);
