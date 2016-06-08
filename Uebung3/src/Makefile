#FIRST OF ALL, POSITION TO UEBUNG3/SRC/ VIA cd

#TO COMPILE SERVER:
server:
	javac server/Server.java #./common/*.java server/ImageProcessorImpl.java
	javac server/ImageProcessorImpl.java ./common/*.java
	rmic server.ImageProcessorImpl

	jar cvf ImageProcessor.jar common/ImageProcessor.class

#TO COMPILE CLIENT:
client:
	javac client/Client.java
	javac client/Page2.java

rmiregistry:
	rmiregistry &

#TO START SERVER AND CLIENT:
starts: server 
	java -cp /home/agostino/workspace/Verteilte/Uebung3/src:/home/agostino/workspace/Verteilte/Uebung3/src/ImageProcessor.jar \
	-Djava.rmi.server.codebase=http://mycomputer/~agostino/workspace/Verteilte/Uebung3/src/ImageProcessor.jar \
	-Djava.rmi.server.hostname=localhost \
	-Djava.security.policy=/home/agostino/workspace/Verteilte/Uebung3/src/server/server.policy \
	server.Server 10001
startc: client
	java -cp /home/agostino/workspace/Verteilte/Uebung3/src:/home/agostino/workspace/Verteilte/Uebung3/src/ImageProcessor.jar \
     -Djava.rmi.server.codebase=http://mysecondcomputer/~agostino/workspace/Verteilte/Uebung3/src/ \
     -Djava.security.policy=/home/agostino/workspace/Verteilte/Uebung3/src/client/client.policy \
        client.Client localhost 10001

clean:
	rm server/*.class
	rm client/*.class
	rm common/*.class