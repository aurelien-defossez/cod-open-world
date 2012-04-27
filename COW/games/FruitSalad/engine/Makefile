all:
	clear
	
	@ cd connector && make
	
	@ echo Compiling game...
	@ rm -f libgame.*
	g++ -shared -m32 src/*.cpp \
	-o libgame.so \
	-I./connector \
	-I../../../resources/cpp/commons/src \
	-I../../../resources/cpp/game/src \
	-L./ -lcommunicator
	
	@ echo Game created
	
	cp libgame.so libgame.dylib
