all:
	clear
	
	@ cd connector && make
	
	@ echo Compiling game...
	@ rm -f libgame.*
	g++ -shared src/*.cpp \
	-o libgame.so \
	-I./connector \
	-I../../../resources/cpp/commons/src \
	-I../../../resources/cpp/game/src \
	-L./ -lcommunicator
	
	cp libgame.so libgame.dylib
