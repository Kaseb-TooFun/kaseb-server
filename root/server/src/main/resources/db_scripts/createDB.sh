#!/bin/sh
DB_NAME=kaseb
DB_USER=kaseb
DB_PASSWORD=kaseb

dropdb $DB_NAME
dropuser $DB_USER

echo "create user$DB_USER password '$DB_PASSWORD'" | psql postgres
createdb -Eutf8 -O $DB_USER $DB_NAME
echo User \'$DB_USER\' with password of \'$DB_PASSWORD\' created.

echo Create $DB_NAME database and initializing with init data...

echo "## $DB_NAME database created:"
echo "##    Database: $DB_NAME"
echo "##        User: $DB_USER"
echo "##      Password: $DB_PASSWORD"
