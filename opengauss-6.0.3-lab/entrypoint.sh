#!/usr/bin/env bash
set -Eeuo pipefail

if [ "$(id -u)" = 0 ]; then
    chown -R omm:omm /var/lib/opengauss
    exec runuser -u omm -- /usr/local/bin/lab-entrypoint
fi

if [ ! -s "$PGDATA/PG_VERSION" ]; then
    : "${GS_PASSWORD:?Set GS_PASSWORD}"
    password_file=$(mktemp)
    chmod 600 "$password_file"
    printf '%s\n' "$GS_PASSWORD" > "$password_file"
    gs_initdb -D "$PGDATA" --nodename=oglab --encoding=UTF8 --locale=C --pwfile="$password_file"
    unlink "$password_file"
fi

mkdir -p /var/lib/opengauss/log
export GAUSSLOG=/var/lib/opengauss/log

if [ ! -f "$PGDATA/.lab-ready" ]; then
    cat >> "$PGDATA/postgresql.conf" <<'CONF'
listen_addresses = '*'
port = 5432
max_connections = 50
shared_buffers = 128MB
max_process_memory = 2GB
password_encryption_type = 1
CONF
    printf '\nhost all all 0.0.0.0/0 sha256\n' >> "$PGDATA/pg_hba.conf"
    gs_ctl start -D "$PGDATA" -Z single_node -l "$PGDATA/bootstrap.log" -w -t 60
    gsql -d postgres -p 5432 -v ON_ERROR_STOP=1 -v lab_password="$GS_PASSWORD" -f /opt/lab/bootstrap.sql
    gs_ctl stop -D "$PGDATA" -m fast -w

    cat >> "$PGDATA/postgresql.conf" <<'CONF'
enable_dolphin_proto = on
dolphin_server_port = 3306
dolphin.default_database_name = 'og_lab'
CONF
    touch "$PGDATA/.lab-ready"
fi

exec gaussdb -D "$PGDATA" --single_node
