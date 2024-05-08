create table if not exists public.resume
(
    uuid      char(36) not null
        constraint uuid
            primary key,
    full_name text
);

alter table public.resume
    owner to postgres;

create table if not exists public.contact
(
    id          serial,
    value       text     not null,
    type        text     not null,
    resume_uuid char(36) not null
        constraint contact_resume_uuid_fk
            references public.resume
            on delete cascade
);

alter table public.contact
    owner to postgres;

create unique index if not exists contact_resume_uuid_type_index
    on public.contact (resume_uuid, type);

create table if not exists public.section
(
    id          serial,
    value       text     not null,
    type        text     not null,
    resume_uuid char(36) not null
        constraint contact_resume_uuid_fk
            references public.resume
            on delete cascade
);

alter table public.section
    owner to postgres;

create unique index if not exists section_resume_uuid_type_index
    on public.section (resume_uuid, type);

create sequence section_id_seq
    as integer;

alter sequence section_id_seq owner to postgres;

alter sequence section_id_seq owned by contact.id;