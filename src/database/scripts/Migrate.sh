#!/usr/bin/env bash
read -ep "Enter username:" username
read -ep "Enter database name:" dbName
echo -n "Enter password:"
read -s password
echo ""

currDir=$(cd `dirname $0` && pwd)

if [[ ${1} == '-f' ]] || [[ ${2} == '-f' ]]
then
    # Remove database and recreate script
    echo "DROP DATABASE IF EXISTS ${dbName}; CREATE DATABASE ${dbName};" > dropAndCreate.sql
    #Execute cleaning sequence
    echo "Dropping database..."
    mysql -u root --password=${password} < dropAndCreate.sql
    #Clean drop and create script
    rm -f dropAndCreate.sql

    echo "Creating Database..."
    for filename in ${currDir}/../migrations/migration_*.sql; do
        echo "Executing migration: ${filename}"
        mysql -u root --password=${password} ${dbName} < ${filename}
    done
    echo "Database structure created successfully!"
fi

#seeding part!
if [[ ${1} == '-s' ]] || [[ ${2} == '-s' ]]
then
    #seeding part
    echo "Starting to seed"
    for filename in ${currDir}/../seeders/*/*.sql; do
        echo "Seeding: ${filename}"
        mysql -u root --password=${password} ${dbName} < ${filename}
    done
    echo "Seeding done!"

fi

echo "All done!"