

ps -ef | grep -i play | grep -v grep | awk '{print $2}' | xargs kill

rm -r assignment-1.0.SNAPSHOT | echo "artifact already deleted"

unzip assignment-1.0-SNAPSHOT.zip 

rm -r assignment-1.0-SNAPSHOT.zip 

./assignment-1.0-SNAPSHOT/bin/assignment &

