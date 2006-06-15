-- DDL for empty GenMAPP database, suitable for use in databases
-- other than Microsoft Access.  Changing the connection object
-- from the current ODBC bridge to the "proxy" database will
-- reroute all subsequent queries to that database.

create table "Info"(
    owner varchar,
    version varchar,
    modsystem varchar,
    species varchar,
    modify varchar,
    displayorder varchar,
    notes varchar
);

create table "Systems"(
    -- Quotes are used here because the code refers to this field
    -- in a case-sensitive manner.
    "Date" varchar,
    columns varchar,
    systemcode varchar
);

create table "Relations"(
    systemcode varchar,
    relatedcode varchar,
    relation varchar,
    type varchar,
    source varchar
);

create table "Other"(
    id varchar,
    systemcode varchar,
    name varchar,
    annotations varchar,
    species varchar,
    date varchar,
    remarks varchar
);

-- Unfortunately, it is not enough to just create these tables.
-- The following issues also exist, and can only be rectified in
-- the code.  Thus, these changes must be made in the code in
-- addition to the connection URL change:
--
-- . Regarding any Date fields; Access appears to accept a
--   different set of strings from PostgreSQL as parseable dates;
--   thus, for generality, all Date fields should be declared as
--   "varchar" instead of "date."
--
-- . The "memo" data type must be replaced with "text" or "varchar."
--
-- . The "long" data type must be replaced with "varchar" (similar
--   issue to how the code currently handles the Date fields).
--
-- . The "Level" and "Count" fields in GO must have a data type of
--   "varchar" (same how-the-code-handles-this-field issue).
