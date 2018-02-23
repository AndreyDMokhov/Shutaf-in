#! /bin/sh
sudo apt-get install -y default-jre
sudo apt-get install -y default-jdk
sudo apt-get install -y git
sudo apt-get install -y maven 
mkdir downloads
cd downloads
if [ ! -f "ideaIU-2017.3.4.tar.gz" ]
then
	echo "Starting to download Intellij IDEA"
	wget https://download.jetbrains.com/idea/ideaIU-2017.3.4.tar.gz
	tar -xzf ideaIU-2017.3.4.tar.gz
	chmod a+x ideaUI-2017.3.4/bin/idea.sh
fi
nohup sh ideaUI-2017.3.4/bin/idea.sh > output.log 2>&1 </dev/null &
cd ..
mkdir si
cd si
git init 
git remote add origin https://edwardKats:go.get4su@bitbucket.org/edwardKats/shutaf-in.git 
git fetch 
git checkout develop
git pull origin develop
mvn install -DskipTests -P frontend,prod
