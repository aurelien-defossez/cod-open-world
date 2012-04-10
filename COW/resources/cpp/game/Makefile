all:
	@ echo Compiling CppConnector...
	g++ -static -c src/connector.cpp src/Commander.cpp ../commons/src/Variant.cpp \
	-I../commons/src
	
	@ rm -f libcppConnector.a
	@ ar rcs libcppConnector.a *.o && echo libcppConnector.a created
	