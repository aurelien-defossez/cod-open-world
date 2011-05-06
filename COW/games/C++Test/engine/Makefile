all:
	clear
	
	(cd connector && make)
	
	g++ -shared src/Game.cpp \
	-o libgame.so \
	-I./connector \
	-I../../../resources/cpp/commons/src \
	-I../../../resources/cpp/game/src \
	-L./ -lcommunicator
