#FIRST OF ALL, POSITION TO UEBUNG3/SRC/ USING cd

#TO COMPILE SERVER:
javac server/Server.java
javac server/ImageProcessorImpl.java
rmic server.ImageProcessorImpl

#TO COMPILE CLIENT:
javac client/Client.java
javac client/Page2.java

rmiregistry 10001 &

#TO START SERVER AND CLIENT:
java -Djava.security.policy=server/server.policy server/Server 10001
java client.Client localhost 10001

#CLEAN:
rm server/*.class
rm client/*.class
rm common/*.class