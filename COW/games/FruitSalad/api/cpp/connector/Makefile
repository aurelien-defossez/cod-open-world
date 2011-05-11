all:
	@(cd ../../../../../resources/cpp/ai && make)
	
	@echo Compiling Specific communicator...
	g++ -c SpecificCommunicator.cpp SpecificApiConnector.cpp \
	-I../../../../../resources/cpp/commons/src \
	-I../../../../../resources/cpp/ai/src \
	-L../../../../../resources/cpp/ai -lcppConnector
	
	@(cd tmp && ar x ../../../../../../resources/cpp/ai/libcppConnector.a)
	
	@(ar rcs ../libcommunicator.a *.o tmp/*.o && echo libcommunicator.a created)
