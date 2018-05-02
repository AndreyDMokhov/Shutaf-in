#! /bin/sh

echo "Enter DB username"
read user

echo "Enter DB password"
read password

cd ..
cd backup
mysql -u$user -p$password si_gateway < si_gateway.sql
mysql -u$user -p$password si_account < si_account.sql
mysql -u$user -p$password si_matching < si_matching.sql
mysql -u$user -p$password si_email < si_email.sql
mysql -u$user -p$password si_deal < si_deal.sql


