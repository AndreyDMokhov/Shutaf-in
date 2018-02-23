#! /bin/sh
cd ..
git checkout develop
git pull origin develop
mvn clean
mvn install -DskipTests -P frontend,prod