all:
	@echo Compiling CppConnector...
	g++ -c src/connector.cpp src/ApiConnector.cpp ../commons/src/Variant.cpp \
	-I../commons/src
	
	@(ar rcs libcppConnector.a *.o && echo libcppConnector.a created)
