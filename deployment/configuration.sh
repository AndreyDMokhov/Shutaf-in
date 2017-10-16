#!/bin/bash
apt-get update
echo "Enter root password for MySQL DB:"
read rootPassword
echo "Repeat your password:"
read rootPassword
apt-get -y install default-jre
apt-get -y install default-jdk
apt-get -y install git
apt-get -y install maven
debconf-set-selections <<< "mysql-server mysql-server/root_password password $rootPassword"
debconf-set-selections <<< "mysql-server mysql-server/root_password_again password $rootPassword"
apt-get -y install mysql-server
apt-get -y install nodejs
apt-get install npm
npm install -g bower
npm install -g gulp
reboot