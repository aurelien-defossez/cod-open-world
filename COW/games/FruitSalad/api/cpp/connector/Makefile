all:
	@(cd ../../../../../resources/cpp/ai && make)
	
	@echo Compiling Specific communicator...
	g++ -c -m32 SpecificCommunicator.cpp SpecificApiConnector.cpp \
	-I../../../../../resources/cpp/commons/src \
	-I../../../../../resources/cpp/ai/src \
	-L../../../../../resources/cpp/ai -lcppConnector
	
	@mkdir -p tmp
	@(cd tmp && ar x ../../../../../../resources/cpp/ai/libcppConnector.a)
	
	@(ar rcs ../libcommunicator.a *.o tmp/*.o && echo libcommunicator.a created)
