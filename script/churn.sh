#!/bin/bash

# The script churns aka install | manage all application
# for the local dataplatform setup


usage() {
	echo "usage: churn [ --app spark|flink|kafka ] [--action intall|start|stop|restart]"
}

main() {
	while [[ $# -gt 0 ]]; do

		case $1 in
			--app | -a )        shift
							    app_name=$1
							    ;;
			--action | -ac )	shift
								action=$1
								;;
			*)					usage
								exit 1
								;;
		esac
		shift

	done 		
}	

main $#
