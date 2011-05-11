all:
	clear
	
	@(cd connector && make)
	
	@echo Compiling game...
	g++ -shared src/Game.cpp \
	-o libgame.so \
	-I./connector \
	-I../../../resources/cpp/commons/src \
	-I../../../resources/cpp/game/src \
	-L./ -lcommunicator
