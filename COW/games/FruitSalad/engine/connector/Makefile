all:
	@ cd ../../../../resources/cpp/game && make
	
	@ echo Compiling Specific communicator...
	g++ -static -m32 -c SpecificCommunicator.cpp SpecificCommander.cpp \
	-I../../../../resources/cpp/commons/src \
	-I../../../../resources/cpp/game/src
	
	@ mkdir -p tmp
	@ cd tmp && ar x ../../../../../resources/cpp/game/libcppConnector.a
	
	@ rm -f ../libcommunicator.a
	@ ar rcs ../libcommunicator.a *.o tmp/*.o && echo libcommunicator.a created
	
