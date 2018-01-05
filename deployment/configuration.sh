#!/bin/bash
sudo apt-get install -y software-properties-common
sudo apt-add-repository universe
sudo apt-get -y update
echo "Enter root password for MySQL DB:"
read rootPassword
echo "Repeat your password:"
read rootPassword
sudo apt-get -y install default-jre
sudo apt-get -y install default-jdk
sudo apt-get -y install git
sudo apt-get -y install maven
sudo apt-get -y install htop
debconf-set-selections <<< "mysql-server mysql-server/root_password password $rootPassword"
debconf-set-selections <<< "mysql-server mysql-server/root_password_again password $rootPassword"
sudo apt-get -y install mysql-server
sudo reboot
