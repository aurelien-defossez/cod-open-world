all:
#	g++ -shared src/connector.cpp src/Variant.cpp src/GameCommunicator.cpp -o /tmp/lib/libcppConnector.so
	g++ -c src/connector.cpp src/Variant.cpp src/GameCommunicator.cpp
	ar rcs libcppConnector.a connector.o Variant.o GameCommunicator.o

