#! /bin/sh
echo "Enter repository username"
read username
echo "Enter repository password"
read password
sudo apt-get install -y default-jre
sudo apt-get install -y default-jdk
sudo apt-get install -y git
sudo apt-get install -y maven
mkdir si
cd si
git init 
git remote add origin https://${username}:${password}@bitbucket.org/edwardKats/shutaf-in.git
git fetch 
git checkout develop
git pull origin develop
mvn install -DskipTests -P frontend,prod
