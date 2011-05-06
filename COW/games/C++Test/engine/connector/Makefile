all:
	(cd ../../../../resources/cpp/game && make)
	
	g++ -c SpecificCommunicator.cpp \
	-I../../../../resources/cpp/commons/src \
	-I../../../../resources/cpp/game/src \
	-L../../../../resources/cpp/game -lcppConnector
	
	(cd tmp && ar xv ../../../../../resources/cpp/game/libcppConnector.a)

	ar rcs ../libcommunicator.a *.o tmp/*.o
	