FROM postgres:16-alpine

# PostgreSQL executes this script automatically on the first container start.
COPY init.sql /docker-entrypoint-initdb.d/01-init.sql
