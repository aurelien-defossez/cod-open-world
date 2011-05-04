#ifndef __GAME_COMMUNICATOR_H__
#define __GAME_COMMUNICATOR_H__

#include "Variant.hpp"

class GameCommunicator {
public:
	void init(int nbParameters, char *parameters[]);
	void addAi(short aiId, char *aiName, char *playerName);
	void play();
	void endGame();
	void disqualifyAi(char *aiName, char *reason);
	void performGameFunction(int functionId, int nbParameters, Variant parameters[]);
};

#endif

