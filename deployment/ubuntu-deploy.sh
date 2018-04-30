#! /bin/sh
echo "Enter repository username"
read username
echo "Enter repository password"
read password
mkdir /home/resources
sudo apt-get update
sudo apt-get install -y default-jre
sudo apt-get install -y default-jdk
sudo apt-get install -y maven
cd ..
git fetch
git checkout develop
git pull origin develop
mvn install -DskipTests -P frontend,prod
sh ./mysql-installation.sh