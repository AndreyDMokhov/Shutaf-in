#! /bin/sh
cd ..

mkdir -p backup
cd backup

echo "Enter DB user"
read user

echo "Enter DB pass"
read pass

mysqldump -u$user -p$pass si_gateway > si_gateway.sql
mysqldump -u$user -p$pass si_account > si_account.sql
mysqldump -u$user -p$pass si_matching > si_matching.sql
mysqldump -u$user -p$pass si_email > si_email.sql

mkdir -p servers
cd ..
pwd
cp -a servers/. backup/servers/
