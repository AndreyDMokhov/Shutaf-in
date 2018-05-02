#! /bin/sh
sh ./backup.sh
cd ..
if [ -d "pids" ]
then
        cd pids

	si_discovery="si-discovery.pid"
	if [ -f "$si_discovery" ]
	then
		echo "STOPPING $si_discovery"
		kill -9 `cat $si_discovery`
	fi 
	
	si_gateway="si-gateway.pid"
	if [ -f "$si_gateway" ]
	then
		echo "STOPPING $si_gateway"
		kill -9 `cat $si_gateway`
	fi

	si_matching="si-matching.pid"
	if [ -f "$si_matching" ]
	then
		echo "STOPPING $si_matching"
		kill -9 `cat $si_matching`
	fi

	si_account="si-account.pid"
	if [ -f "$si_account" ]
	then
		echo "STOPPING $si_account"
		kill -9 `cat $si_account`
	fi

	si_email="si-email.pid"
	if [ -f "$si_email" ]
	then
		echo "STOPPING $si_email"
		kill -9 `cat $si_email`
	fi

	si_deal="si-deal.pid"
	if [ -f "$si_deal" ]
	then
		echo "STOPPING $si_deal"
		kill -9 `cat $si_deal`
	fi

	
        
        cd ..
        rm -rf pids
fi

