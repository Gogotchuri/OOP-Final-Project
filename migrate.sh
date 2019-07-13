#!/bin/sh
currDir=`pwd`
for script in "$currDir"/src/database/seeders/*/*.sql;
do
    mysql -u root "oop" < "$script";
done