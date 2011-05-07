all:
	@(cd ../../../../resources/cpp/game && make)
	
	@echo Compiling Specific communicator...
	g++ -c SpecificCommunicator.cpp \
	-I../../../../resources/cpp/commons/src \
	-I../../../../resources/cpp/game/src \
	-L../../../../resources/cpp/game -lcppConnector
	
	@(cd tmp && ar x ../../../../../resources/cpp/game/libcppConnector.a)
	
	@(ar rcs ../libcommunicator.a *.o tmp/*.o && echo libcommunicator.a created)
	