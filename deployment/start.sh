#! /bin/sh
if [ -d "servers" ]
then
	echo "REMOVING 'servers'"
	rm -rf servers
fi

if [ -d "logs" ]
then
	echo "REMOVING 'logs'"
	rm -rf logs
fi

if [ -d "pids" ]
then
	echo "RUNNING 'stop.sh'"
	sh ./stop.sh	
	echo "REMOVING 'pids'"
	rm -rf pids
fi

mkdir servers
mkdir logs
mkdir pids

cp si-discovery/target/si-discovery.jar servers/si-discovery.jar
cp si-gateway/si-gateway-webapp/target/si-gateway.jar servers/si-gateway.jar
cp si-account/target/si-account.jar servers/si-account.jar
cp si-matching/target/si-matching.jar servers/si-matching.jar
cp si-email-notification/target/si-email.jar servers/si-email.jar
cp si-deal/target/si-deal.jar servers/si-deal.jar

nohup java -jar servers/si-discovery.jar > logs/si-discovery.log 2>&1 </dev/null & echo $! > pids/si-discovery.pid
nohup java -jar servers/si-account.jar > logs/si-account.log 2>&1  </dev/null & echo $! > pids/si-account.pid
nohup java -jar servers/si-deal.jar > logs/si-deal.log 2>&1 </dev/null & echo $! > pids/si-deal.pid
nohup java -jar servers/si-matching.jar > logs/si-matching.log 2>&1 </dev/null & echo $! > pids/si-matching.pid
nohup java -jar servers/si-gateway.jar > logs/si-gateway.log 2>&1 </dev/null & echo $! > pids/si-gateway.pid
nohup java -jar servers/si-email.jar > logs/si-email.log 2>&1 </dev/null & echo $! > pids/si-email.pid

